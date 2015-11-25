package de.ostmarkbeuthen.kasse.client;

import com.google.inject.AbstractModule;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Created by nussin on 11/3/15.
 */
public class AppModule extends AbstractModule {
  final Stage stage;

  public AppModule(Stage stage) {
    this.stage = stage;
  }

  @Override
  protected void configure() {
    bind(ClassLoader.class).toInstance(getClass().getClassLoader());
    bind(Stage.class).toInstance(stage);
  }
}
