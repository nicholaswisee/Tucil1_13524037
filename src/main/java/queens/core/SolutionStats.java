package queens.core;

public class SolutionStats {

  private int cases;
  private long solvingTime;
  private boolean solutionFound;

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

}
