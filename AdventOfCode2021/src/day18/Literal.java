package day18;

public final class Literal extends Node {

	private long value;
	
	public Literal(long value) {
		this(null, value);
	}
	
	public Literal(Pair parent, long value) {
		super(parent);
		this.value = value;
	}
	
	public long value() {
		return value;
	}
	
	public void add(long n) {
		value += n;
	}
	
	public void split() {
		Literal down = new Literal((long) Math.floor(value() / 2.)), up = new Literal((long) Math.ceil(value() / 2.));
		Pair p = new Pair(down, up);
		down.setParent(p);
		up.setParent(p);
		replace(p);
	}
	
	
	@Override
	public long magnitude() {
		return value();
	}

	@Override
	public String toString() {
		return String.valueOf(value());
	}

	@Override
	public Node search(Searcher s, int depth) {
		if(s.test(this, depth))
			return this;
		return null;
	}

	@Override
	public Literal copy() {
		return new Literal(value());
	}
	
}
