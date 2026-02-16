package queens.controller;

import queens.model.Board;
import queens.model.Solution;
import queens.service.FileService;
import queens.service.SolverService;
import queens.util.BoardFormatter;

import java.util.Scanner;

public class CLIController {

  private final FileService fileService;
  private final SolverService solverService;
  private final Scanner scanner;

  public CLIController() {
    this.fileService = new FileService();
    this.solverService = new SolverService();
    this.scanner = new Scanner(System.in);
  }

  public void run(String[] args) {
    try {
      String inputFile = getInputFile(args);

      if (!fileService.fileExists(inputFile)) {
        System.out.println("Error: File '" + inputFile + "' not found.");
        return;
      }

      // Load board
      System.out.println("Reading board from file...");
      Board board = fileService.readBoardFromFile(inputFile);

      System.out.println("Board size: " + board.getSize() + "x" + board.getSize());
      System.out.println("Solving LinkedIn Queens puzzle...");
      System.out.println();

      // Solve
      Solution solution = solverService.solve(board);

      // Display result
      BoardFormatter.printSolution(board, solution);

      // Handle save prompt if solution found
      if (solution != null) {
        handleSavePrompt(board, solution);
      }

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      scanner.close();
    }
  }


  private String getInputFile(String[] args) {
    // Check if filename provided as argument
    if (args.length > 0) {
      String inputFile = args[0];
      System.out.println("Input file: " + inputFile);
      return inputFile;
    } else {
      // Prompt for input file
      System.out.print("Enter input filename: ");
      return scanner.nextLine().trim();
    }
  }

  private void handleSavePrompt(Board board, Solution solution) {
    try {
      System.out.println();
      System.out.print("Do you want to save the solution? (Y/N): ");
      String response = scanner.nextLine().trim().toLowerCase();

      if (response.equals("y") || response.equals("yes")) {
        System.out.print("Enter output filename: ");
        String outputFile = scanner.nextLine().trim();

        fileService.saveSolution(outputFile, board, solution);
        System.out.println("Solution saved to " + outputFile);
      }
    } catch (Exception e) {
      System.err.println("Error saving solution: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
