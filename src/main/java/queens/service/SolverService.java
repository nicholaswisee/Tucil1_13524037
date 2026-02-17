package queens.service;

import queens.model.Board;
import queens.model.Position;
import queens.model.Solution;
import queens.model.SolutionStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverService {

  public Solution solve(Board board) {
    return solve(board, null);
  }

  public Solution solve(Board board, ProgressCallback progressCallback) {
    int size = board.getSize();
    int cases = 0;
    long startTime = System.currentTimeMillis();

    // Generate all n^n cases, store column number for queen in row i in column[i]
    int[] columns = new int[size];

    while (true) {
      // Start with 1 Queen in each row
      List<Position> solution = new ArrayList<>();
      for (int row = 0; row < size; row++) {
        solution.add(new Position(row, columns[row]));
      }

      cases++;

      // Progress callback for GUI updates (every 10,000 iterations)
      if (progressCallback != null && cases % 10000 == 0) {
        progressCallback.onProgress(cases);
      }

      // Progress logging for large puzzles
      if (cases % 1000000 == 0) {
        System.err.printf("Progress: %d million cases reviewed...\n", cases / 1000000);
      }

      if (isValidSolution(solution, board)) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        SolutionStats stats = new SolutionStats(elapsedTime, cases, true);
        return new Solution(solution, stats);
      }

      // Generate new solutions
      int row = size - 1;
      while (row >= 0 && columns[row] == size - 1) {
        columns[row] = 0;
        row--;
      }

      if (row < 0) {
        break;
      }

      columns[row]++;
    }

    // No solution found
    return null;
  }

  private boolean isValidSolution(List<Position> queenPositions, Board board) {
    if (hasAttacks(queenPositions)) {
      return false;
    }

    return hasOnePerRegion(queenPositions, board);
  }

  private boolean hasAttacks(List<Position> queenPositions) {
    for (int i = 0; i < queenPositions.size(); i++) {
      for (int j = i + 1; j < queenPositions.size(); j++) {
        if (queenPositions.get(i).attacks(queenPositions.get(j))) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean hasOnePerRegion(List<Position> queenPositions, Board board) {
    Map<Character, Integer> regionCounts = new HashMap<>();

    for (Position queen : queenPositions) {
      char region = board.getRegion(queen);
      regionCounts.put(region, regionCounts.getOrDefault(region, 0) + 1);
    }

    // Check for only 1 queen
    List<Character> regions = board.getRegions();
    if (regionCounts.size() != regions.size()) {
      return false;
    }

    for (int count : regionCounts.values()) {
      if (count != 1) {
        return false;
      }
    }

    return true;
  }
}
