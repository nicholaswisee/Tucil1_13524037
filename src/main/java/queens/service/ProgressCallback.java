package queens.service;

@FunctionalInterface
public interface ProgressCallback {
  void onProgress(int iterations);
}
