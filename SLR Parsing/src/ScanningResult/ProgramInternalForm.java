package ScanningResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
	private List<Integer> codesList;
	private List<Integer> positionsList;
	private String line = "---------------------";
	
	public ProgramInternalForm() {
		codesList = new ArrayList<>();
		positionsList = new ArrayList<>();
	}
	
	public void add(Integer code, Integer STposition) {
		codesList.add(code);
		positionsList.add(STposition);
	}
	
	public void saveToFile(String file) throws IOException {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.printf("%1$-10s | %2$-10s", "Code", "Position in ST");
			writer.println();
			writer.printf(line);
			writer.println();
			for(int i = 0; i < codesList.size(); i++) {
				writer.printf("%1$-10d | %2$-10d", codesList.get(i), positionsList.get(i));
				writer.println();
			}
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
