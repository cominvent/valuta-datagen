package com.ciber.skatt;

/**
 * Created by janhoy on 15.11.2016.
 */
public interface Generator {
  public String generate();
  public void init(Object config);
  public Generator setSeed(long seed);
}
