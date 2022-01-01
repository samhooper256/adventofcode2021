package day13;

import java.io.*;
import java.util.*;

import utils.Point;

public class Main {

	static char[][] paper;
	static List<Point> points;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day13/input.txt"));
		List<String> lines = br.lines().toList();
		int breakIndex = lines.indexOf("");
		List<String> foldLines = lines.subList(breakIndex + 1, lines.size());
		points = lines.subList(0, breakIndex).stream().map(Main::parsePoint).toList();
		paper = new char[maxRow() + 1][maxCol() + 1];
		for(char[] row : paper)
			Arrays.fill(row, '.');
		for(Point p : points)
			paper[p.row()][p.col()] = '#';
		doFold(foldLines.get(0));
		System.out.println(countPoints());
		for(int i = 1; i < foldLines.size(); i++)
			doFold(foldLines.get(i));
		printPaper();
	}
	
	static void doFold(String line) {
		String[] split = line.substring(line.lastIndexOf(' ') + 1).split("=");
		int num = Integer.parseInt(split[1]);
		if(split[0].equals("x"))
			doXFold(num);
		else
			doYFold(num);
	}
	
	static void doXFold(int x) {
		char[][] grid = new char[paper.length][paper[0].length / 2];
		copyRelevant(grid);
		for(int r = 0; r < paper.length; r++)
			for(int c = x + 1; c < paper[r].length; c++)
				if(paper[r][c] == '#')
					grid[r][c - 2 * (c - x)] = '#';
		paper = grid;
	}
	
	static void doYFold(int y) {
		char[][] grid = new char[paper.length / 2][paper[0].length];
		copyRelevant(grid);
		for(int r = y + 1; r < paper.length; r++)
			for(int c = 0; c < paper[r].length; c++)
				if(paper[r][c] == '#')
					grid[r - 2 * (r - y)][c] = '#';
		paper = grid;
	}
	
	static void copyRelevant(char[][] grid) {
		for(int r = 0; r < grid.length; r++)
			for(int c = 0; c < grid[r].length; c++)
				grid[r][c] = paper[r][c];
	}
	
	static int countPoints() {
		int count = 0;
		for(int r = 0; r < paper.length; r++)
			for(int c = 0; c < paper[r].length; c++)
				if(paper[r][c] == '#')
					count++;
		return count;
	}
	
	static Point parsePoint(String line) {
		String[] split = line.split(",");
		return Point.xy(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
	}
	
	static int maxRow() {
		return points.stream().mapToInt(Point::row).max().getAsInt();
	}
	
	static int maxCol() {
		return points.stream().mapToInt(Point::col).max().getAsInt();
	}
	
	static void printPaper() {
		for(char[] row : paper)
			System.out.println(row);
		System.out.println();
	}
}
