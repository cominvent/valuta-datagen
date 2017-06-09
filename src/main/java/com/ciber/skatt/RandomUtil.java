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
  
  public static String randomNameNo(Random r) {
    boolean boy = r.nextBoolean();
    String first, last;
    if (boy)
      first = boys_no.get(r.nextInt(boys_no.size()));
    else
      first = girls_no.get(r.nextInt(girls_no.size()));
    last = lasts_no.get(r.nextInt(lasts_no.size()));
    return first + " " + last;
  }

  public static String randomName(Random r) {
    boolean boy = r.nextBoolean();
    String first, last;
    if (boy)
      first = boys.get(r.nextInt(boys.size()));
    else
      first = girls.get(r.nextInt(girls.size()));
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
    return digits(4)+"."+digits(2)+"."+digits(5);
  }

  private static String digits(int num) {
    num--;
    return ""+Math.round(random.nextFloat()*(Math.pow(10, num)-1)+Math.pow(10, num));
  }

  public static String randomFirstNameNo() {
    boolean boy = random.nextBoolean();
    return boy ? boys_no.get(random.nextInt(boys_no.size()))
        : girls_no.get(random.nextInt(girls_no.size()));
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
  
}
