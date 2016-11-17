package com.ciber.skatt;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by janhoy on 16.11.2016.
 */
public class Organization extends Person {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private String orgno;
  
  public static Organization getRandom(Random random) {
    Organization o = new Organization();
    o.orgno = RandomUtil.randomOrgNo(random);
    o.name = RandomUtil.randomCompanyName(random);
    o.fromDate = Instant.ofEpochSecond(random.nextInt(1479339500));
    o.toDate = Instant.ofEpochSecond(random.nextInt(1479339500));
    if (o.fromDate.isAfter(o.toDate)) {
      Instant from = o.fromDate;
      o.fromDate = o.toDate;
      o.toDate = from;
    }
    return o;
  }

  public String getOrgno() {
    return orgno;
  }

  public void setOrgno(String orgno) {
    this.orgno = orgno;
  }

  public String toString() {
    Map<String,Object> map = new HashMap<>();
    map.put("name", name);
    map.put("orgno", orgno);
    map.put("fromDate", fromDate);
    map.put("toDate", toDate);
    map.put("links", links);
    return new Gson().toJson(map);
  }
}
