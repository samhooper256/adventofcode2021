package day9;

import java.io.*;
import java.util.*;

public class Main {

	static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static int[][] nums;
	static boolean[][] used;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day9/input.txt"));
		nums = br.lines().map(line -> line.chars().map(i -> i - '0').toArray()).toArray(int[][]::new);
		part1();
		part2();
	}
	
	static void part1() {
		int total = 0;
		for(int r = 0; r < nums.length; r++)
			for(int c = 0; c < nums[r].length; c++)
				if(isLowPoint(r, c))
					total += 1 + nums[r][c];
		System.out.println(total);
	}
	
	static boolean isLowPoint(int r, int c) {
		for(int[] delta : DELTAS) {
			int nr = r + delta[0], nc = c + delta[1];
			if(inBounds(nr, nc) && nums[nr][nc] <= nums[r][c])
				return false;
		}
		return true;
	}
	
	static boolean inBounds(int r, int c) {
		return r >= 0 && r < nums.length && c >= 0 && c < nums[r].length;
	}
	
	static void part2() {
		used = new boolean[nums.length][nums[0].length];
		for (int r = 0; r < nums.length; r++)
			for (int c = 0; c < nums[r].length; c++)
				if(nums[r][c] == 9)
					used[r][c] = true;
		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
		for (int r = 0; r < nums.length; r++)
			for (int c = 0; c < nums[r].length; c++)
				if(!used[r][c])
					pq.add(groupSize(r, c));
		System.out.println(pq.remove() * pq.remove() * pq.remove());
	}
	
	static int groupSize(int r, int c) {
		if(!inBounds(r, c) || used[r][c])
			return 0;
		used[r][c] = true;
		int size = 1;
		for(int[] delta : DELTAS)
			size += groupSize(r + delta[0], c + delta[1]);
		return size;
	}
	
}
