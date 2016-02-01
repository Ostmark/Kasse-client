package de.ostmarkbeuthen.kasse.client;

import com.google.inject.AbstractModule;
import de.ostmarkbeuthen.kasse.client.config.Server;
import de.ostmarkbeuthen.kasse.client.config.factories.ServerFactory;
import de.ostmarkbeuthen.kasse.client.models.Drink;
import de.ostmarkbeuthen.kasse.client.models.DrinkStatic;
import de.ostmarkbeuthen.kasse.client.models.factories.DrinkFactory;
import de.ostmarkbeuthen.util.rest.exceptions.ExceptionFactory;
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
    bind(Server.class).toProvider(ServerFactory.class);
    //bind(DrinkFactory.class).toInstance(new DrinkFactory());
    bind(DrinkStatic.class).toInstance(new DrinkStatic());
    bind(ExceptionFactory.class).toInstance(new ExceptionFactory());
  }
}
