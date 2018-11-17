package com.tpwang.adts;

public class Node<T> {
	
	private T value;
	
	public T getValue() { return value; }
	
	public Node<T> setValue(T value) { this.value = value; return this; }
	
	@Override
	public String toString() { return String.valueOf(value); }

}
