package queens.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import queens.controller.MainController;

public class QueensGUI extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      // Load FXML view
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/queens/gui/MainView.fxml"));
      Parent root = loader.load();

      // Get controller and set stage
      MainController controller = loader.getController();
      controller.setStage(primaryStage);

      // Setup scene and stage
      Scene scene = new Scene(root);
      primaryStage.setTitle("LinkedIn Queens Solver");
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch (Exception e) {
      System.err.println("Error loading FXML: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
