package ScanningTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIterator {
	private BufferedReader reader = null;
	private String lastReadLine;
	private LexicalItemsManager manager;
	private int line;
	
	public FileIterator(String filepath) throws IOException {
		reader = new BufferedReader(new FileReader(filepath));
		lastReadLine = "";
		manager = LexicalItemsManager.getInstance();
		line = 0;
	}
	
	public Boolean hasNextToken() throws IOException {
		if (lastReadLine.isEmpty() || lastReadLine == null) {
			lastReadLine = reader.readLine();
			line++;
		}
		if (lastReadLine == null) {
			if (reader != null)
				reader.close();
			return false;
		}
		return true;
	}
	
	public String nextToken() throws IOException {
		String token = "";
		Character next;
		lastReadLine = lastReadLine.trim();
		while (lastReadLine.isEmpty() || lastReadLine.equals(System.lineSeparator())) {
			lastReadLine = reader.readLine().trim();
			line++;
		}
		
		while (!lastReadLine.isEmpty() && !Character.isWhitespace(next = lastReadLine.charAt(0))
				&& !manager.isOperator(next = lastReadLine.charAt(0))
				&& !manager.isSeparator(next = lastReadLine.charAt(0))
				) {
			token += next;
			lastReadLine = lastReadLine.substring(1, lastReadLine.length());
		}
		while (sequenceContainsOpenQuotes(token)) {
			while (lastReadLine.isEmpty() || lastReadLine.equals(System.lineSeparator())) {
				lastReadLine = reader.readLine().trim();
				line++;
			}
			token += lastReadLine.charAt(0);
			lastReadLine = lastReadLine.substring(1, lastReadLine.length());
		}
		if (token.length() > 0)
			return token;
		
		while (!lastReadLine.isEmpty() && manager.isOperator(next = lastReadLine.charAt(0))) {
			token += next;
			lastReadLine = lastReadLine.substring(1, lastReadLine.length());
		}
		if (token.length() > 0)
			return token;
		else {
			next = lastReadLine.charAt(0);
			token += next;
			lastReadLine = lastReadLine.substring(1, lastReadLine.length());
		}
		return token;
	}
	
	
	public int getLine() {
		return line;
	}
	
	
	private boolean sequenceContainsOpenQuotes(String s) {
		int index = s.indexOf("\"");
		if (index >= 0 && s.indexOf("\"", index + 1) >= 0)
			return false;
		if (index < 0) {
			index = s.indexOf("\'");
			if (index >= 0 && s.indexOf("\'", index + 1) >= 0)
				return false;
			if (index < 0)
				return false;
		}
		return true;
	}
}
