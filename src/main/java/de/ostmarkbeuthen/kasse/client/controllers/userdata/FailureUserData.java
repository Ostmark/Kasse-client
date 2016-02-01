package de.ostmarkbeuthen.kasse.client.controllers.userdata;

/**
 * Created by nussin on 1/28/16.
 */
public class FailureUserData {
  public final String message;
  public final String retryView;
  public final String cancelView;

  public FailureUserData(String message, String retryView, String cancelView) {
    this.message = message;
    this.retryView = retryView;
    this.cancelView = cancelView;
  }
}
