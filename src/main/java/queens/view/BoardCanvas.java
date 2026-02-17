package queens.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.geometry.VPos;
import queens.model.Board;
import queens.model.Position;
import queens.model.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardCanvas extends Canvas {

  private static final int CELL_SIZE = 50;
  private static final int MARGIN = 30;

  private Board board;
  private Solution solution;
  private Map<Character, Color> regionColors;

  public BoardCanvas(int size) {
    super(size * CELL_SIZE + 2 * MARGIN, size * CELL_SIZE + 2 * MARGIN);
    this.regionColors = new HashMap<>();
    initializeColors();

    // Draw initial placeholder
    GraphicsContext gc = getGraphicsContext2D();
    gc.setFill(Color.LIGHTGRAY);
    gc.fillRect(0, 0, getWidth(), getHeight());
    gc.setFill(Color.DARKGRAY);
    gc.setFont(new Font("Arial", 16));
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);
    gc.fillText("Select a file to display the board", getWidth() / 2, getHeight() / 2);
  }

  private void initializeColors() {
    Color[] colors = {Color.rgb(255, 182, 193), // Light Pink
        Color.rgb(173, 216, 230), // Light Blue
        Color.rgb(144, 238, 144), // Light Green
        Color.rgb(255, 218, 185), // Peach
        Color.rgb(221, 160, 221), // Plum
        Color.rgb(255, 255, 224), // Light Yellow
        Color.rgb(255, 228, 196), // Bisque
        Color.rgb(176, 224, 230), // Powder Blue
        Color.rgb(240, 230, 140), // Khaki
        Color.rgb(255, 192, 203), // Pink
        Color.rgb(230, 230, 250), // Lavender
        Color.rgb(152, 251, 152), // Pale Green
        Color.rgb(255, 239, 213), // Papaya Whip
        Color.rgb(255, 228, 225), // Misty Rose
        Color.rgb(224, 255, 255) // Light Cyan
    };

    for (int i = 0; i < 26; i++) {
      char region = (char) ('A' + i);
      regionColors.put(region, colors[i % colors.length]);
    }
  }

  public void setBoard(Board board) {
    this.board = board;
    this.solution = null;
    draw();
  }

  public void setSolution(Solution solution) {
    this.solution = solution;
    draw();
  }

  public void clear() {
    this.board = null;
    this.solution = null;
    GraphicsContext gc = getGraphicsContext2D();
    gc.clearRect(0, 0, getWidth(), getHeight());
  }

  private void draw() {
    if (board == null) {
      return;
    }

    GraphicsContext gc = getGraphicsContext2D();
    int size = board.getSize();

    // Update canvas size
    setWidth(size * CELL_SIZE + 2 * MARGIN);
    setHeight(size * CELL_SIZE + 2 * MARGIN);

    // Clear canvas with white background
    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, getWidth(), getHeight());

    // Draw grid with regions
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        double x = MARGIN + col * CELL_SIZE;
        double y = MARGIN + row * CELL_SIZE;

        // Get region and color
        char region = board.getRegion(row, col);
        Color regionColor = regionColors.getOrDefault(region, Color.LIGHTGRAY);

        // Fill cell with region color
        gc.setFill(regionColor);
        gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);

        // Draw cell border
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1.5);
        gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);

      }
    }

    // Draw row and column labels
    gc.setFill(Color.BLACK);
    gc.setFont(new Font("Arial", 12));
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);

    for (int i = 0; i < size; i++) {
      // Row labels (left side)
      gc.fillText(String.valueOf(i), MARGIN / 2, MARGIN + i * CELL_SIZE + CELL_SIZE / 2);
      // Column labels (top)
      gc.fillText(String.valueOf(i), MARGIN + i * CELL_SIZE + CELL_SIZE / 2, MARGIN / 2);
    }

    // Draw queens if solution exists
    if (solution != null) {
      drawQueens(gc, solution.getQueenPositions());
    }
  }

  private void drawQueens(GraphicsContext gc, List<Position> queens) {
    gc.setFill(Color.BLACK);
    gc.setFont(new Font("Arial", 32));
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);

    for (Position queen : queens) {
      double x = MARGIN + queen.getCol() * CELL_SIZE + CELL_SIZE / 2;
      double y = MARGIN + queen.getRow() * CELL_SIZE + CELL_SIZE / 2;
      gc.fillText("♛", x, y);
    }
  }
}
