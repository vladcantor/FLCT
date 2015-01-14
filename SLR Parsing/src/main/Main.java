package main;

import slr.TableGenerator;
import domain.Grammar;

public class Main {
	public static void main (String args[]) {
		try {
			Grammar g = new Grammar("grammar.txt");
			//System.out.print(g.getStartingSymbol());
			TableGenerator.generateTable(g);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
