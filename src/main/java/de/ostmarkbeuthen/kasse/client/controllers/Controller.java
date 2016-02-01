package de.ostmarkbeuthen.kasse.client.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Created by nussin on 10/29/15.
 */
public abstract class Controller {
  SupervisorController supervisor;

  public void setControllerController(SupervisorController c) {
    supervisor = c;
  }

  public void injectUserData(Object userData) {}

   @FXML
  void cancel() throws IOException {
     supervisor.switchView("index");
   }

}
