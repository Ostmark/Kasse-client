package de.ostmarkbeuthen.util.rest.exceptions;

/**
 * Created by nussin on 11/3/15.
 */
public class BadRequestException extends RestException {
  public BadRequestException(String msg) {
    super(msg);
  }
}
