package com.ciber.skatt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by janhoy on 16.11.2016.
 */
public class Country {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private String name;
  private String code;
  private String currency;
  private String currencyCode;
  private String location;
  
  private static List<Country> countries;

  private static Map<String, String> geo;

  static {
    try {
      countries = read();
      geo = readGeo();
      for (Country c : countries) {
        if (geo.containsKey(c.getCode())) {
          c.setLocation(geo.get(c.getCode()));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  Country(String name, String code, String currency, String currencyCode) {
    this.name = name;
    this.code = code;
    this.currency = currency;
    this.currencyCode = currencyCode;
  }

  public Country(String[] arr) {
    this(arr[0], arr[1], arr[2], arr[3]);
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public static List<Country> getCountries() {
    return countries;
  }
  
  public static Country get(String code) {
    return countries.stream().filter(c -> c.code.equals(code)).findFirst().orElse(null);
  }
  
  public static Country getRandom(Random r) {
    return countries.get(r.nextInt(countries.size()));
  }
  
  public static int getNumCountries() {
    return countries.size();
  }
  
  public static List<Country> read() throws IOException {
    InputStream is = ClassLoader.getSystemResourceAsStream("countries.csv");
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    return br.lines().map(l -> new Country(l.split(","))).collect(Collectors.toList());
  }

  public static Map<String, String> readGeo() throws IOException {
    final Map<String,String> geos = new HashMap<>();
    InputStream is = ClassLoader.getSystemResourceAsStream("country_location.csv");
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    br.lines().map(l -> l.split("\t")).forEach(a -> geos.put(a[0], a[1]+","+a[2]));
    return geos;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
