package de.ostmarkbeuthen.kasse.client.models;

/**
 * Created by nussin on 12/9/15.
 */
public class PassAuth {
  public final String name;
  public final String password;

  public PassAuth(String name, String password) {
    this.name = name;
    this.password = password;
  }
}
