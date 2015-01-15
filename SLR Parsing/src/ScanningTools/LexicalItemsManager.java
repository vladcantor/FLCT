package ScanningTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LexicalItemsManager {
	private List<String> separators;
	private List<String> operators;
	private List<String> reservedWords;
	private static LexicalItemsManager manager = null;
	
	private LexicalItemsManager() throws IOException {
		load();
	}
	
	public static LexicalItemsManager getInstance() throws IOException {
		if (manager == null) {
			manager = new LexicalItemsManager(); 
		}
		return manager;
	}
	
	private void load() throws IOException {
		separators = loadItemsFromFile(Constants.SEPARATORS_FILE);
		operators = loadItemsFromFile(Constants.OPERATORS_FILE);
		reservedWords = loadItemsFromFile(Constants.RESERVED_WORDS_FILE);
	}
	
	private List<String> loadItemsFromFile(String filename) throws IOException {
		List<String> resultList = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename)); 
			String line;
			while((line = reader.readLine()) != null)
				resultList.add(line);
		}
		finally {
			if (reader != null)
				reader.close();
		}
		return resultList;
	}
	
	public boolean isSeparator(String token) {
		return separators.contains(token);
	}
	
	public Boolean isOperator(String token) {
		return operators.contains(token);
	}
	
	public Boolean isReservedWord(String token) {
		return reservedWords.contains(token);
	}
	
	public boolean isSeparator(char token) {
		String strToken = "";
		strToken += token;
		return separators.contains(strToken);
	}
	
	public Boolean isOperator(char token) {
		String strToken = "";
		strToken += token;
		return operators.contains(strToken);
	}
	
	public boolean isIdentifier(String token) {
		return Pattern.matches("[a-zA-Z][a-zA-Z0-9_]{0,7}", token);
	}
	
	public boolean isConstant(String token) {
		if (Pattern.matches("\".*\"", token))
			return true;
		if (Pattern.matches("\'.\'", token))
			return true;
		if (Pattern.matches("[1-9][0-9]*", token))
			return true;
		return false;
	}
}
