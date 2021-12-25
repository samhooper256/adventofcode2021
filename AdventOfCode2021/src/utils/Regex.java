package utils;

import java.util.regex.Pattern;

public final class Regex {
	
	private Regex() {
		
	}
	
	public static final Pattern
			COMMA = Pattern.compile(","),
			SPACE = Pattern.compile(" ");
	
}
