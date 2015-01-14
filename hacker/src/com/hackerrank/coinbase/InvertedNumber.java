package com.hackerrank.coinbase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvertedNumber {
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();

    List<Integer> powers = get2Powers(200000);
    for (Integer power : powers) {
      // System.out.println(power);
    }

    if (!isValid(n)) {
      System.exit(1);
    }

    int in = getInvertedNumber(n);
    System.out.println(in);
  }

  private static int getInvertedNumber(int n) {
    List<Integer> powers = get2Powers(200000);
    int index = 0;
    int next = powers.get(index);
    while (n > next) {
      index = index + 1;
      next = powers.get(index);
    }

    return next - 1 - n;
  }

  private static boolean isValid(int n) {
    if (n > 100000) {
      System.out.println("Invalid input");
      return false;
    }

    return true;
  }

  private static List<Integer> get2Powers(int n) {
    List<Integer> list = new ArrayList<Integer>();

    Integer start = 1;
    do {
      list.add(start);
      start = start * 2;
    } while (start < n);

    return list;
  }
}