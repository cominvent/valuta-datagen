package com.ciber.skatt;

import java.util.Random;

/**
 * Created by janhoy on 15.11.2016.
 */
public abstract class AbstractGenerator implements Generator {
  public static Random random = new Random();
  protected long seed;
  
  public Generator setSeed(long seed) {
    this.seed = seed;
    random.setSeed(seed);
    return this;
  }
  
}
