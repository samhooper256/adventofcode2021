package day12;

import java.io.*;

public class Main {

	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day12/input.txt"));
		CaveSystem cs = new CaveSystem(br.lines().toArray(String[]::new));
		System.out.println(cs.countPaths1());
		System.out.println(cs.countPaths2());
	}

}