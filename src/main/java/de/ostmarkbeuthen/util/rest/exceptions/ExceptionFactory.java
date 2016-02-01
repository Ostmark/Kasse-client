package de.ostmarkbeuthen.util.rest.exceptions;

import javax.inject.Inject;

/**
 * Created by nussin on 12/2/15.
 */
public class ExceptionFactory {
  @Inject
  public ExceptionFactory() {}
  public RestException exceptionForStatus(int status, String resource) {
    switch(status) {
      case 404: return new NotFoundException(String.format("404: %s Not Found", resource));
      case 401: return new BadRequestException(String.format("401: Bad Request"));
      case 402: return new AuthException(String.format("402: Auth Failed"));
      case 422: return new PaymentException(String.format("422: Not Enough Credit"));
      default: return null;
    }
  }
}
