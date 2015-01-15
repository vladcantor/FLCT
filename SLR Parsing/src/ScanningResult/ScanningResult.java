package ScanningResult;

import java.io.IOException;


public class ScanningResult {
	private ProgramInternalForm pif;
	private SymbolTable idsTable;
	private SymbolTable constantsTable;
	
	public ScanningResult() {
		pif = new ProgramInternalForm();
		idsTable = new SymbolTable();
		constantsTable = new SymbolTable();
	}
	
	public void addEntryToPIF(int code, int stPosition) {
		pif.add(code, stPosition);
	}
	
	public void addEntryToPIF(int code) {
		pif.add(code, -1);
	}
	
	public int getPositionInConstantsST(String token) {
		return constantsTable.getPositionInST(token);
	}
	
	public int getPositionInIdentifiersST(String token){
		return idsTable.getPositionInST(token);
	}
	
	public void savePIF(String file) throws IOException {
		pif.saveToFile(file);
	}
	
	public void saveConstantsST(String filename) throws IOException {
		constantsTable.saveToFile(filename);
	}
	
	public void saveIdentifiersST(String filename) throws IOException {
		idsTable.saveToFile(filename);
	}
}
