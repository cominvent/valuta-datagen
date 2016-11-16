package com.ciber.skatt;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by janhoy on 16.11.2016.
 */
public class ExchangeRates {
  private static String ratesStr = "";
  private static Map<String,Double> convert;
  
  static {
    Gson gson = new Gson();
    try {
      ratesStr = new String(Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader().getResource("rates.json").toURI())), "UTF-8");
      convert = (Map<String, Double>) gson.fromJson(ratesStr, Map.class).get("rates");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static double nokTo(double nok, String currency) {
    if (convert.containsKey(currency)) {
      double usd = nok / convert.get("NOK");
      return usd * convert.get(currency);
    } else {
      return nok;
    }
  }
}
