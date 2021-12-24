package day5;

import static java.lang.Integer.parseInt;

import java.io.*;
import java.util.*;

public class Main {

	static List<Line> lines;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day5/input.txt"));
		lines = br.lines().map(Main::parse).toList();
		part1();
	}
	
	private static void part1() {
		List<Line> hlines = lines.stream().filter(Line::isHorizontal).toList();
		int[][] map = new int[10][10];
		for(Line line : hlines) {
			System.out.printf("line=%s%n", line);
			for(int[] point : line) {
				System.out.printf("\tpoint=%s%n", Arrays.toString(point));
				map[point[0]][point[1]]++;
			}
		}
		int count = 0;
		for(int r = 0; r < map.length; r++)
			for(int c = 0; c < map[r].length; c++)
				if(map[r][c] >= 2)
					count++;
		System.out.println(count);
	}
	
	static Line parse(String line) {
		String[] split = line.replaceAll("\\D+", " ").split(" ");
		return new Line(parseInt(split[0]), parseInt(split[1]), parseInt(split[2]), parseInt(split[3]));
	}
	
}
