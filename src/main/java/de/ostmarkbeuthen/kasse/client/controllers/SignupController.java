package de.ostmarkbeuthen.kasse.client.controllers;

import de.ostmarkbeuthen.kasse.client.controllers.listeners.BarcodeListener;
import de.ostmarkbeuthen.kasse.client.models.PassAuth;
import de.ostmarkbeuthen.kasse.client.models.User;
import de.ostmarkbeuthen.kasse.client.models.factories.UserFactory;
import de.ostmarkbeuthen.util.EANBarcode;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.awt.event.TextEvent;
import java.io.IOException;

/**
 * Created by nussin on 12/9/15.
 */
public class SignupController extends Controller {

  @FXML
  TextField name;
  @FXML
  TextField username;
  @FXML
  PasswordField password;
  @FXML
  TextField barcodeField;

  @FXML
  Button signup;


  @FXML
  Pane pane;

  EANBarcode barcode;

  @Inject
  UserFactory userFactory;

  @FXML
  protected void initialize () {
    pane.addEventHandler(KeyEvent.KEY_TYPED, new BarcodeListener(this::handleBarcode));
    name.setOnAction(this::checkStatus);
    username.setOnAction(this::checkStatus);
    password.setOnAction(this::checkStatus);
  }

  @Override
  public void injectUserData(Object userData) {
    super.injectUserData(userData);
  }

  protected void checkStatus (Event e) {
    checkStatus();
  }

  protected void checkStatus() {
    if (barcode != null && name.getText().equals("") && username.getText().equals("") && password.getText().equals("")) {
      signup.setDisable(false);
    } else {
      signup.setDisable(true);
    }
  }

  protected void handleBarcode(String s) {
    barcode = new EANBarcode(s);
    barcodeField.setText(s);
  }

  @FXML
  protected void signup () {
    userFactory
      .createUser(barcode, name.getText(), new PassAuth(username.getText(), password.getText()))
      .done((User u) -> {
        
      });
  }

}
