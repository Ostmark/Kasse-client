package de.ostmarkbeuthen.kasse.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by nussin on 11/4/15.
 */
public class IndexController extends Controller {
  @FXML
  Button checkout;
  @FXML
  Button deposit;
  @FXML
  Button signup;

  @FXML
  protected void handle(ActionEvent event) {
    String id = ((Button) event.getSource()).getId();
    System.out.println(id);
    Platform.runLater(() -> {
      try {
        supervisor.switchView("fxml/" + id + ".fxml");
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public void initialize() {

  }


}
