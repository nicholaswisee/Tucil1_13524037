package queens.model;

import java.util.ArrayList;
import java.util.List;

public class Solution {

  private final List<Position> queenPositions;
  private final SolutionStats stats;

  public Solution(List<Position> queenPositions, SolutionStats stats) {
    this.stats = stats;
    this.queenPositions = new ArrayList<>(queenPositions);
  }

  public SolutionStats getStats() {
    return stats;
  }

  public List<Position> getQueenPositions() {
    return queenPositions;
  }
}
