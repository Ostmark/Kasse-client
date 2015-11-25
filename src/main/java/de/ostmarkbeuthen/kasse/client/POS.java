package de.ostmarkbeuthen.kasse.client;

/**
 * Created by nussin on 10/28/15.
 */

import com.google.inject.Guice;
import de.ostmarkbeuthen.kasse.client.controllers.SupervisorController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class POS extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    SupervisorController controller =
        Guice
            .createInjector(new AppModule(primaryStage))
            .getInstance(SupervisorController.class);
    controller.switchView("fxml/index.fxml");
    primaryStage.show();
  }
}
