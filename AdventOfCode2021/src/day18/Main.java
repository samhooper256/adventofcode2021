package day18;

import java.io.*;
import java.util.*;

public class Main {

	static List<Pair> nodes;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day18/input.txt"));
		nodes = br.lines().map(NodeParser::parse).toList();
		Pair answer = (Pair) nodes.stream().map(Node::copy).reduce(Main::add).get();
		System.out.println(answer.magnitude()); //part1
		part2();
	}
	
	static void part2() {
		long max = Long.MIN_VALUE;
		for(int i = 0; i < nodes.size(); i++) {
			for(int j = 0; j < nodes.size(); j++) {
				if(i == j)
					continue;
				max = Math.max(max, add(nodes.get(i), nodes.get(j)).magnitude());
			}
		}
		System.out.println(max);
	}
	
	static Pair add(Node left, Node right) {
		left = left.copy();
		right = right.copy();
		Pair result = new Pair(null, left, right);
		left.setParent(result);
		right.setParent(result);
		result.reduce();
		return result;
	}
	
}
