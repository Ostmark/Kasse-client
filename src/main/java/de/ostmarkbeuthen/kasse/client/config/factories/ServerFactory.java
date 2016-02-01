package de.ostmarkbeuthen.kasse.client.config.factories;

import com.google.inject.Provider;
import de.ostmarkbeuthen.kasse.client.config.Server;

import java.io.*;
import java.util.Properties;

/**
 * Created by nussin on 12/2/15.
 */
public class ServerFactory implements Provider<Server> {
  Server s;

  class ServerImpl extends Server {
    ServerImpl(String domain, String port, boolean https) {
      super(String.format("%s://%s:%s/", https ? "https" : "http", domain, port), domain, port, https);
    }
  }
  ServerFactory() throws IOException {
    File file = new File("config.properties");
    InputStream config_instr;
    Properties default_props = new Properties();
    default_props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
    Properties props = new Properties(default_props);
    if (file.exists()) {
      config_instr = new FileInputStream(file);
      props.load(config_instr);
    }
    s = new ServerImpl(
      props.getProperty("domain"),
      props.getProperty("port"),
      Boolean.parseBoolean(props.getProperty("https")));
  }

  @Override
  public Server get() {
    return s;
  }
}
