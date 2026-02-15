package queens.core;

import java.util.Objects;

public class Position {
  private final int row;
  private final int col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  // Checks if position is on same row, col, diagonal, or color
  public boolean attacks(Position other) {
    if (this.equals(other)) {
      return false;
    }

    // Same column
    if (this.col == other.col) {
      return true;
    }

    // Same diagonal (absolute difference of rows equals absolute difference columns)
    if (Math.abs(this.row - other.row) == 1 && Math.abs(this.col - other.col) == 1) {
      return true;
    }
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Position position = (Position) o;
    return row == position.row && col == position.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }

  @Override
  public String toString() {
    return "(" + row + "," + col + ")";
  }
}
