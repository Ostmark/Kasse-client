package de.ostmarkbeuthen.kasse.client.controllers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.ostmarkbeuthen.kasse.client.AppModule;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

/**
 * Created by nussin on 10/29/15.
 */
public class SupervisorController {
  final ClassLoader classLoader;
  final Stage stage;
  final Injector injector;

  public void switchView(String view) throws IOException {

    switchView(view, null);

  }

  public void switchView(String view, Object userData) throws IOException {

    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setControllerFactory(injector::getInstance);

    URL url = classLoader.getResource(view);
    fxmlLoader.setLocation(url);

    Pane p = fxmlLoader.load();
    ((Controller) fxmlLoader.getController()).setControllerController(this);
    ((Controller) fxmlLoader.getController()).injectUserData(userData);

    Scene s = new Scene(p);
    Platform.runLater(() -> stage.setScene(s));

  }

  @Inject
  SupervisorController(
                          ClassLoader classLoader,
                          Stage stage) {
    this.classLoader = classLoader;
    this.stage = stage;
    injector = Guice.createInjector(new AppModule(stage));


  }
}