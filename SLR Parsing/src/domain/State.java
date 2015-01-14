package domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class State {
	private String name;
	private Set<Item> items;
	private Map<String, State> gotoMap;
	
	public State() {
		this.items = new HashSet<>();
		gotoMap = new HashMap<String, State>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addItem(Item i ) {
		this.items.add(i);
	}
	
	public Set<Item> getItems() {
		return this.items;
	}
	
	public void setItemSet(Set<Item> items) {
		this.items = items;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (other == null)
	    	return false;
	    if (other == this) 
	    	return true;
	    if (!(other instanceof State))
	    	return false;
	    State obj = (State)other;
	    if (this.items.size() != obj.getItems().size())
	    	return false;
	    boolean found;
	    for (Item i1 : items) {
	    	found = false;
	    	for (Item i2 : obj.getItems())
	    		if (i1.equals(i2))
	    			found = true;
	    	if (!found)
	    		return false;
	    }
	    return true;
	}
	
	public void addGoTo(String symbol, State s) {
		gotoMap.put(symbol, s);
	}
	
	public State gotoOf(String symbol) {
		return gotoMap.get(symbol);
	}
	
	public String toString() {
		String s = this.name + "= {";
		for (Item i : this.items)
			s += i.toString() + "; ";
		s += "}";
		return s + "\n";
	}
}
