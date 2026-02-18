package queens.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import queens.model.Board;
import queens.model.Solution;
import queens.service.FileService;
import queens.service.SolverService;
import queens.util.BoardFormatter;
import queens.view.BoardCanvas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainController {

  @FXML
  private Label fileLabel;

  @FXML
  private Button selectFileButton;

  @FXML
  private Button solveButton;

  @FXML
  private Button exportTxtButton;

  @FXML
  private Button exportImgButton;

  @FXML
  private ScrollPane boardScrollPane;

  @FXML
  private Label iterationsLabel;

  @FXML
  private Label timerLabel;

  private BoardCanvas boardCanvas;
  private File selectedFile;
  private Board currentBoard;
  private Solution currentSolution;
  private Stage stage;

  // Service layer dependencies
  private final FileService fileService;
  private final SolverService solverService;

  public MainController() {
    this.fileService = new FileService();
    this.solverService = new SolverService();
  }

  @FXML
  public void initialize() {
    // Initialize view component
    boardCanvas = new BoardCanvas(8);
    boardCanvas.setStyle("-fx-border-color: lightgray; -fx-border-width: 2;");

    // Wrap canvas in a StackPane to center it
    javafx.scene.layout.StackPane centerContainer = new javafx.scene.layout.StackPane(boardCanvas);
    centerContainer.setAlignment(javafx.geometry.Pos.CENTER);
    centerContainer.setStyle("-fx-background-color: white;");
    boardScrollPane.setContent(centerContainer);

    System.out.println("=== LinkedIn Queens Solver GUI ===");
    System.out.println("Select an input file to view and solve.");
    System.out.println();
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  @FXML
  private void handleSelectFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Input File");

    File inputDir = new File("test/inputs");
    if (inputDir.exists() && inputDir.isDirectory()) {
      fileChooser.setInitialDirectory(inputDir);
    }

    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
        new FileChooser.ExtensionFilter("All Files", "*.*"));

    selectedFile = fileChooser.showOpenDialog(stage);

    if (selectedFile != null) {
      loadAndDisplayBoard();
    }
  }

  private void loadAndDisplayBoard() {
    try {
      currentBoard = fileService.readBoardFromFile(selectedFile.getAbsolutePath());

      // Validate board (NxN)
      char[][] regionGrid = currentBoard.getRegionGrid();
      int rows = regionGrid.length;
      for (int i = 0; i < rows; i++) {
        if (regionGrid[i].length != rows) {
          throw new IllegalArgumentException(
              "Invalid board dimensions: Board must be NxN");
        }
        // Check for null/empty characters (happens when input row is incomplete)
        for (int j = 0; j < rows; j++) {
          if (regionGrid[i][j] == '\0' || regionGrid[i][j] == ' ') {
            throw new IllegalArgumentException(
                "Invalid board: Row " + (i + 1) + " has incomplete data (missing character at column " + (j + 1) + ")");
          }
        }
      }

      // Validate that number of regions equals N
      int numRegions = currentBoard.getRegions().size();
      if (numRegions != currentBoard.getSize()) {
        throw new IllegalArgumentException(
            "Invalid number of regions: Expected " + currentBoard.getSize() +
                " regions, but found " + numRegions + " regions");
      }

      // Reset UI state
      currentSolution = null;
      boardCanvas.setBoard(currentBoard);
      iterationsLabel.setText("0");
      timerLabel.setText("0 ms");

      solveButton.setText("Solve Puzzle");
      solveButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
      solveButton.setDisable(false);
      exportTxtButton.setDisable(true);
      exportImgButton.setDisable(true);

      System.out.println("Board loaded: " + currentBoard.getSize() + "x" + currentBoard.getSize());

      fileLabel.setText("File: " + selectedFile.getName() + "\n(" + currentBoard.getSize() + "x"
          + currentBoard.getSize() + " board)");

      System.out.println("File selected: " + selectedFile.getAbsolutePath());

    } catch (IllegalArgumentException e) {
      System.err.println("Validation error: " + e.getMessage());
      fileLabel.setText("Invalid file!\n" + e.getMessage());
      solveButton.setDisable(true);
      currentBoard = null;
    } catch (Exception e) {
      System.err.println("Error loading board: " + e.getMessage());
      e.printStackTrace();
      fileLabel.setText("Error loading file!");
      solveButton.setDisable(true);
      currentBoard = null;
    }
  }

  @FXML
  private void handleSolve() {
    if (selectedFile == null || currentBoard == null) {
      System.err.println("Error: No file selected!");
      return;
    }

    System.out.println("\n=== Starting Solution ===");
    System.out.println("Board size: " + currentBoard.getSize() + "x" + currentBoard.getSize());
    System.out.println("Solving...");

    // Reset stats labels
    iterationsLabel.setText("0");
    timerLabel.setText("0 ms");

    // Disable solve button during solving
    solveButton.setDisable(true);

    // Run solver in background thread
    final long startTime = System.currentTimeMillis();
    final java.util.concurrent.atomic.AtomicReference<java.util.List<queens.model.Position>> latestPositions = new java.util.concurrent.atomic.AtomicReference<>();

    javafx.concurrent.Task<Solution> solverTask = new javafx.concurrent.Task<Solution>() {
      @Override
      protected Solution call() throws Exception {
        return solverService.solve(currentBoard, (iterations, positions) -> {
          latestPositions.set(positions);
          javafx.application.Platform.runLater(() -> {
            iterationsLabel.setText(String.valueOf(iterations));
          });
        });
      }
    };

    // Update timer and board every 100ms
    final javafx.animation.Timeline timeline = new javafx.animation.Timeline(
        new javafx.animation.KeyFrame(javafx.util.Duration.millis(100), event -> {
          long elapsedTime = System.currentTimeMillis() - startTime;
          timerLabel.setText(elapsedTime + " ms");

          // Update board with latest candidate positions (throttled to 100ms)
          java.util.List<queens.model.Position> positions = latestPositions.get();
          if (positions != null) {
            queens.model.SolutionStats tempStats = new queens.model.SolutionStats(0, 0, false);
            queens.model.Solution tempSolution = new queens.model.Solution(positions, tempStats);
            boardCanvas.setSolution(tempSolution);
          }
        }));
    timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE);
    timeline.play();

    // Handle task completion
    solverTask.setOnSucceeded(event -> {
      timeline.stop();
      Solution solution = solverTask.getValue();
      long finalElapsedTime = System.currentTimeMillis() - startTime;

      System.out.println("\n=== Solution Results ===");
      BoardFormatter.printSolution(currentBoard, solution);

      if (solution != null) {
        System.out.println("\n✓ Solution found in " + finalElapsedTime + "ms");

        currentSolution = solution;
        boardCanvas.setSolution(solution);
        iterationsLabel.setText(String.valueOf(solution.getStats().getCases()));
        timerLabel.setText(finalElapsedTime + " ms");

        // Update UI state
        solveButton.setText("Solved!");
        solveButton.setStyle(
            "-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #90EE90;");
        exportTxtButton.setDisable(false);
        exportImgButton.setDisable(false);
      } else {
        System.out.println("\n✗ No solution found");

        // Update final time
        timerLabel.setText(finalElapsedTime + " ms");

        // Update UI state
        solveButton.setText("No Solution");
        solveButton.setStyle(
            "-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #FFB6C1;");
      }
      System.out.println("========================\n");

      // Re-enable solve button
      solveButton.setDisable(false);
    });

    // Error handler
    solverTask.setOnFailed(event -> {
      timeline.stop();
      Throwable exception = solverTask.getException();
      System.err.println("Error: " + exception.getMessage());
      exception.printStackTrace();

      solveButton.setDisable(false);
    });

    // Start the solver task in a background thread
    Thread solverThread = new Thread(solverTask);
    solverThread.setDaemon(true);
    solverThread.start();
  }

  @FXML
  private void handleExportTxt() {
    if (currentBoard == null || currentSolution == null)
      return;

    File outputDir = new File("test/outputs");
    outputDir.mkdirs();

    String baseName = (selectedFile != null)
        ? selectedFile.getName().replaceFirst("[.][^.]+$", "")
        : "board";
    File outputFile = new File(outputDir, "output_" + baseName + ".txt");

    try {
      fileService.saveSolution(outputFile.getAbsolutePath(), currentBoard, currentSolution);
      System.out.println("Text exported to: " + outputFile.getAbsolutePath());
      exportTxtButton.setText("Exported!");
      exportTxtButton.setStyle("-fx-font-size: 13px; -fx-padding: 10px 8px; -fx-background-color: #90EE90;");
      javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
          javafx.util.Duration.seconds(2));
      pause.setOnFinished(e -> {
        exportTxtButton.setText("Export .txt");
        exportTxtButton.setStyle("-fx-font-size: 13px; -fx-padding: 10px 8px;");
      });
      pause.play();
    } catch (IOException e) {
      System.err.println("Failed to export text: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @FXML
  private void handleExportImage() {
    if (boardCanvas == null)
      return;

    // Snapshot the canvas
    WritableImage writableImage = boardCanvas.snapshot(null, null);

    // Convert to BufferedImage for ImageIO
    int width = (int) writableImage.getWidth();
    int height = (int) writableImage.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        javafx.scene.paint.Color fxColor = writableImage.getPixelReader().getColor(x, y);
        int r = (int) (fxColor.getRed() * 255);
        int g = (int) (fxColor.getGreen() * 255);
        int b = (int) (fxColor.getBlue() * 255);
        int a = (int) (fxColor.getOpacity() * 255);
        bufferedImage.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
      }
    }

    // Ensure output directory exists
    File outputDir = new File("test/outputs");
    outputDir.mkdirs();

    // Generate filename
    String baseName = (selectedFile != null)
        ? selectedFile.getName().replaceFirst("[.][^.]+$", "")
        : "board";
    File outputFile = new File(outputDir, "output_" + baseName + ".png");

    try {
      ImageIO.write(bufferedImage, "PNG", outputFile);
      System.out.println("Image exported to: " + outputFile.getAbsolutePath());
      exportImgButton.setText("Exported!");
      exportImgButton.setStyle("-fx-font-size: 13px; -fx-padding: 10px 8px; -fx-background-color: #90EE90;");

      // Reset button text after 2 seconds
      javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
          javafx.util.Duration.seconds(2));
      pause.setOnFinished(e -> {
        exportImgButton.setText("Export Image");
        exportImgButton.setStyle("-fx-font-size: 13px; -fx-padding: 10px 8px;");
      });
      pause.play();
    } catch (IOException e) {
      System.err.println("Failed to export image: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
