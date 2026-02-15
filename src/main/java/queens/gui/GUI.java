package queens.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import queens.core.Board;
import queens.core.Solution;
import queens.core.Solver;
import queens.util.BoardFormatter;
import queens.util.FileHandler;

import java.io.File;

public class GUI extends Application {

  private Label fileLabel;
  private Button selectFileButton;
  private Button solveButton;
  private File selectedFile;

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("LinkedIn Queens Solver");

    // Create UI components
    fileLabel = new Label("No file selected");
    fileLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

    selectFileButton = new Button("Select Input File");
    selectFileButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
    selectFileButton.setOnAction(e -> selectFile(primaryStage));

    solveButton = new Button("Solve Puzzle");
    solveButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
    solveButton.setDisable(true); // Disabled until file selected
    solveButton.setOnAction(e -> solvePuzzle());

    // Layout
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(40));
    layout.getChildren().addAll(createTitle(), fileLabel, selectFileButton, solveButton);

    // Scene
    Scene scene = new Scene(layout, 500, 300);
    primaryStage.setScene(scene);
    primaryStage.show();

    System.out.println("=== LinkedIn Queens Solver GUI ===");
    System.out.println("Select an input file to begin solving.");
    System.out.println();
  }

  private Label createTitle() {
    Label title = new Label("LinkedIn Queens Solver");
    title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    return title;
  }

  private void selectFile(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Input File");

    // Set initial directory to test/inputs if it exists
    File inputDir = new File("test/inputs");
    if (inputDir.exists() && inputDir.isDirectory()) {
      fileChooser.setInitialDirectory(inputDir);
    }

    // Filter for text files
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
        new FileChooser.ExtensionFilter("All Files", "*.*"));

    selectedFile = fileChooser.showOpenDialog(stage);

    if (selectedFile != null) {
      fileLabel.setText("Selected: " + selectedFile.getName());
      solveButton.setDisable(false);
      System.out.println("File selected: " + selectedFile.getAbsolutePath());
    }
  }

  private void solvePuzzle() {
    if (selectedFile == null) {
      System.err.println("Error: No file selected!");
      return;
    }

    try {
      System.out.println("\n=== Starting Solution ===");
      System.out.println("Input file: " + selectedFile.getAbsolutePath());
      System.out.println();

      // Read board from file
      System.out.println("Reading board from file...");
      Board board = FileHandler.readBoardFromFile(selectedFile.getAbsolutePath());

      System.out.println("Board size: " + board.getSize() + "x" + board.getSize());
      System.out.println("Solving LinkedIn Queens puzzle...");
      System.out.println();

      // Solve the puzzle
      long startTime = System.currentTimeMillis();
      Solution solution = Solver.solve(board);
      long elapsedTime = System.currentTimeMillis() - startTime;

      // Display results
      System.out.println("\n=== Solution Results ===");
      BoardFormatter.printSolution(board, solution);

      if (solution != null) {
        System.out.println("\n✓ Solution found!");
        System.out.println("Time: " + elapsedTime + "ms");
      } else {
        System.out.println("\n✗ No solution found.");
      }
      System.out.println("========================\n");

    } catch (Exception e) {
      System.err.println("Error solving puzzle: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
