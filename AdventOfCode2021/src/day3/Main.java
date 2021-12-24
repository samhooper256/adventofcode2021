package day3;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {
	
	static int LENGTH;
	static int[] gammaBits, epsilonBits;
	static int[][] bits;
	static List<String> lines;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day3/input.txt"));
		lines = br.lines().toList();
		LENGTH = lines.get(0).length();
		bits = lines.stream().map(Main::arrayOfBits).toArray(int[][]::new);
		gammaBits = new int[LENGTH];
		epsilonBits = new int[LENGTH];
		part1();
		part2();
	}

	static int[] arrayOfBits(String line) {
		return line.chars().map(c -> Character.digit(c, 2)).toArray();
	}
	
	static int toInt(int[] bits) {
		return Integer.parseInt(toString(bits), 2);
	}

	public static String toString(int[] bits) {
		return Arrays.stream(bits).mapToObj(String::valueOf).collect(Collectors.joining());
	}
	
	public static void part1() {
		for(int col = 0; col < LENGTH; col++) {
			int sum = 0;
			for(int row = 0; row < bits.length; row++)
				sum += bits[row][col];
			gammaBits[col] = sum >= bits.length / 2 ? 1 : 0;
			epsilonBits[col] = 1 - gammaBits[col];
		}
		System.out.println(toInt(gammaBits) * toInt(epsilonBits));
	}
	
	static void part2() {
		System.out.println(getRating(Main::mostCommon) * getRating(Main::leastCommon));
	}
	
	static int getRating(BiFunction<List<int[]>, Integer, Integer> function) {
		List<int[]> list = new ArrayList<>(List.of(bits));
		for(int c = 0; c < LENGTH && list.size() > 1; c++) {
			final int index = c;
			int mostCommon = function.apply(list, c);
			list.removeIf(arr -> arr[index] != mostCommon);
		}
		return toInt(list.get(0));
	}
	
	static void printBits(int[][] bits) {
		printBits(List.of(bits));
	}
	
	static void printBits(List<int[]> list) {
		for(int[] bitSequence : list)
			System.out.println(toString(bitSequence));
	}
	
	static int mostCommon(List<int[]> list, int index) {
		int ones = 0;
		for(int r = 0; r < list.size(); r++)
			ones += list.get(r)[index];
		int zeros = list.size() - ones;
		return ones >= zeros ? 1 : 0;
	}
	
	static int leastCommon(List<int[]> list, int index) {
		return mostCommon(list, index) == 1 ? 0 : 1;
	}
	
}