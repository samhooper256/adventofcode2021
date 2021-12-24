package day5;

import java.util.Iterator;

public record Line(int row1, int col1, int row2, int col2) implements Iterable<int[]> {

	boolean isHorizontalOrVertical() {
		return col1 == col2 || row1 == row2;
	}
	
	boolean isDiagonal() {
		return !isHorizontalOrVertical();
	}
	
	int[] deltas() {
		int dr = row2() - row1(), dc = col2() - col1();
		if(dr != 0)
			dr /= Math.abs(dr);
		if(dc != 0)
			dc /= Math.abs(dc);
		return new int[] {dr, dc};
	}
	
	@Override
	public Iterator<int[]> iterator() {
		return new Iterator<int[]>() {
			int[] delta = deltas(), last = {row1() - delta[0], col1() - delta[1]};

			@Override
			public boolean hasNext() {
				return last == null || last[0] != row2() || last[1] != col2();
			}

			@Override
			public int[] next() {
				last[0] += delta[0];
				last[1] += delta[1];
				return new int[] {last[0], last[1]};
			}
			
		};
	}
	
}
