package queens.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
  private final char[][] regions;
  private final int size;
  private final Map<Character, List<Position>> regionMap;

  public Board(char[][] regions) {
    this.regions = regions;
    this.size = regions.length;
    this.regionMap = new HashMap<>();
    buildRegionMap();
  }

  public Board(List<String> lines) {
    this.size = lines.size();
    this.regions = new char[size][size];
    this.regionMap = new HashMap<>();

    for (int i = 0; i < size; i++) {
      String line = lines.get(i);
      for (int j = 0; j < size && j < line.length(); j++) {
        regions[i][j] = line.charAt(j);
      }
    }

    buildRegionMap();
  }

  private void buildRegionMap() {
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        char region = regions[row][col];
        regionMap.computeIfAbsent(region, k -> new ArrayList<>()).add(new Position(row, col));
      }
    }
  }

  public int getSize() {
    return size;
  }

  public char getRegion(int row, int col) {
    if (row < 0 || row >= size || col < 0 || col >= size) {
      throw new IllegalArgumentException("Position out of bounds");
    }
    return regions[row][col];
  }

  // Get region color of position
  public char getRegion(Position pos) {
    return getRegion(pos.getRow(), pos.getCol());
  }

  // Gets all positions that belong to a specific region.
  public List<Position> getRegionPositions(char region) {
    return regionMap.getOrDefault(region, new ArrayList<>());
  }


  // Gets all unique regions on the board.
  public List<Character> getRegions() {
    return new ArrayList<>(regionMap.keySet());
  }

  // Gets the region grid as a 2D array.
  public char[][] getRegionGrid() {
    char[][] copy = new char[size][size];
    for (int i = 0; i < size; i++) {
      System.arraycopy(regions[i], 0, copy[i], 0, size);
    }
    return copy;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        sb.append(regions[i][j]);
      }
      if (i < size - 1) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }
}
