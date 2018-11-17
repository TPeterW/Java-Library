package com.tpwang.adts.list;

import com.tpwang.adts.Node;

public class ListNode extends Node {
	private ListNode previous, next;
	
	public ListNode(int value, ListNode previous, ListNode next) {
		setValue(value);
		this.previous = previous;
		this.next = next;
	}
	
	public boolean isHead() {
		return previous == null;
	}
	
	public boolean isTail() {
		return next == null;
	}
	
	public ListNode getPrevious() { return previous; }
	
	public ListNode setPrevious(ListNode previous) { this.previous = previous; return this; }
	
	public ListNode getNext() { return next; }
	
	public ListNode setNext(ListNode next) { this.next = next; return this; }
	
}
