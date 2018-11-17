package com.tpwang.adts.list;

import com.tpwang.adts.list.ListNode;

/***
 * Flexible Stack
 * @author Peter
 *
 */
public class FlexStack<T> {
	private ListNode<T> top;
	private int size;
	
	public T getTop() { return top.getValue(); }
	
	public int getSize() { return size; }
	
	/***
	 * Constructor
	 */
	public FlexStack() {
		this.top = null;
		size = 0;
	}
	
	/***
	 * Add a node to top of stack (by value)
	 * @param value
	 * @return FlexStack
	 */
	public FlexStack<T> push(T value) {
		ListNode<T> newNode = new ListNode<T>(value, null, null);
		return push(newNode);
	}
	
	/***
	 * Add a node to top of stack (by node)
	 * @param node
	 * @return FlexStack
	 */
	public FlexStack<T> push(ListNode<T> node) {
		if (top == null) {
			top = node;
			size = 1;
			return this;
		}
		
		top.setPrevious(node);
		node.setNext(top);
		top = top.getPrevious();
		size++;
		
		return this;
	}
	
	/***
	 * Remove the top node
	 * @return popped node
	 */
	public ListNode<T> pop() {
		ListNode<T> ret = null;
		if (size > 1) {
			top = top.getNext();
			ret = top.getPrevious();
			top.setPrevious(null);
			size--;
		} else if (size == 1) {
			ret = new ListNode<T>(top.getValue(), null, null);
			clear();
		}
		return ret;
	}
	
	/***
	 * Bulk remove numToRemove nodes
	 * @param numToRemove
	 * @return stack after popping numToRemove nodes
	 */
	public FlexStack<T> pop(int numToRemove) {
		if (numToRemove > size)
			return clear();
		
		if (numToRemove == 0)
			return this;
		
		if (numToRemove == 1){
			pop();
			return this;
		} else {
			pop();
			return pop(numToRemove - 1);		// recursion
		}
	}
	
	/***
	 * Clear whole stack
	 * @return empty stack
	 */
	public FlexStack<T> clear() {
		top = null;
		size = 0;
		return this;
	}
	
	/***
	 * Get value by depth, top node has depth 0
	 * @param depth
	 * @return Integer
	 */
	public T getValueByDepth(int depth) {
		if (depth >= size || depth < 0)
			return null;
		
		ListNode<T> current = top;
		while (0 < depth--)
			current = top.getNext();
		
		return current.getValue();
	}
	
	/***
	 * Print out the stack from top to bottom
	 * @return [n1, n2, n3, ..., n?]
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		
		if (size == 0)
			return builder.append(" ]").toString();
		
		ListNode<T> current = top;
		while (!current.isTail()) {		// has next
			builder.append(current.getValue());
			builder.append(", ");
			current = current.getNext();
		}
		
		builder.append(current.getValue());
		
		return builder.append(']').toString();
	}
	
	/***
	 * Generate a new stack that is the reverse of the current one 
	 * @return FlexStack
	 */
	public FlexStack<T> reverse() {
		FlexStack<T> reversed = new FlexStack<T>();
		ListNode<T> current = top;
		
		for (int i = 0; i < size; i++) {
			reversed.push(current.getValue());
			current = current.getNext();
		}
		
		return reversed;
	}
}
