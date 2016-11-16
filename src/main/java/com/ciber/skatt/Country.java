package com.ciber.skatt;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by janhoy on 16.11.2016.
 */
public class Country {
  private String name;
  private String code;
  private String currency;
  private String currencyCode;
  
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

  public Country(String[] arr) {
    new Country(arr[0], arr[1], arr[2], arr[3]);
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
    InputStream is = new ClassPathResource("countries.csv").getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    return br.lines().map(l -> new Country(l.split(","))).collect(Collectors.toList());
  }
}
