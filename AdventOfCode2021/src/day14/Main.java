package day14;

import java.io.*;
import java.util.*;

public class Main {
	
	record Insertion(int index, String str) implements Comparable<Insertion> {

		@Override
		public int compareTo(Insertion o) {
			return Integer.compare(index, o.index);
		}
		
	}
	
	static final class AlphaCounter {
		long[] counts; 
		
		public AlphaCounter() {
			counts = new long['Z' - 'A' + 1];
		}
		
		public void accept(char c) {
			counts[c - 'A']++;
		}
		
		public void accept(String str) {
			if(str.length() != 1)
				throw new IllegalArgumentException("str.length() != 1");
			accept(str.charAt(0));
		}
		
		public void acceptAll(String str) {
			for(char c : str.toCharArray())
				accept(c);
		}
		
		public void acceptAll(AlphaCounter o) {
			for(int i = 0; i < 26; i++)
				counts[i] += o.counts[i];
		}
		
		public long maxCount() {
			return Arrays.stream(counts).max().getAsLong();
		}
		
		public long minCount() {
			return Arrays.stream(counts).filter(i -> i > 0L).min().getAsLong();
		}
		
		@Override
		public String toString() {
			StringJoiner j = new StringJoiner(", ", "{", "}");
			for(int i = 0; i < counts.length; i++)
				if(counts[i] > 0)
					j.add(String.format("%c=%d", i + 'A', counts[i]));
			return j.toString();
		}
		
	}
	
	static final int PART1_STEPS = 10, PART2_STEPS = 40;
	static Map<String, String> insertionRules;
	static String template, polymer;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day14/input.txt"));
		List<String> lines = br.lines().toList();
		insertionRules = new HashMap<>();
		template = polymer = lines.get(0);
		for(String line : lines.subList(2, lines.size()))
			addInsertionRule(line);
		part1();
		part2();
	}
	
	static void part2() {
		Map<String, AlphaCounter[]> map = new HashMap<>();
		for(String s : insertionRules.keySet()) {
			AlphaCounter[] arr = new AlphaCounter[PART2_STEPS + 1];
			for(int i = 0; i < arr.length; i++)
				arr[i] = new AlphaCounter();
			map.put(s, arr);
		}
		for(String key : map.keySet()) {
			AlphaCounter counter = map.get(key)[1];
			String middle = insertionRules.get(key);
			counter.accept(middle);
		}
		for(int step = 2; step <= PART2_STEPS; step++) {
			for(String key : map.keySet()) {
				AlphaCounter stepCounter = map.get(key)[step];
				String middle = insertionRules.get(key);
				String depth1 = key.charAt(0) + middle + key.charAt(1);
				String first = depth1.substring(0, 2), second = depth1.substring(1);
				if(insertionRules.containsKey(first)) {
					stepCounter.acceptAll(map.get(first)[step - 1]);
				}
				if(insertionRules.containsKey(second)) {
					stepCounter.acceptAll(map.get(second)[step - 1]);
				}
				stepCounter.accept(middle);
			}
		}
		
		AlphaCounter finalCount = new AlphaCounter();
		finalCount.acceptAll(template);
		for(int i = 0; i < template.length() - 1; i++) {
			String sub = template.substring(i, i + 2);
			finalCount.acceptAll(map.get(sub)[PART2_STEPS]);
		}
		
//		System.out.println(finalCount);
		System.out.println(finalCount.maxCount() - finalCount.minCount());
	}
	
	static void part1() {
		polymer = expand(polymer, PART1_STEPS);
		Map<String, Integer> counts = getCounts(polymer);
//		System.out.println(counts);
		System.out.println(Collections.max(counts.values()) - Collections.min(counts.values()));
//		System.out.println();
	}

	public static String expand(String input, int steps) {
		for(int step = 1; step <= steps; step++)
			input = doStep(input);
		return input;
	}
	
	static Map<String, Integer> getCounts(String str) {
		Map<String, Integer> map = new HashMap<>();
		for(int i = 0; i < str.length(); i++)
			map.merge(str.substring(i, i + 1), 1, Integer::sum);
		return map;
	}
	
	static void addInsertionRule(String line) {
		insertionRules.put(line.substring(0, 2), line.substring(line.length() - 1));
	}
	
	static String doStep(String input) {
		List<Insertion> ins = new ArrayList<>();
		for(Map.Entry<String, String> e : insertionRules.entrySet())
			ins.addAll(getInsertions(input, e.getKey(), e.getValue()));
		Collections.sort(ins, Comparator.reverseOrder());
		String result = input;
		for(Insertion i : ins) {
			int index = i.index();
			String str = i.str();
			result = result.substring(0, index) + str + result.substring(index);
		}
		return result;
	}
	
	static List<Insertion> getInsertions(String str, String left, String right) {
		List<Insertion> ins = new ArrayList<>();
		for(int i = 0; i < str.length() - 1; i++)
			if(str.substring(i, i + 2).equals(left))
				ins.add(new Insertion(i + 1, right));
		return ins;
	}
	
}
