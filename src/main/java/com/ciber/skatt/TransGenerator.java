package com.ciber.skatt;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.Instant;
import java.util.*;

/**
 * Created by janhoy on 15.11.2016.
 */
public class TransGenerator extends AbstractGenerator {
  Log log = LogFactory.getLog(TransGenerator.class);
  
  private static final Gson gson = new Gson();
  private Config conf;
  private List<Person> persons;
  private List<Organization> orgs;
  private List<Foreign> foreigns;

  public static TransGenerator getInstance() {
    return new TransGenerator();
  }
  
  @Override
  public String generate() {
    boolean person = random.nextBoolean();
    if (person)
      return generatePersonTrans();
    else
      return generateOrgTrans();
  }

  private String generateOrgTrans() {
    Organization o = orgs.get(random.nextInt(orgs.size()));
    Foreign f = foreigns.get(o.getLinks().get(random.nextInt(o.getLinks().size())));
    float amountNo = random.nextFloat() * 10000000 + 100;
    long span = o.getToDate().toEpochMilli() - o.getFromDate().toEpochMilli();
    Instant transTime = o.getFromDate().plusMillis(Math.round(random.nextFloat() * span)); 
    String innut = random.nextBoolean() ? "I" : "U";
    
    Transaction t = new Transaction(o, f, amountNo, transTime, innut);
    return t.toString();
  }

  private String generatePersonTrans() {
    Person p = persons.get(random.nextInt(persons.size()));
    Foreign f = foreigns.get(p.getLinks().get(random.nextInt(p.getLinks().size())));
    float amountNo = random.nextFloat() * 10000000 + 100;
    long span = p.getToDate().toEpochMilli() - p.getFromDate().toEpochMilli();
    Instant transTime = p.getFromDate().plusMillis(Math.round(random.nextFloat() * span)); 
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
    public long numPersons = 10000;
    public long numOrg = 10000;
    public long numForeign = 5000;
    public int maxConnectionsPerEntity = 10;
    public int maxTransactionsPerEntity = 100;
    public Instant minDate = Instant.parse("2015-01-01T00:00:00Z");  
    public Instant maxDate = Instant.parse("2016-11-01T00:00:00Z");  
  }


  private class Transaction {
    private Person p = null;
    private Organization o = null;
    private Foreign f;
    private float amountNo;
    private Instant transTime;
    private String innut;

    public Transaction(Person p, Foreign f, float amountNo, Instant transTime, String innut) {
      this.p = p;
      this.f = f;
      this.amountNo = amountNo;
      this.transTime = transTime;
      this.innut = innut;
    }

    public Transaction(Organization o, Foreign f, float amountNo, Instant transTime, String innut) {
      this.p = o;
      this.o = o;
      this.f = f;
      this.amountNo = amountNo;
      this.transTime = transTime;
      this.innut = innut;
    }
    
    public String toString() {
      Map<String,Object> map = new HashMap<>();
      map.put("transdato", new Date(transTime.toEpochMilli()));
      map.put("nokbelop", amountNo);
      map.put("valutabelop", ExchangeRates.nokTo(amountNo, f.getCountry().getCurrencyCode()));
      map.put("valutakode", f.getCountry().getCurrencyCode());
      map.put("innut", innut);
      map.put("behandlingskode", "");
      map.put("norstatsborgerland", "Norge");
      map.put("norkontonr", p.getKontoNr());
      map.put("norskpartstype", o==null?"person":"virksomhet");
      if (o!=null) {
        map.put("nororgnr", o.getOrgno());
        map.put("noretternavn", p.getName());
      } else {
        map.put("noretternavn", p.getLastName());
        map.put("norfornavn", p.getFirstName());
        map.put("norfodselsnr", p.getSsn());
        map.put("norfodselsdato", p.getSsn().substring(0, 6));
      }
      map.put("utlfodselsnr", f.getBirthDate());
      map.put("utlkontonr", f.getKontoNr());
      map.put("utletternavn", f.getLastName());
      map.put("utlfornavn", f.getFirstName());
      map.put("utlland", f.getCountry().getName());
      map.put("nokbelopstr", String.format("%.2f", amountNo));
      map.put("valutabelopstr", String.format("%.2f", map.get("nokbelop")));
      return gson.toJson(map);
    } 
    
  }
}
