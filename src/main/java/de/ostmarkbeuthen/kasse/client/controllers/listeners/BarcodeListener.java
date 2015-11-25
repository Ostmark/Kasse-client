package de.ostmarkbeuthen.kasse.client.controllers.listeners;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by nussin on 11/19/15.
 */
public class BarcodeListener implements EventHandler<KeyEvent> {

  enum State {
    WAITING,
    PARSING
  }

  State state = State.WAITING;
  final Consumer<String> callback;
  String current = "";

  public BarcodeListener(Consumer<String> callback) {
    super();
    this.callback = callback;
  }

  @Override
  public void handle(KeyEvent keyEvent) {
    if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
      if (state == State.WAITING) {
        if (Objects.equals(keyEvent.getCharacter(), ("\u0002"))) {
          state = State.PARSING;
          keyEvent.consume();
        }
      } else {
        keyEvent.consume();
        if (Objects.equals(keyEvent.getCharacter(), "\u0003")) {
          callback.accept(current);
          current = "";
          state = state.WAITING;
        } else {
          current = current.concat(keyEvent.getCharacter());
        }
      }
    }
  }
}
