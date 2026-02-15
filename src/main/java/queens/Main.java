package queens;

import queens.core.Board;
import queens.core.Solution;
import queens.core.Solver;
import queens.gui.GUI;
import queens.util.BoardFormatter;
import queens.util.FileHandler;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    // Check for GUI mode
    if (args.length == 0 || (args.length > 0 && args[0].equals("--gui"))) {
      System.out.println("Launching GUI mode...");
      GUI.main(args);
      return;
    }

    // CLI mode
    Scanner scanner = new Scanner(System.in);

    try {
      String inputFile;

      // Check if filename provided as argument
      if (args.length > 0) {
        inputFile = args[0];
        System.out.println("Input file: " + inputFile);
      } else {
        // Prompt for input file
        System.out.print("Enter input filename: ");
        inputFile = scanner.nextLine().trim();
      }

      // Check if file exists
      if (!FileHandler.fileExists(inputFile)) {
        System.out.println("Error: File '" + inputFile + "' not found.");
        return;
      }

      // Read board from file
      System.out.println("Reading board from file...");
      Board board = FileHandler.readBoardFromFile(inputFile);

      System.out.println("Board size: " + board.getSize() + "x" + board.getSize());
      System.out.println("Solving LinkedIn Queens puzzle...");
      System.out.println();

      // Solve the puzzle
      Solution solution = Solver.solve(board);

      // Display results
      BoardFormatter.printSolution(board, solution);

      // Prompt to save (if solution found)
      if (solution != null) {
        System.out.println();
        System.out.print("Do you want to save the solution? (Y/N): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
          System.out.print("Enter output filename: ");
          String outputFile = scanner.nextLine().trim();

          FileHandler.saveSolution(outputFile, board, solution);
          System.out.println("Solution saved to " + outputFile);
        }
      }

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      scanner.close();
    }
  }
}
