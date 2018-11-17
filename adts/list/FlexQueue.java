package com.tpwang.adts.list;

import com.tpwang.adts.list.ListNode;

/***
 * Flexible Queue
 * @author Peter
 *
 */
public class FlexQueue {
	private ListNode head, tail;
	private int size;
	
	public int getFront() { return head.getValue(); }
	
	public int getEnd() { return tail.getValue(); }
	
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
	public FlexQueue push(int value) {
		ListNode newNode = new ListNode(value, null, null);
		return push(newNode);
	}
	
	/***
	 * Push value to the end of the queue (by node)
	 * @param node
	 * @return
	 */
	public FlexQueue push(ListNode node) {
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
	public ListNode pop() {
		ListNode ret = null;
		if (size == 0)
			return null;
		
		if (size > 1) {
			head = head.getNext();
			ret = head.getPrevious();
			head.setPrevious(null);
			size--;
		} else if (size == 1) {
			ret = new ListNode(head.getValue(), null, null);
			clear();
		}
		
		return ret;
	}
	
	/***
	 * Bulk remove numToRemove nodes
	 * @param numToRemove
	 * @return queue after popping numToRemove nodes
	 */
	public FlexQueue pop(int numToRemove) {
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
	
	public FlexQueue clear() {
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
		
		ListNode current = head;
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
	public FlexQueue reverse() {
		FlexQueue reversed = new FlexQueue();
		FlexStack stack = new FlexStack();		// temporary stack used to reverse
		ListNode current = head;
		
		for (int i = 0; i < size; i++) {
			stack.push(current.getValue());
			current = current.getNext();
		}
		
		for (int j = 0; j < size; j++)
			reversed.push(stack.pop());
		
		return reversed;
	}
}
