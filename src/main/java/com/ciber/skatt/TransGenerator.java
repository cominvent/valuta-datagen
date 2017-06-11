package com.ciber.skatt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janhoy on 15.11.2016.
 */
public class TransGenerator extends AbstractGenerator {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  private Config conf;
  private List<Person> persons;
  private List<Organization> orgs;
  private List<Foreign> foreigns;

  public static TransGenerator getInstance() {
    return new TransGenerator();
  }
  
  @Override
  public String generate() {
    boolean person = random.nextInt(100) >= 70;
    if (person)
      return generatePersonTrans();
    else
      return generateOrgTrans();
  }

  private String generateOrgTrans() {
    Organization o = orgs.get(random.nextInt(orgs.size()));
    Foreign f = foreigns.get(o.getLinks().get(random.nextInt(o.getLinks().size())));
    float amountNo = (float) Math.sqrt(random.nextFloat() * 10000000 + 100);
    long span = (o.getToDate().toEpochMilli() - o.getFromDate().toEpochMilli()) / 1000;
    Instant transTime = o.getFromDate().plusSeconds(Math.round(random.nextFloat() * span)); 
    String innut = random.nextBoolean() ? "I" : "U";
    
    Transaction t = new Transaction(o, f, amountNo, transTime, innut);
    return t.toString();
  }

  private String generatePersonTrans() {
    Person p = persons.get(random.nextInt(persons.size()));
    Foreign f = foreigns.get(p.getLinks().get(random.nextInt(p.getLinks().size())));
    float amountNo = (float) Math.sqrt(random.nextFloat() * 100000 + 100);
    long span = (p.getToDate().toEpochMilli() - p.getFromDate().toEpochMilli()) / 1000;
    Instant transTime = p.getFromDate().plusSeconds(Math.round(random.nextFloat() * span)); 
    String innut = random.nextBoolean() ? "I" : "U";
    
    Transaction t = new Transaction(p, f, amountNo, transTime, innut);
    return t.toString();
  }

  @Override
  public void init(Object config) {
    conf = (Config) config;

    foreigns = new ArrayList<>();
    while (foreigns.size() < conf.numForeign) {
      foreigns.add(Foreign.getRandom(random));
    }

    persons = new ArrayList<>();
    while (persons.size() < conf.numPersons) {
      Person p = Person.getRandom(random);
      List<Integer> links = new ArrayList<>();
      int numConns = random.nextInt(conf.maxConnectionsPerEntity)+1; 
      while (links.size() < numConns) {
        links.add(random.nextInt(foreigns.size()));
      }
      p.setLinks(links);
      persons.add(p);
    }
    
    orgs = new ArrayList<>();
    while (orgs.size() < conf.numOrg) {
      Organization o = Organization.getRandom(random);
      List<Integer> links = new ArrayList<>();
      int numConns = random.nextInt(conf.maxConnectionsPerEntity)+1; 
      while (links.size() < numConns) {
        links.add(random.nextInt(foreigns.size()));
      }
      o.setLinks(links);
      orgs.add(o);
    }
    log.info("Initialized person " + persons.size() + ", orgs " + orgs.size() + ", foreign " + foreigns.size());
  }


  public static class Config {
    public long numPersons = 2000000; // 2 mill norwegians withdrawing/sending money abroad
    public long numOrg = 300000; // 300k norwegian companies trading with foreign companies
    public long numForeign = 1000000; // 1 million foreign parties
//    public long numPersons = 2000; // 2 mill norwegians withdrawing/sending money abroad
//    public long numOrg = 3000; // 300k norwegian companies trading with foreign companies
//    public long numForeign = 1000; // 1 million foreign parties
    public int maxConnectionsPerEntity = 50; // Max different transaction partners per person
    public Instant minDate = Instant.parse("2005-01-01T00:00:00Z"); // global start date
    public Instant maxDate = Instant.parse("2017-06-01T00:00:00Z"); // global end date
  }
}
