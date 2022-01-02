package day18;

public abstract sealed class Node permits Literal, Pair {
	
	private Pair parent;
	
	public Node() {
		this(null);
	}
	
	public Node(Pair parent) {
		this.parent = parent;
	}
	
	public Pair parent() {
		return parent;
	}
	
	public void setParent(Pair parent) {
		this.parent = parent;
	}
	
	public boolean isPair() {
		return this instanceof Pair;
	}
	
	public boolean isLiteral() {
		return !isPair();
	}
	
	public Literal drillRight() {
		Node n = this;
		while(n instanceof Pair p)
			n = p.right();
		return (Literal) n;
	}
	
	public Literal drillLeft() {
		Node n = this;
		while(n instanceof Pair p)
			n = p.left();
		return (Literal) n;
	}
	
	public Literal nearestLeft() {
		Node behind = this;
		Pair p = parent();
		while(p != null) {
			if(behind.isRightChildOf(p))
				break;
			behind = p;
			p = p.parent();
		}
		if(p == null)
			return null;
		return p.left().drillRight();
	}
	
	public Literal nearestRight() {
		Node behind = this;
		Pair p = parent();
		while(p != null) {
			if(behind.isLeftChildOf(p))
				break;
			behind = p;
			p = p.parent();
		}
		if(p == null)
			return null;
		return p.right().drillLeft();
	}
	
	public void replace(Node replacement) {
		Pair p = parent();
		if(p == null)
			throw new IllegalStateException(String.format("No parent. this=%s", this));
		if(isLeftChildOf(p))
			p.setLeft(replacement);
		else
			p.setRight(replacement);
		replacement.setParent(p);
	}
	
	public boolean isLeftChildOf(Pair p) {
		return this ==  p.left();
	}
	
	public boolean isRightChildOf(Pair p) {
		return this ==  p.right();
	}
	
	public abstract Node search(Searcher s, int depth);
	
	public abstract long magnitude();

	public abstract Node copy();
	
}
