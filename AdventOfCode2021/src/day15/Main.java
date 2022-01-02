package day15;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import utils.Point;

/** This solution assumes the cave is square. */
public class Main {
	
	static final class Vertex implements Comparable<Vertex> {
		
		final int n;
		int dist;
		
		Vertex(int n, int dist) {
			this.n = n;
			this.dist = dist;
		}
		
		@Override
		public int compareTo(Vertex o) {
			int c = Integer.compare(dist, o.dist);
			if(c != 0)
				return c;
			return Integer.compare(n, o.n);
		}
		
	}
	
	static final int DISPLAY_PADDING = 1;
	static int[][] firstTile;
	static int[][] risk, lowest;
	static int SIZE, TILE_SIZE;
	static TreeSet<Vertex> Q;
	static Vertex[] vertices;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day15/input.txt"));
		firstTile = br.lines().map(Main::lineToArray).toArray(int[][]::new);
		TILE_SIZE = firstTile.length;
		SIZE = TILE_SIZE * 5;
		risk = new int[SIZE][SIZE];
		for(int r = 0; r < TILE_SIZE; r++)
			for(int c = 0; c < TILE_SIZE; c++)
				risk[r][c] = firstTile[r][c];
		for(int r = 0; r < SIZE; r++) {
			for(int c = 0; c < SIZE; c++) {
				if(risk[r][c] > 0)
					continue;
				if(c >= TILE_SIZE)
					risk[r][c] = nextWrapped(risk[r][c - TILE_SIZE]);
				else
					risk[r][c] = nextWrapped(risk[r - TILE_SIZE][c]);
			}
		}
		lowest = new int[SIZE][SIZE];
		doIt();
	}
	
	static int nextWrapped(int n) {
		if(n == 9)
			return 1;
		return n + 1;
	}
	
	static void doIt() {
		Q = new TreeSet<>();
		vertices = new Vertex[SIZE * SIZE];
		for(int v = 0; v < SIZE * SIZE; v++) {
			vertices[v] = new Vertex(v, Integer.MAX_VALUE);
			Q.add(vertices[v]);
		}
		final int source = collapse(0, 0);
		vertices[source].dist = 0;
		while(!Q.isEmpty()) {
			int u = Q.pollFirst().n;
			for(int v : neighbors(u))
				innerLoop(vertices[v], u);
		}
		System.out.println(vertices[collapse(TILE_SIZE - 1, TILE_SIZE - 1)].dist);
		System.out.println(vertices[collapse(SIZE - 1, SIZE - 1)].dist);
	}
	
	static void innerLoop(Vertex v, int u) {
		if(Q.contains(v))
			innerLoopConfirmed(v, u);
	}
	
	static void innerLoopConfirmed(Vertex v, int u) {
		int alt = vertices[u].dist + risk(v.n);
		if(alt < v.dist) {
			Q.remove(v);
			v.dist = alt;
			Q.add(v);
		}
	}
	
	static void printCollapsed(int[] nums) {
		printCollapsed(nums, "");
	}
	
	static void printCollapsed(int[] nums, String linePrefix) {
		for(int r = 0; r < SIZE; r++) {
			System.out.println(linePrefix + Arrays.stream(nums, r * SIZE, (r + 1) * SIZE)
					.mapToObj(i -> String.format("%" + DISPLAY_PADDING + "d", i))
					.collect(Collectors.joining(", ", "[", "]")));
		}
		
	}
	
	static int[] neighbors(int vertex) {
		//four corners:
		if(vertex == 0)
			return new int[] {1, SIZE};
		if(vertex == SIZE - 1)
			return new int[] {SIZE - 1, SIZE * 2 - 1};
		if(vertex == SIZE * (SIZE - 1))
			return new int[] {SIZE * (SIZE - 2), SIZE * (SIZE - 1) + 1};
		if(vertex == SIZE * SIZE - 1)
			return new int[] {SIZE * (SIZE - 1) - 1, SIZE * SIZE - 2};
		
		//edges:
		if(vertex % SIZE == 0) //left edge
			return new int[] {vertex - SIZE, vertex + SIZE, vertex + 1};
		if((vertex + 1) % SIZE == 0) //right edge
			return new int[] {vertex - SIZE, vertex + SIZE, vertex - 1};
		if(vertex < SIZE) //top edge
			return new int[] {vertex - 1, vertex + 1, vertex + SIZE};
		if(vertex >= SIZE * (SIZE - 1)) //bottom edge
			return new int[] {vertex - 1, vertex + 1, vertex - SIZE};
		
		//middle:
		return new int[] {vertex - 1, vertex + 1, vertex - SIZE, vertex + SIZE};
	}
	
	static int collapse(int r, int c) {
		return r * SIZE + c;
	}
	
	static Point expand(int vertex) {
		return new Point(vertex / SIZE, vertex % SIZE);
	}
	
	static int risk(int vertex) {
		return risk[vertex / SIZE][vertex % SIZE];
	}
	
	static void printGridCompact(int[][] grid) {
		for(int[] row : grid)
			System.out.println(Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.joining()));
	}
	
	static void printGrid(int[][] grid) {
		for(int[] row : grid)
			System.out.println(Arrays.toString(row));
	}
	
	static int[] lineToArray(String line) {
		return line.chars().map(i -> i - '0').toArray();
	}
	
	static boolean inBounds(int r, int c) {
		return r >= 0 && r < SIZE && c >= 0 && c < SIZE;
	}
	
}
