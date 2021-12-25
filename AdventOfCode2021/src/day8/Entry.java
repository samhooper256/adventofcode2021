package day8;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.Regex;

record Entry(List<String> segments, List<String> output) {

	private static final Pattern INITIAL_SPLIT_PATTERN = Pattern.compile(" | ", Pattern.LITERAL);
	
	static Entry fromLine(String line) {
		String[] split = INITIAL_SPLIT_PATTERN.split(line);
		List<String>
				segments = Regex.SPACE.splitAsStream(split[0]).collect(Collectors.toCollection(ArrayList::new)),
				output = Regex.SPACE.splitAsStream(split[1]).collect(Collectors.toCollection(ArrayList::new));
		return new Entry(segments, output);
	}
	
	static String sorted(String str) {
		char[] chars = str.toCharArray();
		Arrays.sort(chars);
		return String.valueOf(chars);
	}
	
	public Entry {
		segments.replaceAll(s -> sorted(s));
		output.replaceAll(s -> sorted(s));
	}
}
