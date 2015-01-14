package com.hackerrank.coinbase;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Pairs {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    if (!isValid(n, 0, 100000)) {
      System.exit(1);
    }

    int d = sc.nextInt();
    if (!isValid(n, 1, 999999999)) {
      System.exit(1);
    }

    List<Integer> input = new ArrayList<Integer>();
    for (int i = 0; i < n; i++) {
      input.add(sc.nextInt());
    }

    int numMatchedPairs = 0;
    Collections.sort(input);
    for (int i = 0; i < input.size() - 1; i++) {
      int j = i + 1;
      while (diff(input, i, j) <= d) {
        if (diff(input, i, j) == d) {
          numMatchedPairs++;
          // System.out.println("(" + input.get(i) + ", " + input.get(j) + ")");
        }

        j++;
      }
    }
    System.out.println(numMatchedPairs);

  }

  private static int diff(List<Integer> input, int i, int j) {
    return abs(input.get(j) - input.get(i));
  }

  private static boolean isValid(int n, int min, int max) {
    if (n < min || n > max) {
      System.out.println("Invalid input");
      return false;
    }

    return true;
  }

}
