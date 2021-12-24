package day2;

import java.io.*;
import java.util.List;

public class Main {

	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day2/input.txt"));
		List<String> lines = br.lines().toList();
		part1(lines);
		part2(lines);
	}

	public static void part1(List<String> lines) {
		int hpos = 0, depth = 0;
		for(String line : lines) {
			String[] split = line.split(" ");
			int num = Integer.parseInt(split[1]);
			switch(split[0]) {
				case "up" -> depth -= num;
				case "down" -> depth += num;
				case "forward" -> hpos += num;
			}
		}
		System.out.println(hpos * depth);
	}
	
	public static void part2(List<String> lines) {
		int hpos = 0, depth = 0, aim = 0;
		for(String line : lines) {
			String[] split = line.split(" ");
			int num = Integer.parseInt(split[1]);
			switch(split[0]) {
				case "down" -> aim += num;
				case "up" -> aim -= num;
				case "forward" -> {
					hpos += num;
					depth += aim * num;
				}
			}
		}
		System.out.println(hpos * depth);
	}
	
}
