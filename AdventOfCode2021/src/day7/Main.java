package day7;

import java.io.*;
import java.util.Arrays;

import utils.*;

public class Main {

	static int[] nums;
	static int max;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day7/input.txt"));
		nums = Regex.COMMA.splitAsStream(br.readLine()).mapToInt(Integer::parseInt).toArray();
		max = Maths.max(nums);
		System.out.printf("length: %d%n", nums.length);
		part1();
		part2();
	}
	
	static void part1() {
		int median = roundedMedian(nums);
		int total = 0;
		for(int n : nums)
			total += Math.abs(n - median);
		System.out.println(total);
	}
	
	static void part2() {
		int min = Integer.MAX_VALUE;
		for(int dest = 0; dest <= max; dest++) {
			int sum = 0;
			for(int i = 0; i < nums.length; i++)
				sum += fuelCost(dest, nums[i]);
			min = Math.min(sum, min);
		}
		System.out.println(min);
	}
	
	static int fuelCost(int a, int b) {
		int n = Math.abs(a - b);
		return n * (n + 1) / 2;
	}
	
	static int roundedMedian(int... nums) {
		return Math.toIntExact(Math.round(median(nums)));
	}
	
	static double median(int... nums) {
		int[] copy = Arrays.copyOf(nums, nums.length);
		Arrays.sort(nums);
		int n = copy.length;
		if(n % 2 == 0)
			return (nums[n / 2 - 1] + nums[n / 2]) / 2.0;
		else
			return nums[n / 2];
	}
	
}
