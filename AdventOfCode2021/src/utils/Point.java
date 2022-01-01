package utils;

public record Point(int row, int col) {

	/** from top left corner. */
	public static Point xy(int x, int y) {
		return new Point(y, x);
	}
	
	public int x() {
		return col;
	}
	
	public int y() {
		return row;
	}
	
}
