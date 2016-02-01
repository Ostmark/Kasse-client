package de.ostmarkbeuthen.kasse.client.models;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.BaseRequest;
import de.ostmarkbeuthen.kasse.client.config.Server;
import de.ostmarkbeuthen.util.PromiseUtil;
import de.ostmarkbeuthen.util.rest.exceptions.ExceptionFactory;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by nussin on 12/2/15.
 */
public class DrinkStatic {
  @Inject
  static Server server;

  @Inject
  static ExceptionFactory exceptionFactory;
  public static Promise<Void, Exception, Void> buy(Collection<Drink> drinks, User u) {
    JSONObject req = new JSONObject();

    req.put("drinks", drinks);
    req.put("user", u.json);

    BaseRequest request = Unirest.post ("{url}/buy/").routeParam("url", server.url).body(req);
    return PromiseUtil.promisifyUnirest(request::asJsonAsync).then( (HttpResponse<JsonNode> res) -> {
      if(res.getStatus() == 200) {
        return new DeferredObject<Void, Exception, Void>()
                   .resolve(null)
                   .promise();
      }
      else {
        return new DeferredObject<Void, Exception, Void>()
                   .reject(exceptionFactory.exceptionForStatus(res.getStatus(), "/buy") )
                   .promise();
      }
    });
  }

}
