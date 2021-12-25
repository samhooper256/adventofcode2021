package day8;

import java.io.*;
import java.util.*;

import utils.Arrs;

public class Main {

	static final Set<Integer> ALL_SEGSPOTS = Set.of(0, 1, 2, 3, 4, 5, 6);
	static final int[] UNIQUE_COUNT_DISPLAYS = {1, 4, 7, 8};
	static final int[] SEGMENT_COUNTS = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};
	static final int[] COUNTS_OF_UNIQUE =
		{SEGMENT_COUNTS[1], SEGMENT_COUNTS[4], SEGMENT_COUNTS[7], SEGMENT_COUNTS[8]};
	static final Set<String> ALL_SEGMENTS = Set.of("a", "b", "c", "d", "e", "f", "g");
	/** Seg spots:
	 * --0--
	 * 1   2
	 * --3--
	 * 4   5
	 * --6--
	 */
	static final Map<Integer, Set<Integer>> DISPLAY_TO_SEGSPOTS = Map.of(
			0, Set.of(0, 1, 2, 4, 5, 6),
			1, Set.of(2, 5),
			2, Set.of(0, 2, 3, 4, 6),
			3, Set.of(0, 2, 3, 5, 6),
			4, Set.of(1, 2, 3, 5),
			5, Set.of(0, 1, 3, 5, 6),
			6, Set.of(0, 1, 3, 4, 5, 6),
			7, Set.of(0, 2, 5),
			8, ALL_SEGSPOTS,
			9, Set.of(0, 1, 2, 3, 5, 6)
	);
	/** Maps a given number of segments to a set of all possible numbers that require that many segments to display.*/
	static final Map<Integer, Set<Integer>> COUNT_TO_NUMBERS = new HashMap<>();
	
	static {
		for(int i = 0; i < SEGMENT_COUNTS.length; i++) {
			int segmentCount = SEGMENT_COUNTS[i];
			if(!COUNT_TO_NUMBERS.containsKey(segmentCount))
				COUNT_TO_NUMBERS.put(segmentCount, new HashSet<>());
			COUNT_TO_NUMBERS.get(segmentCount).add(i);
		}
	}
	
	static List<String> lines;
	static List<Entry> entries;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day8/input.txt"));
		lines = br.lines().toList();
		entries = lines.stream().map(Entry::fromLine).toList();
		part1();
		part2();
	}
	
	static void part1() {
		System.out.println(entries.stream().map(Entry::output).mapToInt(Main::count1478).sum());
	}
	
	static int count1478(List<String> output) {
		return (int) output.stream().mapToInt(String::length).filter(i -> Arrs.contains(COUNTS_OF_UNIQUE, i)).count();
	}
	
	static int segmentCountToDisplay(int n) {
		return SEGMENT_COUNTS[n];
	}
	
	static void part2() {
		int total = 0;
		for(Entry e : entries)
			total += deduce(e);
		System.out.println(total);
	}
	
	static int deduce(Entry entry) {
		List<String> segments = entry.segments();
		Map<String, Set<Integer>> strToSegSpots = new HashMap<>();
		for(String s : ALL_SEGMENTS) {
			strToSegSpots.put(s, new HashSet<>());
			strToSegSpots.get(s).addAll(ALL_SEGSPOTS);
		}
		
		Map<String, Integer> strToOccurences = new HashMap<>();
		for(String s : ALL_SEGMENTS)
			strToOccurences.put(s, countOccurencesAsSubustring(segments, s));
		
		//whichever one occurs 6 times must have segspot 1.
		Set<Integer> set6 = strToSegSpots.get(getKeyWithValue(strToOccurences, 6));
		set6.clear();
		set6.add(1);
		
		//whichever one occurs 4 times must have segspot 4.
		Set<Integer> set4 = strToSegSpots.get(getKeyWithValue(strToOccurences, 4));
		set4.clear();
		set4.add(4);
		
		//whichever one occurs 9 times must have segspot 5.
		Set<Integer> set9 = strToSegSpots.get(getKeyWithValue(strToOccurences, 9));
		set9.clear();
		set9.add(5);
				
		String[] displayToString = new String[10];
		for(int i : UNIQUE_COUNT_DISPLAYS)
			displayToString[i] = getStringWithLength(segments, segmentCountToDisplay(i));
		for(int i : UNIQUE_COUNT_DISPLAYS) {
			String str = displayToString[i];
			Set<Integer> segSpots = DISPLAY_TO_SEGSPOTS.get(i);
			for(int j = 0; j < str.length(); j++) {
				String sub = str.substring(j, j + 1);
				strToSegSpots.get(sub).retainAll(segSpots);
			}
		}
		
		String oneStr = displayToString[1];
		String one1 = oneStr.substring(0, 1), one2 = oneStr.substring(1, 2);
		if(strToSegSpots.get(one1).size() == 2)
			strToSegSpots.get(one1).removeAll(strToSegSpots.get(one2));
		else
			strToSegSpots.get(one2).removeAll(strToSegSpots.get(one1));
		
		String sevenStr = displayToString[7];
		for(int i = 0; i < sevenStr.length(); i++) {
			String sub = sevenStr.substring(i, i + 1);
			if(!oneStr.contains(sub)) {
				Set<Integer> subSpots = strToSegSpots.get(sub);
				subSpots.removeAll(strToSegSpots.get(one1));
				subSpots.removeAll(strToSegSpots.get(one2));
				break;
			}
		}
		
		Set<Integer> bottomStrSet = strToSegSpots.get(getKeyWithValue(strToSegSpots, ALL_SEGSPOTS));
		bottomStrSet.clear();
		bottomStrSet.add(6);
		
		for(Set<Integer> set : strToSegSpots.values()) {
			if(set.size() > 1) {
				set.clear();
				set.add(3);
				break;
			}
		}
		
		Map<Integer, String> segSpotToSegment = new HashMap<>();
		for(Map.Entry<String, Set<Integer>> e : strToSegSpots.entrySet())
			segSpotToSegment.put(e.getValue().iterator().next(), e.getKey());
			
		Map<String, Integer> strToDisplay = new HashMap<>();
		for(int i = 0; i < 10; i++) {
			String str = "";
			for(int segSpot : DISPLAY_TO_SEGSPOTS.get(i)) {
				str += segSpotToSegment.get(segSpot);
			}
			strToDisplay.put(Entry.sorted(str), i);
		}
//		System.out.printf("strToSegSpots: %n");
//		for(var e : strToSegSpots.entrySet())
//			System.out.printf("%s : %s%n", e.getKey(), e.getValue());
//		
//		System.out.printf("%nstrToDisplay: %n");
//		for(var e : strToDisplay.entrySet())
//			System.out.printf("%s : %s%n", e.getKey(), e.getValue());
		
		String out = "";
		for(String s : entry.output())
			out += strToDisplay.get(s);
		return Integer.parseInt(out);
	}
	
	static String getStringWithLength(Collection<String> strings, int length) {
		for(String s : strings)
			if(s.length() == length)
				return s;
		throw new IllegalStateException();
	}
	
	static int countOccurencesAsSubustring(Collection<String> strings, String str) {
		int count = 0;
		for(String s : strings)
			if(s.contains(str))
				count++;
		return count;
	}
	
	static <K, V> K getKeyWithValue(Map<K, V> map, V value) {
		boolean keyFound = false;
		K key = null;
		for(Map.Entry<K, V> e : map.entrySet()) {
			if(Objects.equals(value, e.getValue())) {
				if(keyFound)
					throw new IllegalArgumentException("Multiple keys with given value");
				key = e.getKey();
				keyFound = true;
			}
		}
		if(!keyFound)
			throw new IllegalArgumentException("No key with given value");
		return key;
	}
	
}
