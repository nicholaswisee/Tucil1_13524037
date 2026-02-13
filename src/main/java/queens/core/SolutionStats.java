package queens.core;

public class SolutionStats {

  private int cases;
  private long solvingTime;
  private boolean solutionFound;

  public SolutionStats(int cases, long solvingTime, boolean solutionFound) {
    this.cases = cases;
    this.solvingTime = solvingTime;
    this.solutionFound = solutionFound;
  }

  public int getCases() {
    return cases;
  }

  public long getSolvingTime() {
    return solvingTime;
  }

  public boolean getSolutionFound() {
    return solutionFound;
  }

  public void setCases(int cases) {
    this.cases = cases;
  }

  public void setSolvingTime(long solvingTime) {
    this.solvingTime = solvingTime;
  }

  public void setSolutionFound(boolean solutionFound) {
    this.solutionFound = solutionFound;
  }

  @Override
  public String toString() {
    return String.format("Time to solve: %d ms\n Total cases generated: %d kasus", solvingTime,
        cases);
  }

}
