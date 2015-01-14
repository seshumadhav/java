package com.smc.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BloomLogger {

  private static final Logger BLOOM_LOGGER = Logger.getLogger("Bloom");

  public static void log(String msg) {
    BLOOM_LOGGER.log(Level.INFO, msg);
  }

  public static void log(Level level, String msg) {
    BLOOM_LOGGER.log(level, msg);
  }

  public static void log(String msg, Throwable t) {
    BLOOM_LOGGER.log(Level.SEVERE, msg, t);
  }

}
