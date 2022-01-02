package day18;

public final class Pair extends Node {

	private Node left, right;
	
	public Pair(Node left, Node right) {
		this(null, left, right);
	}
	
	public Pair(Pair parent, Node left, Node right) {
		super(parent);
		this.left = left;
		this.right = right;
	}
	
	public Node left() {
		return left;
	}
	
	public Node right() {
		return right;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	@Override
	public Node search(Searcher s, int depth) {
		if(s.test(this, depth))
			return this;
		Node result = left().search(s, depth + 1);
		if(result != null)
			return result;
		return right().search(s, depth + 1);
	}

	public void explode() {
		if(!(left() instanceof Literal left) || !(right() instanceof Literal right))
			throw new IllegalStateException(String.format("Cannot explode: %s", this));
		Literal nearestLeft = nearestLeft();
		if(nearestLeft != null)
			nearestLeft.add(left.value());
		Literal nearestRight = nearestRight();
		if(nearestRight != null)
			nearestRight.add(right.value());
		replace(new Literal(0));
	}
	
	public void reduce() {
		while(true) {
			Pair e = (Pair) search((n, d) -> n.isPair() && d >= 4, 0);
			if(e != null) {
				e.explode();
				continue;
			}
			Literal s = (Literal) search((n, d) -> n instanceof Literal l && l.value() >= 10, 0);
			if(s != null) {
				s.split();
				continue;
			}
			break;
		}
	}

	
	@Override
	public Pair copy() {
		Node left = left().copy(), right = right().copy();
		Pair copy = new Pair(left, right);
		left.setParent(copy);
		right.setParent(copy);
		return copy;
	}

	@Override
	public long magnitude() {
		return 3 * left().magnitude() + 2 * right().magnitude();
	}

	@Override
	public String toString() {
		return String.format("[%s,%s]", left(), right());
	}
	
}
