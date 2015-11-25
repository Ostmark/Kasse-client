package de.ostmarkbeuthen.kasse.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

/**
 * Created by nussin on 11/5/15.
 */
public class LoginController extends Controller {

  public static class User {
    final String name;
    final String password;

    User(String name, String password) {
      this.name = name;
      this.password = password;
    }
  }

  Consumer<User> callback;
  @FXML
  TextField username;
  @FXML
  PasswordField password;

  @FXML
  void login(ActionEvent e) {
    callback.accept(new User(username.getText(), password.getText()));
  }

  @Override
  public void injectUserData(Object userData) {
    assert userData instanceof Consumer;
    callback = (Consumer<User>) userData;
  }


}
