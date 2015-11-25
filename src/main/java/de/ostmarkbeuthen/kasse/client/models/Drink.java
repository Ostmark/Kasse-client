package de.ostmarkbeuthen.kasse.client.models;

/**
 * Created by nussin on 10/28/15.
 */
public abstract class Drink {
  static void buy(Drink[] drinks) {

  }

  public abstract String getBarcode();

  public abstract String getName();

  public abstract String getAmount();

  public abstract int getCost();
}
