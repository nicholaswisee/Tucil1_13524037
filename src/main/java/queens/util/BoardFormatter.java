package queens.util;

import queens.model.Board;
import queens.model.Position;
import queens.model.Solution;

import java.util.List;

public class BoardFormatter {

  public static String formatSolution(Board board, List<Position> queens) {
    int size = board.getSize();
    char[][] grid = board.getRegionGrid();

    // Mark queen positions with '#'
    for (Position queen : queens) {
      grid[queen.getRow()][queen.getCol()] = '#';
    }

    // Build output string
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        sb.append(grid[i][j]);
      }
      if (i < size - 1) {
        sb.append('\n');
      }
    }

    return sb.toString();
  }

  // Print solution
  public static void printSolution(Board board, Solution solution) {
    if (solution == null) {
      System.out.println("Tidak ada solusi yang ditemukan.");
      return;
    }

    // Print the board with queens
    String formattedBoard = formatSolution(board, solution.getQueenPositions());
    System.out.println(formattedBoard);

    // Print statistics
    System.out.println(solution.getStats());
  }

  // Save solution
  public static boolean promptSave() {
    System.out.print("Apakah Anda ingin menyimpan solusi? (Ya/Tidak): ");
    return true;
  }
}
