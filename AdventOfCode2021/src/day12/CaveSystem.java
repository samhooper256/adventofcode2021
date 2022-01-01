package day12;

import java.util.*;

public class CaveSystem {

	private final Map<String, Set<String>> adj;

	public CaveSystem(String[] lines) {
		adj = new HashMap<>();
		buildAdj(lines);
	}

	public void buildAdj(String[] lines) {
		for(String line : lines) {
			String split[] = line.split("-"), from = split[0], to = split[1];
			if(!adj.containsKey(from))
				adj.put(from, new HashSet<>());
			if(!adj.containsKey(to))
				adj.put(to, new HashSet<>());
			adj.get(from).add(to);
			adj.get(to).add(from);
		}
	}

	public int countPaths1() {
		return countPaths1("start", new HashSet<>());
	}

	private int countPaths1(String cave, Set<String> visited) {
		if(cave.equals("end"))
			return 1;
		visited.add(cave);
		int count = 0;
		for(String next : adj.get(cave)) {
			if(isSmall(next) && visited.contains(next))
				continue;
			count += countPaths1(next, visited);
		}
		visited.remove(cave);
		return count;
	}

	public int countPaths2() {
		HashMap<String, Integer> visited = new HashMap<>();
		for(String s : adj.keySet())
			visited.put(s, 0);
		return countPaths2("start", visited, false);
	}

	private int countPaths2(String cave, Map<String, Integer> visited, boolean doubleUsed) {
		if(cave.equals("end"))
			return 1;
		visited.put(cave, visited.get(cave) + 1);
		int count = 0;
		for(String next : adj.get(cave))
			if(next.equals("start"))
				continue;
			else if(next.equals("end"))
				count += countPaths2(next, visited, doubleUsed);
			else if(isSmall(next)) {
				int times = visited.get(next);
				if(times == 0)
					count += countPaths2(next, visited, doubleUsed);
				else if(times == 1 && !doubleUsed)
					count += countPaths2(next, visited, true);
			}
			else
				count += countPaths2(next, visited, doubleUsed);
		visited.put(cave, visited.get(cave) - 1);
		return count;
	}

	public static boolean isSmall(String cave) {
		return Character.isLowerCase(cave.charAt(0));
	}

}
