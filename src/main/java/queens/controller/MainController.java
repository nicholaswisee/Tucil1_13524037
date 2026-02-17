package queens.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import queens.model.Board;
import queens.model.Solution;
import queens.service.FileService;
import queens.service.SolverService;
import queens.util.BoardFormatter;
import queens.view.BoardCanvas;

import java.io.File;

public class MainController {

  @FXML
  private Label fileLabel;

  @FXML
  private Button selectFileButton;

  @FXML
  private Button solveButton;

  @FXML
  private ScrollPane boardScrollPane;

  @FXML
  private Label iterationsLabel;

  @FXML
  private Label timerLabel;

  private BoardCanvas boardCanvas;
  private File selectedFile;
  private Board currentBoard;
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
      // Use service to load board (Model)
      currentBoard = fileService.readBoardFromFile(selectedFile.getAbsolutePath());

      System.out
          .println("DEBUG: Board loaded: " + currentBoard.getSize() + "x" + currentBoard.getSize());
      System.out.println("DEBUG: Calling boardCanvas.setBoard()...");

      // Update view
      boardCanvas.setBoard(currentBoard);

      System.out.println("DEBUG: Canvas size after setBoard: " + boardCanvas.getWidth() + "x"
          + boardCanvas.getHeight());
      System.out.println("Board loaded: " + currentBoard.getSize() + "x" + currentBoard.getSize());

      fileLabel.setText("File: " + selectedFile.getName() + "\n(" + currentBoard.getSize() + "x"
          + currentBoard.getSize() + " board)");
      solveButton.setDisable(false);

      System.out.println("File selected: " + selectedFile.getAbsolutePath());

    } catch (Exception e) {
      System.err.println("Error loading board: " + e.getMessage());
      e.printStackTrace();
      fileLabel.setText("Error loading file!");
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

    // Create a Task to run the solver in a background thread
    final long startTime = System.currentTimeMillis();
    javafx.concurrent.Task<Solution> solverTask = new javafx.concurrent.Task<Solution>() {
      @Override
      protected Solution call() throws Exception {
        // Solve with progress callback that updates GUI on JavaFX thread
        return solverService.solve(currentBoard, (iterations) -> {
          javafx.application.Platform.runLater(() -> {
            iterationsLabel.setText(String.valueOf(iterations));
          });
        });
      }
    };

    // Create a Timeline to update the timer every 100ms
    final javafx.animation.Timeline timeline = new javafx.animation.Timeline(
        new javafx.animation.KeyFrame(javafx.util.Duration.millis(100), event -> {
          long elapsedTime = System.currentTimeMillis() - startTime;
          timerLabel.setText(elapsedTime + " ms");
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

        boardCanvas.setSolution(solution);
        iterationsLabel.setText(String.valueOf(solution.getStats().getCases()));
        timerLabel.setText(finalElapsedTime + " ms");

        // Update UI state
        solveButton.setText("Solved!");
        solveButton.setStyle(
            "-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #90EE90;");
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
}
