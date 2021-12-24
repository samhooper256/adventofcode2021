package day4;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
	
	static int[] getRow(String line) {
		return Arrays.stream(line.strip().split(" +")).mapToInt(Integer::parseInt).toArray();
	}
	
	private final int[][] nums;
	private final boolean[][] marked = new boolean[5][5];
	
	Board(List<String> lines) {
		nums = lines.stream().map(Board::getRow).toArray(int[][]::new);
	}
	
	void called(int num) {
		int[] spot = indexOf(num);
		if(spot != null)
			marked[spot[0]][spot[1]] = true;
	}
	
	int[] indexOf(int num) {
		for(int r = 0; r < 5; r++)
			for(int c = 0; c < 5; c++)
				if(nums[r][c] == num)
					return new int[] {r, c};
		return null;
	}
	
	boolean won() {
		outer1:
		for(int r = 0; r < 5; r++) {
			for(int c = 0; c < 5; c++)
				if(!marked[r][c])
					continue outer1;
			return true;
		}
		outer2:
		for(int c = 0; c < 5; c++) {
			for(int r = 0; r < 5; r++)
				if(!marked[r][c])
					continue outer2;
			return true;
		}
		return false;
	}
	
	void print() {
		for(int[] row : nums)
			System.out.println(Arrays.stream(row).mapToObj(i -> String.format("%2d", i))
					.collect(Collectors.joining(" ")));
	}
	
	int score(int justCalled) {
		int sum = 0;
		for(int r = 0; r < 5; r++)
			for(int c = 0; c < 5; c++)
				if(!marked[r][c])
					sum += nums[r][c];
		return sum * justCalled;
	}
	
}
