package domain;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Grammar {

	private String filepath = null;
	private Set<String> terminals;
	private Set<String> nonterminals;
	private String startingSymbol;
	private Map<String, List<List<String>>> productions;
	
	public Grammar(Set<String> terminals, Set<String> nonterminals, String startingSymbol) {
		this.terminals = terminals;
		this.nonterminals = nonterminals;
		this.startingSymbol = startingSymbol;
		this.productions= new HashMap<String, List<List<String>>>();
	}
	
	public Grammar getExtendedGrammar(String newSym) {
		String[] value = {startingSymbol};
		this.addProduction(newSym, Arrays.asList(value));
		return this;
	}
	
	
	public Grammar(String grammar) throws IOException {
		this.filepath = grammar;
		this.terminals = new HashSet<String>();
		this.nonterminals = new HashSet<String>();
		this.productions = new HashMap<String, List<List<String>>>();
		load();
	}
	
	private void load() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.filepath));
			
			loadTerminals(br);
			loadNonterminals(br);
			loadStartingSymbol(br);
			loadProductions(br);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null)
				br.close();
		}
	}
	
	private void loadTerminals(BufferedReader br) throws IOException {
		String[] terminals = br.readLine().split(" ");
		for (String terminal : terminals) 
			this.terminals.add(terminal);
	}
	
	private void loadNonterminals(BufferedReader br) throws IOException {
		String[] nonterminals = br.readLine().split(" ");
		for (String nonterminal : nonterminals) 
			this.nonterminals.add(nonterminal);
	}
	
	private void loadStartingSymbol(BufferedReader br) throws IOException {
		this.startingSymbol = br.readLine();
	}
	
	private void loadProductions(BufferedReader br) throws IOException {
		String production = br.readLine();
		while (production != null) {
			String[] parts = production.split("->");
			if (parts.length == 2) {
				String left = parts[0];
				String[] options = {parts[1]};
				if (parts[1].contains("|"))
					options = parts[1].split("|");
				for (String o : options)
                    if (!o.trim().isEmpty())
					    addProduction(left, Arrays.asList(o.split(" ")));
			}
            production = br.readLine();
		}
	}
	
	public void addProduction(String from, List<String> to) {
		if (productions.containsKey(from)) {
			List<List<String>> values = productions.get(from);
			if (!values.contains(to))
				values.add(to);
			productions.put(from,  values);
		}
		else {
			List<List<String>> derived = new ArrayList<List<String>>();
			derived.add(to);
			productions.put(from, derived);
		}
	}
	
	public Set<String> getTerminals() {
		return this.terminals;
	}
	
	public Set<String> getNonterminals() {
		return this.nonterminals;
	}
	
	public String getStartingSymbol() {
		return this.startingSymbol;
	}
	
	public List<List<String>> getProductionsOf(String symbol) {
		if (this.productions.get(symbol) != null) 
			return this.productions.get(symbol);
		return new ArrayList<List<String>>();
	}
	
	public boolean existsProduction(String from, String to) {
		List<List<String>> all = productions.get(from);
		for (List<String> s : all) 
			if (s.contains(to))
				return true;
		return false;
	}
	
}
