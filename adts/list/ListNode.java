package com.tpwang.adts.list;

import org.eclipse.jdt.annotation.Nullable;

import com.tpwang.adts.Node;

public class ListNode<T> extends Node<T> {
	private ListNode<T> previous, next;
	
	public ListNode(T value, @Nullable ListNode<T> previous, @Nullable ListNode<T> next) {
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
	
	public ListNode<T> getPrevious() { return previous; }
	
	public ListNode<T> setPrevious(ListNode<T> previous) { this.previous = previous; return this; }
	
	public ListNode<T> getNext() { return next; }
	
	public ListNode<T> setNext(ListNode<T> next) { this.next = next; return this; }
	
}
