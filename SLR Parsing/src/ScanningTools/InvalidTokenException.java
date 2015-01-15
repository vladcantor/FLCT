package ScanningTools;

public class InvalidTokenException extends Exception {
	
	public InvalidTokenException(String s, int line) {
		super("At line " + line + ": Lexical error: " + s);
	}
}
