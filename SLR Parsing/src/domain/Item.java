package domain;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private String left;
	private List<String> right;
	private int dotPos;
	
	public Item(String left, List<String> right, int dotPos) {
		this.left = left;
		this.right = right;
		this.dotPos = dotPos;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (other == this) 
	    	return true;
	    if (!(other instanceof Item))
	    	return false;
	    Item obj = (Item)other;
	    if (obj.right().equals(this.right) && obj.left().equals(this.left) && obj.dotPosition() == this.dotPos) 
	    	return true;
	    return false;
	}
	
	public List<String> right() {
		return this.right;
	}
	
	public String left() {
		return this.left;
	}
	
	public int dotPosition() {
		return this.dotPos;
	}
	
	public String toString() {
		List<String> cp = new ArrayList<>(this.right);
		cp.add(this.dotPos, ".");
		return this.left + "->" + cp.toString();
	}
}
