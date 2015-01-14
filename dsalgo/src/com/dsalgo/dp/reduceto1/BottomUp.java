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
public class BottomUp {
	
	public static final int MAX = 10000;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		System.out.println(n);
		
		int[] values = new int[n+1];
		values[0] = MAX;
		values[1] = 0;
		
		for (int i = 2; i <= n; i++) {
			int x = values[i-1];
			int y = i%2 == 0 ? values[i/2] : MAX;
			int z = i%3 == 0 ? values[i/3] : MAX;
			int min = min(x, y, z) + 1;
			values[i] = min;
		}
		
		System.out.println(values[n]);
	}
	
	private static int min(int x, int y, int z) {
		int min = x;		
		if (x <= y && x <= z) {
			min = x;
		} else if (y <= x && y <= z) {
			min = y;
		} else if (z <= x && z <= y) {
			min = z;
		}
		
		return min;
	}
}
