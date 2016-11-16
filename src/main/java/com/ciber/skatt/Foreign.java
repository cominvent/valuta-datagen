package com.ciber.skatt;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by janhoy on 16.11.2016.
 */
public class Foreign extends Person {
  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public Country getCountry() {
    return country;
  }

  private String name;
  private String id;
  private Country country;

  public static Foreign getRandom(Random random) {
    Foreign f = new Foreign();
    f.firstName = RandomUtil.randomFirstName();
    f.lastName = RandomUtil.randomLastName();
    f.birthDate = RandomUtil.randomBirthDate();
    f.id = RandomUtil.randomSsn(f.birthDate);
    f.kontoNr = RandomUtil.randomAccount();
    f.country = Country.getRandom(random);
    return f;
  }
  
  public String toString() {
    Map<String,Object> map = new HashMap<>();
    map.put("name", name);
    map.put("id", id);
    map.put("country", country.getName());
    map.put("currency", country.getCurrencyCode());
    return new Gson().toJson(map);
  }
  
}
