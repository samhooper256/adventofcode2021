package day5;

import java.util.Iterator;

public record Line(int x1, int y1, int x2, int y2) implements Iterable<int[]>{

	boolean isHorizontal() {
		return x1 == x2 || y1 == y2;
	}
	
	int col1() {
		return x1;
	}	
	
	int row1() {
		return y1;
	}
	
	int col2() {
		return x2;
	}
	
	int row2() {
		return y2;
	}
	
	int[] deltas() {
		int dr = row1() - row2(), dc = col1() - col2();
		if(dr != 0)
			dr = dr / Math.abs(dr);
		if(dc != 0)
			dc = dc / Math.abs(dc);
		return new int[] {dr, dc};
	}
	
	@Override
	public Iterator<int[]> iterator() {
		return new Iterator<int[]>() {
			int[] cur = {row1(), row2()}, delta = deltas();

			@Override
			public boolean hasNext() {
				return !(cur[0] == row2() && cur[1] == col2());
			}

			@Override
			public int[] next() {
				int[] next = {cur[0], cur[1]};
				cur[0] += delta[0];
				cur[1] += delta[1];
				return next;
			}
			
		};
	}
	
}
