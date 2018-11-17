package com.tpwang.adts.list;

import com.tpwang.adts.list.ListNode;

/***
 * Flexible Stack
 * @author Peter
 *
 */
public class FlexStack {
	private ListNode top;
	private int size;
	
	public int getTop() { return top.getValue(); }
	
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
	public FlexStack push(int value) {
		ListNode newNode = new ListNode(value, null, null);
		return push(newNode);
	}
	
	/***
	 * Add a node to top of stack (by node)
	 * @param node
	 * @return FlexStack
	 */
	public FlexStack push(ListNode node) {
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
	public ListNode pop() {
		ListNode ret = null;
		if (size > 1) {
			top = top.getNext();
			ret = top.getPrevious();
			top.setPrevious(null);
			size--;
		} else if (size == 1) {
			ret = new ListNode(top.getValue(), null, null);
			clear();
		}
		return ret;
	}
	
	/***
	 * Bulk remove numToRemove nodes
	 * @param numToRemove
	 * @return stack after popping numToRemove nodes
	 */
	public FlexStack pop(int numToRemove) {
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
	public FlexStack clear() {
		top = null;
		size = 0;
		return this;
	}
	
	/***
	 * Get value by depth, top node has depth 0
	 * @param depth
	 * @return Integer
	 */
	public Integer getValueByDepth(int depth) {
		if (depth >= size || depth < 0)
			return null;
		
		ListNode current = top;
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
		
		ListNode current = top;
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
	public FlexStack reverse() {
		FlexStack reversed = new FlexStack();
		ListNode current = top;
		
		for (int i = 0; i < size; i++) {
			reversed.push(current.getValue());
			current = current.getNext();
		}
		
		return reversed;
	}
}
