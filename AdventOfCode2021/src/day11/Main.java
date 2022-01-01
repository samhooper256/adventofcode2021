package day11;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

	@FunctionalInterface
	interface GridAction {
		void apply(int row, int col);
	}
	
	static final int[][] DELTAS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
	
	static int[][] grid;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day11/input.txt"));
		grid = br.lines().map(line -> line.chars().map(c -> Character.digit(c, 10)).toArray()).toArray(int[][]::new);
		part1();
		part2();
	}
	
	static void part1() {
		int totalFlashes = 0;
		for(int i = 0; i < 100; i++)
			totalFlashes += doStepAndCountFlashes();
		System.out.println(totalFlashes);
	}
	
	/** Assumes the first step at which they all flash occurs after step 100. */
	static void part2() {
		int step = 1;
		while(doStepAndCountFlashes() != grid.length * grid[0].length)
			step++;
		System.out.println(step + 100);
	}
	
	static int doStepAndCountFlashes() {
		boolean[][] flashed = doStep();
		int flashCount = 0;
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[r].length; c++) {
				if(flashed[r][c]) {
					grid[r][c] = 0;
					flashCount++;
				}
			}
		}
		return flashCount;
	}
	
	/** Returns the number of flashes. */
	static boolean[][] doStep() {
		increaseAllBy(1);
		boolean[][] flashed = new boolean[grid.length][grid[0].length];
		for(int r = 0; r < grid.length; r++)
			for(int c = 0; c < grid[r].length; c++)
				if(grid[r][c] > 9)
					tryFlash(r, c, flashed);
		return flashed;
	}
	
	static void increaseAllBy(int n) {
		for(int r = 0; r < grid.length; r++)
			for(int c = 0; c < grid[r].length; c++)
				grid[r][c] += n;
	}
	
	static void tryFlash(int r, int c, boolean[][] flashed) {
		if(flashed[r][c])
			return;
		flashed[r][c] = true;
		forInBoundsNeighbors(r, c, (nr, nc) -> {
			grid[nr][nc]++;
			if(grid[nr][nc] > 9)
				tryFlash(nr, nc, flashed);
		});
	}
	
	static void forInBoundsNeighbors(int r, int c, GridAction action) {
		for(int[] delta : DELTAS) {
			int nr = r + delta[0], nc = c + delta[1];
			if(inBounds(nr, nc))
				action.apply(nr, nc);
		}
	}
	
	static boolean inBounds(int r, int c) {
		return r >= 0 && r < grid.length && c >= 0 && c < grid[r].length;
	}
	
	static void printGrid() {
		for(int[] row : grid)
			System.out.println(Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.joining()));
	}
}
