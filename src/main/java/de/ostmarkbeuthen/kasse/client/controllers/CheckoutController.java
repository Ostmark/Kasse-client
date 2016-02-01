package de.ostmarkbeuthen.kasse.client.controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostmarkbeuthen.kasse.client.controllers.listeners.BarcodeListener;
import de.ostmarkbeuthen.kasse.client.models.Drink;
import de.ostmarkbeuthen.kasse.client.models.DrinkStatic;
import de.ostmarkbeuthen.kasse.client.models.User;
import de.ostmarkbeuthen.kasse.client.models.factories.DrinkFactory;
import de.ostmarkbeuthen.util.EANBarcode;
import de.ostmarkbeuthen.util.rest.exceptions.NotFoundException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import javax.inject.Inject;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by nussin on 10/28/15.
 */
public class CheckoutController extends Controller {
  @FXML
  private Pane pane;
  @FXML
  private TextField barcode;
  @FXML
  private ListView<Drink> order;
  @Inject
  DrinkFactory drinkFactory;
  @Inject
  DrinkStatic drinkStatic;

  ObservableList<Drink> drinks;

  @FXML
  protected void initialize() {
    drinks = FXCollections.observableArrayList();
    order.setItems(drinks);
    order.setCellFactory((ListView<Drink> view) ->
                             new ListCell<Drink>() {
                               @Override
                               protected void updateItem(Drink item, boolean empty) {
                                 super.updateItem(item, empty);
                                 if (item != null) {
                                   this.setText(item.getName());
                                 }
                               }
                             });
    pane.addEventHandler(KeyEvent.KEY_TYPED, new BarcodeListener((String s) -> handleBarcode(new EANBarcode(s))));
  }

  @FXML
  protected void handleCommit(ActionEvent e) {

    try {
      supervisor.switchView("login", (Consumer<LoginController.User>) user -> System.out.println(user.name + " " + user.password));
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  @FXML
  protected void handleBarcode(ActionEvent e) {
    handleBarcode(new EANBarcode(barcode.getText()))
    .always((Promise.State s, Optional<Drink> a, Exception b) -> {

      if(a == null || a.isPresent())
        Platform.runLater(barcode::requestFocus);
    });
  }

  protected Promise<Optional<Drink>, Exception, Void> handleBarcode(EANBarcode b) {
    if (b.countryCode == EANBarcode.CountryCode.INTERNAL)
      return drinkFactory
        .getDrink(b.barcode)
        .done(
          (Drink val) -> Platform.runLater(() -> {
            drinks.add(val);
          }))
        .then(
          (Drink val) -> new DeferredObject<Optional<Drink>, Exception, Void>().resolve(Optional.of(val))
        )
        .fail((Exception exception) -> {
          System.out.print(exception.toString());
          Alert alert = null;
          if (exception instanceof ConnectException) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Verbindung mit Server fehlgeschlagen");
            alert.setTitle("Verbindung mit Server fehlgeschlagen");
          }
          if (exception instanceof UnirestException) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Verbindung mit Server fehlgeschlagen");
            alert.setTitle("Verbindung mit Server fehlgeschlagen");
          }
          if (exception instanceof NotFoundException) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Getränk nicht gefunden");
            alert.setTitle("Getränk nicht gefunden");
            alert.setContentText("Das Getränk mit diesem Barcode scheint nicht zu existieren");
          }
          if (alert != null)
            Platform.runLater(alert::showAndWait);
        });
    else {
      User u = new User(b);
      return drinkStatic.buy(drinks, u)
        .fail((Exception e) -> new DeferredObject<Optional<Drink>, Exception, Void>().reject(e).promise())
        .then((Void v) -> {
          try {
            supervisor.switchView("index");
          } catch (IOException e) {
            return new DeferredObject<Optional<Drink>, Exception, Void>().reject(e);
          }
          return new DeferredObject<Optional<Drink>, Exception, Void>().resolve(Optional.empty()).promise();
        });

    }
  }
}
