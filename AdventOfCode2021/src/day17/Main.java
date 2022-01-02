package day17;

import java.io.*;

public class Main {

	static final int MAX_ITERATIONS = 1000;
	
	static int xmin, xmax, ymin, ymax;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day17/input.txt"));
		String[] split = br.readLine().replaceAll("[^\\d-]+", " ").strip().split(" ");
		xmin = Integer.parseInt(split[0]);
		xmax = Integer.parseInt(split[1]);
		ymin = Integer.parseInt(split[2]);
		ymax = Integer.parseInt(split[3]);
		System.out.println(ymin * (ymin + 1) / 2); //part 1
		part2();
	}
	
	static void part2() {
		int xvelmax = xmax;
		int yvelmin = ymin, yvelmax = Math.abs(yvelmin);
		int count = 0;
		for(int xvel = 0; xvel <= xvelmax; xvel++)
			for(int yvel = yvelmin; yvel <= yvelmax; yvel++)
				if(enters(xvel, yvel))
					count++;
		System.out.println(count);
	}
	
	static int restingX(int xvel) {
		int x = 0;
		while(xvel != 0) {
			x += xvel;
			if(xvel > 0)
				xvel--;
			else if(xvel < 0)
				xvel++;
		}
		return x;
	}
	
	static boolean inTargetX(int x) {
		return x >= xmin && x <= xmax;
	}
	
	static boolean inTargetArea(int x, int y) {
		return inTargetX(x) && y >= ymin && y <= ymax;
	}
	
	private static boolean enters(int xvel, int yvel) {
		int x = 0, y = 0;
		for(int step = 0; step < MAX_ITERATIONS; step++) {
			x += xvel;
			y += yvel;
			if(xvel > 0)
				xvel--;
			else if(xvel < 0)
				xvel++;
			yvel--;
			if(inTargetArea(x, y))
				return true;
		}
		return false;
	}	
	
}
