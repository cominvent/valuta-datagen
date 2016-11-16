package com.ciber.skatt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by janhoy on 16.11.2016.
 */
public class Country {
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

  private final String name;
  private final String code;
  private final String currency;
  private final String currencyCode;
  
  private static List<Country> countries;

  static {
    try {
      countries = read();
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
    try {
      List<Country> countries = new ArrayList<>();
      Path p = Paths.get(Thread.currentThread().getContextClassLoader().getResource("countries.csv").toURI());
      for (String line : Files.readAllLines(p)) {
        String[] cols = line.split(",");
        countries.add(new Country(cols[0], cols[1], cols[2], cols[3]));
      }
      return countries;
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
  }
}
