package de.ostmarkbeuthen.kasse.client.models.factories;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import de.ostmarkbeuthen.kasse.client.config.Server;
import de.ostmarkbeuthen.kasse.client.models.PassAuth;
import de.ostmarkbeuthen.kasse.client.models.User;
import de.ostmarkbeuthen.util.EANBarcode;
import de.ostmarkbeuthen.util.PromiseUtil;
import de.ostmarkbeuthen.util.rest.exceptions.UnknownException;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by nussin on 12/9/15.
 */
public class UserFactory {
  @Inject
  Server s;

  public Promise<User, Exception, Void> createUser(EANBarcode b, String fullname, PassAuth auth) {
    JSONObject body = new JSONObject();
    body.put("full_name", fullname);
    body.put("username", auth.name);
    body.put("barcode", b.toString());

    return PromiseUtil
      .promisifyUnirest(
        Unirest
          .post(s.url + "/user/{user}")
          .routeParam("user", auth.name)
          .body(body.toString())::asJsonAsync)
      .then((HttpResponse<JsonNode> res) -> {
        if (res.getStatus() == 200 && res.getBody().getObject().getBoolean("success")) {
          return new DeferredObject<User, Exception, Void>().resolve(new User(b, auth)).promise();
        } else {
          return new DeferredObject<User, Exception, Void>().reject(new UnknownException("unknown exception")).promise();
        }
      });
  }
}
