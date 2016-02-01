package de.ostmarkbeuthen.kasse.client.config;

import com.mashape.unirest.http.Unirest;

/**
 * Created by nussin on 12/2/15.
 */
public abstract class Server {
  public final String url;
  public final String domain;
  public final String port;

  public final boolean https;

  protected Server(String url, String domain, String port, boolean https) {
    this.url = url;
    this.domain = domain;
    this.port = port;
    this.https = https;
  }
}
