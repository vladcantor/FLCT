package slr;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import domain.Grammar;
import domain.Item;
import domain.ParsingTable;
import domain.State;

public class TableGenerator {
	private static int index = 0;
	
	private static Set<State> canonicalCollection(Grammar g) {
		List<State> cc = new ArrayList<State>();
		
		String[] val = {g.getStartingSymbol()};
		Item start = new Item("S'", Arrays.asList(val), 0);
		Set<Item> startSet = new HashSet<>();
		startSet.add(start);
		List<String> allSymbols = new ArrayList<>(g.getNonterminals());
		allSymbols.addAll(new ArrayList<>(g.getTerminals()));
		
		State s0 = closure(g, startSet);
		s0.setName("s" + index++);
		cc.add(s0);
		List<State> cc1 = null;
		
		do {
			for (int i = 0; i < cc.size(); i++) {
				cc1 = new ArrayList<>(cc); 
				for (String symbol : allSymbols) {
					State t = goto1(cc.get(i), symbol, g);
					if (t != null) {
						if (!cc.contains(t)) {
							t.setName("s" + index++);
							cc.get(i).addGoTo(symbol, t);
							cc.add(t);
						}
						else 
							cc.get(i).addGoTo(symbol, cc.get(cc.indexOf(t)));
					}
				}
			}
		} while (!cc.equals(cc1));
		return new HashSet<>(cc);
	}
	
	private static State closure(Grammar g, Set<Item> items) {
		List<Item> closure = new ArrayList<>(items);
		Set<Item> closure1 = null;
		do {
			closure1 = new HashSet<>(closure);
			for (int i = 0; i < closure.size(); i++) {
				if (closure.get(i).dotPosition() < closure.get(i).right().size()) {
					String symbol = closure.get(i).right().get(closure.get(i).dotPosition());
					for (List<String> right : g.getProductionsOf(symbol)) {
						Item item = new Item(symbol, right, 0);
						if (!closure.contains(item))
							closure.add(item);
					}
				}
			} 
		} while (!closure1.equals(new HashSet<>(closure)));
	
		State s = null;
		if (! closure.isEmpty()) {
			s = new State();
			s.setItemSet(closure1);
		}
		return s;
	}
	
	private static State goto1(State s, String x, Grammar g) {
		Set<Item> cl = new HashSet<>();
		Set<Item> items = s.getItems();
		for (Item i : items) {
			if (i.dotPosition() == i.right().indexOf(x))
				if (i.dotPosition() < i.right().size())
					cl.add(new Item(i.left(), i.right(), i.dotPosition() + 1));
				else
					cl.add(new Item(i.left(), i.right(), i.dotPosition()));
		}
		State st = closure(g, cl);
		return st;
		
	}
	
	public static ParsingTable generateTable(Grammar g) throws Exception {
		Set<State> cc = canonicalCollection(g);
		List<String> allSymbols = new ArrayList<>(g.getNonterminals());
		allSymbols.addAll(new ArrayList<>(g.getTerminals()));
		ParsingTable t = new ParsingTable(cc.size(), allSymbols.size() + 1);
		
		for (State s : cc)
			for (String sym : allSymbols) 
				setAction(s, sym, g, t);
		t.print();
		return t;
	}
	
	private static void setAction(State s, String sym, Grammar g, ParsingTable t) throws Exception {
		if (g.getNonterminals().contains(sym) && s.gotoOf(sym) != null)
			t.add(s.getName(), sym, s.gotoOf(sym).getName());
		for (Item i : s.getItems()) {
			if (i.left().equals("S'") && i.right().size() == 1 && i.right().get(0) == g.getStartingSymbol()
					&& i.dotPosition() == 1)
				t.add(s.getName(), "$", "a");
			if (i.dotPosition() == i.right().size()) {
				Set<String> follow = follow(i.left(), g);
				for (String fl : follow) 
					t.add(s.getName(), fl, "r@@"+g.getProductionNumber(i.left(), i.right()));
				return;
			}
			for (String term : g.getTerminals()) {
				if (s.gotoOf(term)!= null)
					t.add(s.getName(), term, "s@@"+s.gotoOf(term).getName());
			}
		}
	}
	
	private static Set<String> follow(String n, Grammar g) {
		Set<String> follow = new HashSet<>();
		if (g.getTerminals().contains(n))
			return follow;
		if (n.equals(g.getStartingSymbol()))
			follow.add("$");
		for (String nont : g.getNonterminals()) 
			for (List<String> rhs : g.getProductionsOf(nont)) {
				int found = rhs.indexOf(n);
				if (found >= 0 && found < rhs.size() - 1) {
					Set<String> first = first(rhs.subList(found + 1, rhs.size()), g);
					if (first.contains("$"))
						follow.addAll(follow(nont, g));
					first.remove("$");
					follow.addAll(first);
				}
				else if (found == rhs.size() - 1)
					follow.addAll(follow(nont, g));
			}
		return follow;
	}
	
	private static Set<String> first(List<String> sequence, Grammar g) {
		Set<String> firstSeq = new HashSet<String>();
		int i;
		
		for (i = 0; i < sequence.size(); i++) {
			Set<String> first = first1(sequence.get(i), g);
			boolean contains = first.contains("$");
			first.remove("$");
			firstSeq.addAll(first);
			if (!contains)
				break;
		}
		
		if (i == sequence.size())
			firstSeq.add("$");
		
		return firstSeq;
	}
	
	private static Set<String> first1(String n, Grammar g) {
		Set<String> first = new HashSet<String>();
		if (g.getTerminals().contains(n)) {
			first.add(n);
			return first;
		}
		List<List<String>> prods = g.getProductionsOf(n);
		for (List<String> prod : prods) {
			if (prod.size() == 1 && prod.get(0).equals("e*"))
				first.add("$");
			else {
				boolean containsEmpty = true;
				for (int i = 0; i < prod.size(); i ++) {
					if (!containsEmpty)
						break;
					else {
						Set<String> f1 = first1(prod.get(i), g);
						if (! f1.contains("$"))
							containsEmpty = false;
						f1.remove("$");
						first.addAll(f1);
					}
				}
			}
		}
		return first;
	}
}
