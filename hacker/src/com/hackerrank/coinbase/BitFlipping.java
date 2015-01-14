package com.hackerrank.coinbase;

import java.util.Scanner;

public class BitFlipping {
	public static void main(String[] args) {

		// get n
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		// get input array
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = sc.nextInt();
		}

		// print input array
		for (int i = 0; i < n; i++) {
			System.out.print(array[i] + " ");
		}

		int max = 0;
		for (int i = 0; i < n - 1; i++) {
			// get bits between i, j
			for (int j = i + 1; j < n; j++) {
				int pre = getBitsSet(0, i - 1, array, false);
				int band = getBitsSet(i, j, array, true);
				int post = getBitsSet(j + 1, n - 1, array, false);
				if (max < pre + band + post) {
					max = pre + band + post;
				}
			}
		}

		System.out.println("\nMax: " + max);
	}

	private static int getBitsSet(int begin, int end, int[] array, boolean invert) {
		int count = 0;
		for (int i = begin; i <= end; i++) {
			int aim = invert ? 0 : 1;
			if (array[i] == aim) {
				count++;
			}
		}

		return count;
	}

}