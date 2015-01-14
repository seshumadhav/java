package com.smc.winetasting;

import com.smc.utils.BloomLogger;

public class DistributeWinesDriver {

  public static void main(String[] args) {
    WineDistributor matcher = new WineDistributor("person_wine_3.txt");

    try {
      matcher.distribute();
    } catch (Exception e) {
      BloomLogger.log("Unexpected exception occured. Pls check the logs.", e);
    }
  }

}
