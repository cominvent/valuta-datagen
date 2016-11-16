package com.ciber.skatt;

import com.google.gson.Gson;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
      InputStream is = new ClassPathResource("rates.json").getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      convert = (Map<String, Double>) gson.fromJson(br, Map.class).get("rates");
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
