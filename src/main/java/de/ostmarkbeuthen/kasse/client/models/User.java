package de.ostmarkbeuthen.kasse.client.models;

import de.ostmarkbeuthen.util.EANBarcode;
import org.json.JSONObject;

import java.util.Optional;

/**
 * Created by nussin on 12/2/15.
 */
public class User {
  public final Optional<EANBarcode> barcode;
  public final Optional<PassAuth> auth;

  public final JSONObject json;

  public User(EANBarcode barcode, PassAuth auth) {
    this.barcode = Optional.ofNullable(barcode);
    this.auth = Optional.ofNullable(auth);
    json = new JSONObject();
    if (barcode != null) {
      json.put("barcode", barcode.toString());
    }
    if (auth != null) {
      json.put("name", auth.name);
      json.put("password", auth.password);
    }
  }

  public User(EANBarcode barcode) {
    this(barcode, null);
  }

  public User(String name, String password) {
    this(null, new PassAuth(name, password));
  }
}
