package day6;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
	
	static final Pattern COMMA = Pattern.compile(",");
	static final int PART1_DAYS = 256, FISH_SPAWN_TIMER = 8;
	static final long D = PART1_DAYS;
	static long[][] fishGrid = new long[(int) (D + 1)][FISH_SPAWN_TIMER + 1];
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day6/input.txt"));
		List<Integer> fish = COMMA.splitAsStream(br.readLine()).map(Integer::valueOf).collect(Collectors.toList());
		for(int r = 0; r < fishGrid.length; r++)
			Arrays.fill(fishGrid[r], -1L);
	
//		System.out.println(part1(fish));
		System.out.println(part2(fish));
	}
	
	static int part1(List<Integer> fish) {
		fish = new ArrayList<>(fish);
		for(int i = 0; i < PART1_DAYS; i++)
			runDay(fish);
		return fish.size();
	}
	
	static long part2(List<Integer> fish) {
		fish = new ArrayList<>(fish);
		long total = 0L;
		for(int f : fish)
			total += fishSpawnedBy(f, 0);
		return fish.size() + total;
	}
	
	
	static long fishSpawnedBy(final int k, final int d) {
//		System.out.printf("[enter] fishSpawnedBy(k=%d, d=%d)%n", k, d);
		if(k > FISH_SPAWN_TIMER || d >= D)
			return 0L;
		if(fishGrid[d][k] != -1)
			return fishGrid[d][k];
		long total = Math.max(0, (long) Math.ceil((D - d - k) / 7.0));
//		System.out.printf("\tpre-total: %d%n", total);
		for(int d2 = d + k + 1; d2 <= D; d2 += 7) {
			total += fishSpawnedBy(8, d2);
		}
		fishGrid[d][k] = total;
		return total;
	}
	
	static void runDay(List<Integer> fish) {
		int size = fish.size();
		for(int i = 0; i < size; i++) {
			int f = fish.get(i);
			if(f == 0) {
				fish.set(i, 6);
				fish.add(FISH_SPAWN_TIMER);
			}
			else {
				fish.set(i, f - 1);
			}
		}
	}
	
	
}
