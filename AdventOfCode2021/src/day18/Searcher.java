package day18;

@FunctionalInterface
public interface Searcher {

	boolean test(Node n, int depth);
	
}
