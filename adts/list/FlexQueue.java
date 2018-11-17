package com.tpwang.adts.list;

import com.tpwang.adts.list.ListNode;

/***
 * Flexible Queue
 * @author Peter
 *
 */
public class FlexQueue<T> {
	private ListNode<T> head, tail;
	private int size;
	
	public T getFront() { return head.getValue(); }
	
	public T getEnd() { return tail.getValue(); }
	
	public int getSize() { return size; }
	
	/***
	 * Constructor
	 */
	public FlexQueue() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/***
	 * Push value to the end of the queue (by value)
	 * @return
	 */
	public FlexQueue<T> push(T value) {
		ListNode<T> newNode = new ListNode<T>(value, null, null);
		return push(newNode);
	}
	
	/***
	 * Push value to the end of the queue (by node)
	 * @param node
	 * @return
	 */
	public FlexQueue<T> push(ListNode<T> node) {
		if (head == null) {
			head = node;
			tail = head;
			size = 1;
			return this;
		}
		
		tail.setNext(node);
		node.setPrevious(tail);
		node.setNext(null);
		tail = tail.getNext();
		size++;
		
		return this;
	}
	
	/***
	 * Remove the first node in queue
	 * @return popped node
	 */
	public ListNode<T> pop() {
		ListNode<T> ret = null;
		if (size == 0)
			return null;
		
		if (size > 1) {
			head = head.getNext();
			ret = head.getPrevious();
			head.setPrevious(null);
			size--;
		} else if (size == 1) {
			ret = new ListNode<T>(head.getValue(), null, null);
			clear();
		}
		
		return ret;
	}
	
	/***
	 * Bulk remove numToRemove nodes
	 * @param numToRemove
	 * @return queue after popping numToRemove nodes
	 */
	public FlexQueue<T> pop(int numToRemove) {
		if (numToRemove >= size)
			return clear();
		
		if (numToRemove == 0)
			return this;
		
		if (numToRemove == 1) {
			pop();
			return this;
		} else {
			pop();
			return pop(numToRemove - 1);		// recursion
		}
	}
	
	public FlexQueue<T> clear() {
		head = tail = null;
		size = 0;
		return this;
	}
	
	/***
	 * Print out the stack from top to bottom
	 * @return [n1, n2, n3, ..., n?]
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		
		if (size == 0)
			return builder.append(" ]").toString();
		
		ListNode<T> current = head;
		while (!current.isTail()) {		// has next
			builder.append(current.getValue());
			builder.append(", ");
			current = current.getNext();
		}
		
		builder.append(current.getValue());
		
		return builder.append(']').toString();
	}
	
	/***
	 * Generate a new queue that is the reverse of the current one
	 * @return
	 */
	public FlexQueue<T> reverse() {
		FlexQueue<T> reversed = new FlexQueue<T>();
		FlexStack<T> stack = new FlexStack<T>();		// temporary stack used to reverse
		ListNode<T> current = head;
		
		for (int i = 0; i < size; i++) {
			stack.push(current.getValue());
			current = current.getNext();
		}
		
		for (int j = 0; j < size; j++)
			reversed.push(stack.pop());
		
		return reversed;
	}
}
