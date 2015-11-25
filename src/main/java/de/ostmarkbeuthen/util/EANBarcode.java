package de.ostmarkbeuthen.util;

/**
 * Created by nussin on 11/25/15.
 */
public class EANBarcode {
  public enum CountryCode {
    GERMANY,
    INTERNAL,
    OTHER
  }

  public final String barcode;
  public final CountryCode countryCode;
  public final boolean valid;

  public EANBarcode (String s) {
    barcode = s;
    int country_candidate = Integer.parseInt(barcode.substring(2));
    if (country_candidate >= 20 && country_candidate < 30) {
      countryCode = CountryCode.INTERNAL;
    } else if (country_candidate >= 40 && country_candidate < 45) {
      countryCode = CountryCode.GERMANY;
    } else {
      countryCode = CountryCode.OTHER;
    }
     valid = false;
  }

  static int calculateCheckSum (String barcode) {
    int checksum = 0;

    final String GTIN_PADDING = "00000000000000";

    String GTIN = GTIN_PADDING.substring(GTIN_PADDING.length() - barcode.length()) + barcode;

    for (int i = GTIN.length() - 1; i >= 0; i--) {

    }

    return checksum;
  }

  @Override
  public String toString() {
    return barcode;
  }
}
