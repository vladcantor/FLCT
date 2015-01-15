package main;

import javax.naming.PartialResultException;

import LexicalScanner.LexicalScanner;
import ScanningResult.ScanningResult;
import slr.TableGenerator;
import domain.Grammar;

public class Main {
	public static void main (String args[]) {
		PartOne();
		//PartTwo();
	}
	
	private static void PartTwo(){
		try {
			Grammar g = new Grammar("grammar.txt");
			String filename = "file.txt";
			
			ScanningResult lexicalScaner = LexicalScanner.Scan(filename);
			//System.out.print(g.getStartingSymbol());
			TableGenerator.generateTable(g);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void PartOne(){
		try {
			Grammar g = new Grammar("grammar.txt");
			
			TableGenerator.generateTable(g);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
