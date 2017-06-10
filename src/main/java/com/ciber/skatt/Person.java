package com.ciber.skatt;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by janhoy on 16.11.2016.
 */
public class Person {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  protected String kontoNr;
  protected String adresse;

  public String getBirthDate() {
    return birthDate;
  }

  protected String birthDate;

  public String getName() {
    return name;
  }

  public String getSsn() {
    return ssn;
  }

  protected String name;
  protected String firstName;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  protected String lastName;
  protected String ssn;
  protected String city;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  protected String postcode;

  public Instant getFromDate() {
    return fromDate;
  }

  public Instant getToDate() {
    return toDate;
  }

  protected Instant fromDate = Instant.parse("2010-01-01T00:00:00Z");
  protected Instant toDate = Instant.parse("2016-11-01T00:00:00Z");

  public List<Integer> getLinks() {
    return links;
  }

  protected List<Integer> links;

  public static Person getRandom(Random random) {
    Person p = new Person();
    p.firstName = RandomUtil.randomFirstNameNo();
    p.lastName = RandomUtil.randomLastNameNo();
    p.birthDate = RandomUtil.randomBirthDate();
    String csv = RandomUtil.randomCityCSVNo();
    p.city = csv.split(";")[1];
    p.postcode = csv.split(";")[0];
    p.adresse = RandomUtil.randomNorwayStreet() + " " + RandomUtil.randomGatenummer();
    p.ssn = RandomUtil.randomSsn(p.birthDate);
    p.kontoNr = RandomUtil.randomAccount();
    p.fromDate = Instant.ofEpochSecond(random.nextInt(1479339500));
    p.toDate = Instant.ofEpochSecond(random.nextInt(1479339500));
    if (p.fromDate.isAfter(p.toDate)) {
      Instant from = p.fromDate;
      p.fromDate = p.toDate;
      p.toDate = from;
    }
      
    return p;
  }

  public void setLinks(List<Integer> links) {
    this.links = links;
  }
  
  public String toString() {
    Map<String,Object> map = new HashMap<>();
    if (name != null)
      map.put("name", name);
    if (firstName != null) {
      map.put("firstName", firstName);
      map.put("lastName", lastName);
    }
    map.put("kontonummer", kontoNr);
    map.put("ssn", ssn);
    map.put("fromDate", fromDate);
    map.put("toDate", toDate);
    map.put("links", links);
    return new Gson().toJson(map);
  }

  public String getKontoNr() {
    return kontoNr;
  }

  public void setKontoNr(String kontoNr) {
    this.kontoNr = kontoNr;
  }

  public String getAdresse() {
    return adresse;
  }
}
