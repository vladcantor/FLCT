package ScanningTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ScanningTools.Constants;

public class CodificationTable {
	
	private static CodificationTable table = null;
	private Map<String, Integer> codingMap;
	
	private CodificationTable() throws IOException {
		codingMap = new HashMap<>();
		load();
	}
	
	public static CodificationTable getInstance() throws IOException {
		if (table == null) {
			table = new CodificationTable();
		}
		return table;
	}
	
	private void load() throws IOException {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(Constants.CODIFICATION_TABLE_FILE));
			String line;
			String tokens[];
			
			while ((line = reader.readLine()) != null) {
				tokens = line.split(Constants.COLUMN_DELIMITER);
				codingMap.put(tokens[0], Integer.parseInt(tokens[1]));
			}
		}
		finally {
			if (reader != null)
				reader.close();
		}
	}
	
	public int getCode(String token) {
		return codingMap.get(token);
	}
}
