package day1;

import java.io.*;
import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day1/input.txt"));
		int[] nums = br.lines().mapToInt(Integer::parseInt).toArray();
		int[] windows = IntStream.range(0, nums.length - 2).map(i -> nums[i] + nums[i + 1] + nums[i + 2]).toArray();
		System.out.println(increaseCount(nums));
		System.out.println(increaseCount(windows));
	}
	
	private static int increaseCount(int[] nums) {
		return (int) IntStream.range(1, nums.length).filter(i -> nums[i] > nums[i - 1]).count();
	}
	
}
