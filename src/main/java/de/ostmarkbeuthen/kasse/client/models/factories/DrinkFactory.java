package de.ostmarkbeuthen.kasse.client.models.factories;


import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.BaseRequest;
import de.ostmarkbeuthen.kasse.client.config.Server;
import de.ostmarkbeuthen.util.PromiseUtil;
import de.ostmarkbeuthen.util.rest.exceptions.NotFoundException;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONObject;

import de.ostmarkbeuthen.kasse.client.models.Drink;


import org.jdeferred.Deferred;
import org.jdeferred.Promise;

import javax.inject.Inject;

/**
 * Created by nussin on 10/28/15.
 */
public class DrinkFactory {

  @Inject
  Server server;

  @Inject
  public DrinkFactory() {
  }

  public Promise<Drink, Exception, Void> getDrink(final String barcode) {
    BaseRequest u = Unirest.get("{url}/drink/{barcode}")
                        .routeParam("url", server.url)
                        .routeParam("barcode", barcode);
    return PromiseUtil.promisifyUnirest(u::asJsonAsync).then((HttpResponse<JsonNode> res) -> {
      Deferred<Drink, Exception, Void> d = new DeferredObject<>();
      JSONObject root = res.getBody().getObject();
      boolean success =
          res.getStatus() == 200 ||
              !root.has("success") ||
              root.getBoolean("success");
      if (!success)
        switch (res.getStatus()) {
          case 404:
            return d.reject(new NotFoundException(""));
          default:
            return d.reject(new Exception(""));
        }

      String name = root.getString("name"),
          amount = root.getString("amount");
      int cost = root.getInt("cost");
      return d.resolve(new DrinkImpl(barcode, name, amount, cost));
    });
  }

  public Promise<Drink, Exception, Void> createDrink(
    String barcode,
    String name,
    String amount,
    int cost) {

    JSONObject drink = new JSONObject()
      .put("name", name)
      .put("amount", amount)
      .put("cost", cost);
    BaseRequest req = Unirest.post("{url}/drink/{barcode}")
                          .routeParam("url", server.url)
                          .routeParam("barcode", barcode)
                          .body(new JsonNode(drink.toString()));
    return PromiseUtil.promisifyUnirest(req::asJsonAsync)
               .then((HttpResponse<JsonNode> res) -> new DrinkImpl(barcode, name, amount, cost));
  }

  private class DrinkImpl implements Drink {
    public int getCost() {
      return cost;
    }

    public String getBarcode() {
      return barcode;
    }

    public String getName() {
      return name;
    }

    public String getAmount() {
      return amount;
    }

    public final String barcode, name, amount;
    public final int cost;

    DrinkImpl(
                 String barcode,
                 String name,
                 String amount,
                 int cost) {
      this.barcode = barcode;
      this.name = name;
      this.amount = amount;
      this.cost = cost;
    }
  }
}
