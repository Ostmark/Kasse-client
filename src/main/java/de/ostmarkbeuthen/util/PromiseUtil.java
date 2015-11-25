package de.ostmarkbeuthen.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by nussin on 10/28/15.
 */
public class PromiseUtil {
  public static Promise<HttpResponse<JsonNode>, Exception, Void> promisifyUnirest(Function<Callback<JsonNode>, Object> f) {
    final Deferred<HttpResponse<JsonNode>, Exception, Void> d
        = new DeferredObject<>();
    f.apply(new Callback<JsonNode>() {
      public void completed(HttpResponse<JsonNode> httpResponse) {
        d.resolve(httpResponse);
      }

      public void failed(UnirestException e) {
        d.reject(e);
      }

      public void cancelled() {
        d.reject(new NotImplementedException());
      }
    });
    return d.promise();

  }

  public static <R, E, P> Promise<R, E, P> promiseWhile(
     Promise<R, E, P> promise,
     BiFunction<
       Promise<R, E, P>,
       Consumer<Promise<R, E, P>>,
       Promise<R, E, P>> func) {
    return null;
  }


}
