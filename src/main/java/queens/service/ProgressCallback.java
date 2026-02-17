package queens.service;

import queens.model.Position;
import java.util.List;

@FunctionalInterface
public interface ProgressCallback {
  void onProgress(int iterations, List<Position> currentPositions);
}
