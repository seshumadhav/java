package com.hackerrank.myprofile;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/flipping-bits
 *
 * @author smc
 */
public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		validate(T, 1, 100);

		long[] inputs = new long[T];
		for (int i = 0; i < T; i++) {
			inputs[i] = sc.nextLong();
			validate(inputs[i], 0, 4294967295L);
		}

		long[] outputs = new long[T];
		for (int i = 0; i < inputs.length; i++) {
			long number = inputs[i];
			long newNumber = toBinaryFlipAndBackToDecimal(number);
			// long newNumber = toBinaryFlipAndBackToDecimalV2(number);
			outputs[i] = newNumber;
		}

		for (long output : outputs) {
			System.out.println(output);
		}
	}

	/**
	 * 2^32 - 1 = 4294967295L
	 */
	private static long toBinaryFlipAndBackToDecimalV2(long number) {
		return 4294967295L - number;
	}

	private static long toBinaryFlipAndBackToDecimal(long number) {
		int[] toBin = toBinary(number);
		int[] flipBin = flip(toBin);
		long newNumber = toDecimal(flipBin);
		return newNumber;
	}

	private static int[] toBinary(long n) {
		int[] list = new int[32];
		for (int i = 0; i < 32; i++) {
			list[i] = 0;
		}

		int k = 0;
		while (n != 0) {
			list[k++] = (int) (n % 2);
			n = n / 2;
		}

		int[] binary = new int[32];
		for (int j = 0, i = 31; i >= 0; i--) {
			binary[j++] = list[i];
		}

		return binary;
	}

	private static int[] flip(int[] input) {
		int[] output = new int[32];

		for (int i = 0; i < 32; i++) {
			output[i] = input[i] == 0 ? 1 : 0;
		}

		return output;
	}

	private static long toDecimal(int[] bits) {
		int[] rev = new int[32];
		rev = reverse(bits);

		long multiple = 1;
		long d = 0;

		for (int i = 0; i < 32; i++) {
			d = rev[i] * multiple + d;
			multiple *= 2;
		}

		return d;
	}

	private static int[] reverse(int[] input) {
		int[] output = new int[32];
		for (int i = 0, j = 31; i < 32; i++) {
			output[j--] = input[i];
		}

		return output;
	}

	private static void validate(long input, long min, long max) {
		if (input < min || input > max) {
			System.out.println("Cannot proceed. Invalid input specified");
			System.exit(1);
		}
	}
}
