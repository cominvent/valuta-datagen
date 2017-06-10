package com.ciber.skatt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.ciber.skatt.AbstractGenerator.random;

/**
 * Created by janhoy on 16.11.2016.
 */
public class RandomUtil {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static List<String> boys_no = readLines("firstname_boy_no.csv");
  private static List<String> girls_no = readLines("firstname_girl_no.csv");
  private static List<String> lasts_no = readLines("lastnames_no.csv");

  private static List<String> boys = readLines("male.txt");
  private static List<String> girls = readLines("female.txt");
  private static List<String> lasts = readLines("last.txt");

  private static List<String> occu = readLines("occupations.txt");
  private static List<String> adj = readLines("adjectives.txt");

  private static List<String> city_no = readLines("city_no.csv");
  private static List<String> gater_no = readLines("gatenavn.txt");
  private static List<String> streets = readLines("streetnames.txt");

  
  public static String randomNameNo(Random r) {
    boolean boy = r.nextBoolean();
    String first, last;
    first = randomFirstNameNo();
    last = lasts_no.get(r.nextInt(lasts_no.size()));
    return first + " " + last;
  }

  public static String randomName(Random r) {
    String first, last;
    first = randomFirstName();
    last = lasts.get(r.nextInt(lasts.size()));
    return first + " " + last;
  }

  public static String randomCompanyName(Random r) {
    return adj.get(r.nextInt(adj.size())) + " " + occu.get(r.nextInt(occu.size()));
  }
  
  public static List<String> readLines(String file) {
    try {
      InputStream is = ClassLoader.getSystemResourceAsStream(file);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      return br.lines().collect(Collectors.toList());
    } catch (Exception e) {
      System.err.println("Failed to find file "+file);
      e.printStackTrace();
      return null;
    }
  }

  public static String randomSsn(String bdate) {
    return bdate + "12345";
  }
  
  public static String getRandomDate() {
    int minDay = (int) LocalDate.of(2005, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2017, 6, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);
    LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
    return randomBirthDate.format(DateTimeFormatter.ofPattern("ddMMyy"));
  }

  public static String randomOrgNo(Random random) {
    return "9"+digits(8);
  }

  public static String randomAccount() {
    return digits(11);
  }

  private static String digits(int num) {
    num--;
    return ""+Math.round(random.nextFloat()*(Math.pow(10, num)-1)+Math.pow(10, num));
  }

  public static String randomFirstNameNo() {
    boolean boy = random.nextBoolean();
    String first;
    if (boy) {
      first = boys_no.get(random.nextInt(boys_no.size()));
      if (random.nextInt(10) > 6) {
        first += random.nextBoolean()?" ":"-" + boys_no.get(random.nextInt(boys_no.size()));
      }
    } else {
      first = girls_no.get(random.nextInt(girls_no.size()));
      if (random.nextInt(10) > 6) {
        first += random.nextBoolean()?" ":"-" + girls_no.get(random.nextInt(girls_no.size()));
      }
    }
    return first;
  }

  public static String randomLastNameNo() {
    return lasts_no.get(random.nextInt(lasts_no.size()));
  }

  public static String randomBirthDate() {
    int minDay = (int) LocalDate.of(1970, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2010, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);
    LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
    return randomBirthDate.format(DateTimeFormatter.ofPattern("ddMMyy"));
  }

  public static String randomFirstName() {
    boolean boy = random.nextBoolean();
    return boy ? boys.get(random.nextInt(boys.size()))
        : girls.get(random.nextInt(girls.size()));
  }
  public static String randomLastName() {
    return lasts.get(random.nextInt(lasts.size()));
  }

  public static String randomCityCSVNo() {
    return city_no.get(random.nextInt(city_no.size()-1)+1);
  }

  public static String randomNorwayStreet() {
    return gater_no.get(random.nextInt(gater_no.size()));
  }

  public static int randomGatenummer() {
    return random.nextInt(200)+1;
  }

  public static int getNumber(int bound) {
    return random.nextInt(bound);
  }

  public static String string(int len) {
    StringBuffer sb = new StringBuffer(len);
    for (int i=0; i<len; i++) {
      char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ".toCharArray();
      sb.append(chars[random.nextInt(28)]);
    }
    return sb.toString();
  }

  public static String randomStreet() {
    return streets.get(random.nextInt(streets.size()));
  }
}
