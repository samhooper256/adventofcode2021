package day10;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

public class Main {

	static final Map<String, Integer>
			CORRUPTED_SCORES = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137),
			INCOMPLETE_SCORES = Map.of(")", 1, "]", 2, "}", 3, ">", 4);
	
	static final Map<String, String> REVERSE_MAP = Map.of(")", "(", "]", "[", "}", "{", ">", "<",
			"(", ")", "[", "]", "{", "}", "<", ">");
	
	static List<String> lines;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day10/input.txt"));
		lines = br.lines().toList();
		part1();
		part2();
	}
	
	static void part1() {
		System.out.println(lines.stream().mapToLong(Main::getCorruptedScore).sum());
	}
	
	static long getScore(String line, ToLongFunction<Stack<String>> incompleteFunction) {
		Stack<String> stack = new Stack<>();
		for(String token : tokens(line)) {
			if(isOpening(token))
				stack.add(token);
			else if(stack.isEmpty() || !stack.peek().equals(REVERSE_MAP.get(token)))
				return CORRUPTED_SCORES.get(token);
			else
				stack.pop();
		}
		return incompleteFunction.applyAsLong(stack);
	}
	
	static void part2() {
		long[] scores = Main.lines.stream()
				.filter(line -> !isCorrupted(line))
				.mapToLong(Main::getIncompleteScore)
				.sorted().toArray();
		System.out.println(scores[scores.length / 2]);
	}
	
	static long getCorruptedScore(String line) {
		return getScore(line, stack -> 0);
	}
	
	static long getIncompleteScore(String line) {
		return getScore(line, stack -> {
			long score = 0;
			while(!stack.isEmpty())
				score = score * 5 + INCOMPLETE_SCORES.get(REVERSE_MAP.get(stack.pop()));
			return score;
		});
	}
	
	static List<String> tokens(String line) {
		return IntStream.range(0, line.length()).mapToObj(i -> line.substring(i, i + 1)).toList();
	}
	
	static boolean isCorrupted(String line) {
		return getCorruptedScore(line) > 0;
	}
	
	static boolean isOpening(String token) {
		return Set.of("(", "[", "{", "<").contains(token);
	}
	
}
