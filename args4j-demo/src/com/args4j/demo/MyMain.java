package com.args4j.demo;

public class MyMain {

  public static void main(String... args) {
    Features features = new Features(args);
    System.out.println("Authentication disabled: " + features.isDisableAuth());
  }
}
