package main;

import LexicalScanner.LexicalScanner;
import slr.TableGenerator;
import domain.Grammar;

public class Main {
	public static void main (String args[]) {
		try {
			Grammar g = new Grammar("grammar.txt");
			String filename = "file.txt";
			System.out.println("Scanning file: " + filename);
			
			LexicalScanner.Scan(filename);
			//System.out.print(g.getStartingSymbol());
			TableGenerator.generateTable(g);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
