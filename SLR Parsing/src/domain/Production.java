package domain;

import java.util.List;

public class Production {
	private String from;
	private List<String> to;
	private Integer pos;
	
	public Production (String from, List<String> to, Integer pos) {
		this.from = from;
		this.to = to;
		this.pos = pos;
	}
	
	public String from() {
		return this.from;
	}
	
	public List<String> to() {
		return this.to;
	}
	
	public Integer position() {
		return this.pos;
	}
}
