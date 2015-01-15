package ScanningResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SymbolTable {
	private List<String> table;
	private List<Integer> positions;
	private String line = "---------------------";
	
	public SymbolTable() {
		table = new ArrayList<>();
		positions = new ArrayList<>();
	}
	
	private int indexOf(String item) {
		String[] arrTable = table.toArray(new String[0]);
		return Arrays.binarySearch(arrTable, item);
	}
	
	private int insert(int index, String item) {
		table.add(index, item);
		positions.add(index, positions.size());
		return index;
	}
	
	public int getPositionInST(String item) {
		int index = indexOf(item);
		if (index < 0)
			index = insert(-(index + 1), item);
		return positions.get(index);
	}
	
	public void saveToFile(String file) throws IOException {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.printf("%1$-10s | %2$-10s", "Position", "Token");
			writer.println();
			writer.printf(line);
			writer.println();
			for(int i = 0; i < table.size(); i++) {
				writer.printf("%1$-10d | %2$-10s", positions.get(i), table.get(i));
				writer.println();
			}
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
