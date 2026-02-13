package queens.util;

import queens.core.Board;
import queens.core.Position;
import queens.core.Solution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoardFormatter {

  public static String formatSolution(Board board, List<Position> queens) {
    int size = board.getSize();
    char[][] grid = board.getRegionGrid();

    // Mark queen positions with '#'
    Set<Position> queenSet = new HashSet<>(queens);
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
