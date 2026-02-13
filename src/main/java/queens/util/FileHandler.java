package queens.util;

import queens.core.Board;
import queens.core.Solution;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

  // Read board from file
  public static Board readBoardFromFile(String filename) throws IOException {
    // If filename has no path separator, prepend test/inputs/
    String filepath = filename;
    if (!filename.contains("/") && !filename.contains("\\")) {
      filepath = "test/inputs/" + filename;
    }

    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty()) {
          lines.add(line);
        }
      }
    }

    if (lines.isEmpty()) {
      throw new IOException("File is empty or contains no valid board data");
    }

    return new Board(lines);
  }

  // Save solution to file
  public static void saveSolution(String filename, Board board, Solution solution)
      throws IOException {
    // If filename has no path separator, prepend test/outputs/
    String filepath = filename;
    if (!filename.contains("/") && !filename.contains("\\")) {
      filepath = "test/outputs/" + filename;
    }

    String formattedSolution = BoardFormatter.formatSolution(board, solution.getQueenPositions());

    try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
      writer.println(formattedSolution);
      writer.println();
      writer.println(solution.getStats());
    }
  }

  // Check if file exists
  public static boolean fileExists(String filename) {
    // If filename has no path separator, check in test/inputs/
    String filepath = filename;
    if (!filename.contains("/") && !filename.contains("\\")) {
      filepath = "test/inputs/" + filename;
    }
    return Files.exists(Paths.get(filepath));
  }
}
