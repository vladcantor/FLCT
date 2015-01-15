package main;

import LexicalScanner.LexicalScanner;
import slr.TableGenerator;
import domain.Grammar;

public class Main {
	public static void main (String args[]) {
		try {
			Grammar g = new Grammar("grammar.txt");
			String display = "  - ";
			String filename = "F:/anul3/FLCT/SLR Parsing/src";
			System.out.println("Scanning file: " + filename);
			
			LexicalScanner.Scan(filename);
			//System.out.print(g.getStartingSymbol());
			TableGenerator.generateTable(g);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
