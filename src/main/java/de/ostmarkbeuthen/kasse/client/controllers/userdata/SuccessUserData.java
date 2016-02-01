package de.ostmarkbeuthen.kasse.client.controllers.userdata;

/**
 * Created by nussin on 1/28/16.
 */
public class SuccessUserData {
  public final String message;
  public final String nextView;

  public SuccessUserData(String message, String nextView) {
    this.message = message;
    this.nextView = nextView;
  }
}
