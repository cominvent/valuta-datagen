package com.ciber.skatt;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by janhoy on 16.11.2016.
 */
public class Organization extends Person {
  private String orgno;
  
  public static Organization getRandom(Random random) {
    Organization o = new Organization();
    o.orgno = RandomUtil.randomOrgNo(random);
    o.name = RandomUtil.randomCompanyName(random);
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
