package domain;

import java.util.ArrayList;
import java.util.List;

public class ParsingTable {
	List<String> rowNames;
	List<String> columnNames;
	String[][] values;
	
	public ParsingTable(int rownr, int colnr) {
		rowNames = new ArrayList<String>();
		columnNames = new ArrayList<String>();
		values = new String[rownr][colnr];
		for (int  i = 0; i < rownr; i++) 
			for (int j = 0; j < colnr; j++)
				values[i][j] = "";
	}
	
	public void add(String rowName, String columnName, String value) throws Exception {
		if (get(rowName, columnName) != null && !get(rowName, columnName).isEmpty())
			return;//throw new Exception("The grammar is not SLR(1)");
		if (!rowNames.contains(rowName))
			rowNames.add(rowName);
		if (!columnNames.contains(columnName))
			columnNames.add(columnName);
		if (value == null)
			value = "";
		values[rowNames.indexOf(rowName)][columnNames.indexOf(columnName)] = value;
	}
	
	public String get(String rowName, String columnName) {
		if (!rowNames.contains(rowName) || !columnNames.contains(columnName))
			return null;
		return values[rowNames.indexOf(rowName)][columnNames.indexOf(columnName)];
	}
	
	public void print() {
		System.out.format("%3s", "");
		for (String s : this.columnNames) 
			System.out.format("%15s", s);
		System.out.println();
		for (int i = 0; i < this.rowNames.size(); i++) {
			System.out.format("%3s", this.rowNames.get(i));
			for (String v : this.values[i])
				System.out.format("%15s", v);
			System.out.println();
		}
	}
}
