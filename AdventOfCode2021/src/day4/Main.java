package day4;

import java.io.*;
import java.util.*;

public class Main {

	static int[] calls;
	static List<Board> boards;
	
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("src/day4/input.txt"));
		List<String> lines = br.lines().toList();
		calls = Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
		boards = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Board>[] winners = new List[calls.length];
		for(int i = 0; i < winners.length; i++)
			winners[i] = new ArrayList<>();
		
		for(int i = 2; i <= lines.size(); i += 6)
			boards.add(new Board(lines.subList(i, i + 5)));
		
		int callIndex;
		for(callIndex = 0; callIndex < calls.length; callIndex++) {
			call(calls[callIndex], winners[callIndex]);
		}
		
		for(int i = 0; i < calls.length; i++) {
			if(winners[i].size() > 0) {
				Board winner = winners[i].get(0);
				System.out.println(winner.score(calls[i]));
				break;
			}
		}
		
		for(int i = calls.length - 1; i >= 0; i--) {
			if(winners[i].size() > 0) {
				Board winner = winners[i].get(0);
				System.out.println(winner.score(calls[i]));
				break;
			}
		}
	}
	
	static void call(int num, List<Board> winnerList) {
		boards.removeIf(b -> {
			b.called(num);
			if(b.won()) {
				winnerList.add(b);
				return true;
			}
			return false;
		});
	}
	
	static Board winner() {
		return boards.stream().filter(Board::won).findFirst().orElse(null);
	}
}
