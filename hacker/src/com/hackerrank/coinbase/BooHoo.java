package com.hackerrank.coinbase;

import java.util.Scanner;

public class BooHoo {
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();

    if (!isValid(n)) {
      System.exit(1);
    }

    for (int i = 1; i <= n; i++) {
      if (isMutlipleOf3(i) && isMultipleOf5(i)) {
        System.out.println("BooHoo");
      } else if (isMutlipleOf3(i)) {
        System.out.println("Hoo");
      } else if (isMultipleOf5(i)) {
        System.out.println("Boo");
      } else {
        System.out.println(i);
      }
    }

  }

  private static boolean isMultipleOf5(int i) {
    return i % 5 == 0;
  }

  private static boolean isMutlipleOf3(int i) {
    return i % 3 == 0;
  }

  private static boolean isValid(int n) {
    if (n > 10000000) {
      System.out.println("Invalid input");
      return false;
    }

    return true;
  }
}