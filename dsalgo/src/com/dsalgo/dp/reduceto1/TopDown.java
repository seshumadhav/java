package com.dsalgo.dp.reduceto1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * On a positive integer, you can perform any one of the following 3 steps.
 * 
 * 1.) Subtract 1 from it. ( n = n - 1 ) , 
 * 2.) If its divisible by 2, divide by 2. ( if n % 2 == 0 , then n = n / 2 ) , 
 * 3.) If its divisible by 3, divide by 3. ( if n % 3 == 0 , then n = n / 3 ). 
 * 
 * Now the question is, given a positive integer n, 
 * find the minimum number of steps that takes n to 1 eg: 
 * 
 * 1.) For n = 1 , output: 0 
 * 2.) For n = 4, output: 2 ( 4 /2 = 2 /2 = 1 ) 
 * 3.) For n = 7, output: 3 ( 7 -1 = 6 /3 = 2 /2 = 1 )
 */
public class TopDown {
	
	public static final int MAX = 65535;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		System.out.println(n);
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		System.out.println("Min steps: " + f(n, map));
	}
	
	private static int f(int i, Map<Integer, Integer> map) {
		if (i == 1) {
			return 0;
		}

		if (map.containsKey(i)) {
			return map.get(i);
		}
		
		System.out.println("f("+i+")");
		int x = f(i-1, map);
		int y = i%2 == 0 ? f(i/2, map) : MAX;
		int z = i%3 == 0 ? f(i/3, map) : MAX;
		
		int min = x;
		if (x <= y && x <= z) {
			min = x;
		} else if (y <= x && y <= z) {
			min = y;
		} else if (z <= x && z <= y) {
			min = z;
		}
		
		int steps = 1 +min;
		map.put(i, steps);
		return steps;
	}

}
