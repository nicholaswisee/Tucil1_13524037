package queens.model;

public class SolutionStats {

  private int cases;
  private long solvingTime;
  private boolean solutionFound;

  public SolutionStats(long solvingTime, int cases, boolean solutionFound) {
    this.solvingTime = solvingTime;
    this.cases = cases;
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
    return String.format("Waktu pencarian: %d ms%nBanyak kasus yang ditinjau: %d kasus",
        solvingTime, cases);
  }

}
