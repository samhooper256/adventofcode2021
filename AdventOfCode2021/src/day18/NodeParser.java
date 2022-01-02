package day18;

public final class NodeParser {

	public static Pair parse(String str) {
		return (Pair) new NodeParser(str).parse();
	}
	
	private int index;
	private String str;
	
	private NodeParser(String str) {
		this.str = str;
		index = 0;
	}
	
	private Node parse() {
		if(str.charAt(index) == '[') {
			index++;
			Node left = parse();
			index++; //skip comma
			Node right = parse();
			index++; //skip closing ']'
			Pair pair = new Pair(null, left, right);
			left.setParent(pair);
			right.setParent(pair);
			return pair;
		}
		else {
			int end = endOfLiteral(index);
			long value = Long.parseLong(str.substring(index, end));
			Literal lit = new Literal(null, value);
			index = end;
			return lit;
		}
	}
	
	private int endOfLiteral(int start) {
		int i = start;
		while(i < str.length() && Character.isDigit(str.charAt(i)))
			i++;
		return i;
	}
	
}
