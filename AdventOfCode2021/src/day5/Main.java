package day5;

import static java.lang.Integer.parseInt;

import java.io.*;
import java.util.*;

public class Main {

	static List<Line> lines;
	static int[][] map;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day5/input.txt"));
		lines = br.lines().map(Main::parse).toList();
		int size = findMapSize();
		map = new int[size][size];
		part1();
		part2();
	}
	
	static Line parse(String line) {
		String[] split = line.replaceAll("\\D+", " ").split(" ");
		return new Line(parseInt(split[1]), parseInt(split[0]), parseInt(split[3]), parseInt(split[2]));
	}
	
	static int findMapSize() {
		return lines.stream()
				.mapToInt(line -> Math.max(Math.max(line.col1(), line.row1()), Math.max(line.col2(), line.row2())))
				.max().getAsInt() + 1;
	}
	
	static void part1() {
		incorporateLines(lines.stream().filter(Line::isHorizontalOrVertical).toList());
		System.out.println(intersectionCount());
	}
	
	static void part2() {
		incorporateLines(lines.stream().filter(Line::isDiagonal).toList());
		System.out.println(intersectionCount());
	}
	
	public static void incorporateLines(List<Line> hvlines) {
		for(Line line : hvlines)
			for(int[] point : line)
				map[point[0]][point[1]]++;
	}
	
	static int intersectionCount() {
		int count = 0;
		for(int r = 0; r < map.length; r++)
			for(int c = 0; c < map[r].length; c++)
				if(map[r][c] >= 2)
					count++;
		return count;
	}
	
}
