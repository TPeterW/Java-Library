package com.tpwang.adts;

public class Node {
	private int value;
	
	public int getValue() { return value; }
	
	public Node setValue(int value) { this.value = value; return this; }
	
	@Override
	public String toString() { return String.valueOf(value); }
}
