package com.tpwang.adts.list;

public class DoublyLinkedList<T> {
	private ListNode<T> head, tail;
	private int size;
	
	public T getHead() { return head.getValue();}
	
	public T getTail() { return tail.getValue(); }
	
	public int size() { return size; }
	
	/***
	 * Constructor
	 */
	public DoublyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/***
	 * print from head to tail
	 */
	public void printAll() {
		if (size == 0)
			return;
		
		ListNode<T> current = head;
		while (!current.isTail()) {
			System.out.print(current.getValue() + " ");
			current = current.getNext();
		}
		
		System.out.println(current.getValue());
	}
	
	/***
	 * print from tail to head
	 */
	public void printAllReversed() {
		ListNode<T> current = tail;
		while (!current.isHead()) {
			System.out.print(current.getValue() + " ");
			current = current.getPrevious();
		}
		
		System.out.println(current.getValue());
	}
	
	/***
	 * add value to the end of the DLL
	 * @param value the number to add
	 * @return DoublyLinkedList after the addition
	 */
	public DoublyLinkedList<T> add(T value) {
		if (tail == null) {		// list is empty
			head = new ListNode<T>(value, null, null);
			tail = head;
			size = 1;
			return this;
		}
		
		tail.setNext(new ListNode<T>(value, tail, null));
		tail = tail.getNext();
		size++;
		
		return this;
	}
	
	/***
	 * insert at index
	 * if positive, insert before the index
	 * if negative, insert after the negative index
	 * @param value the number the add
	 * @param index position to add the value
	 * @return DoublyLinkedList after the addition
	 */
	public DoublyLinkedList<T> insertAt(T value, int index) {
		ListNode<T> current;
		if (index < 0) {
			if (Math.abs(index) > size)
				return null;
			current = tail;
			for (int i = size - 1; i > size + index; i--)
				current = current.getPrevious();
		} else {
			if (index >= size)
				return null;
			current = head;
			for (int i = 0; i < index; i++)
				current = current.getNext();
		}
		
		if (current.isHead()) {
			head.setPrevious(new ListNode<T>(value, null, head));
			head = head.getPrevious();
		} else if (current.isTail() && index < 0) {
			tail.setNext(new ListNode<T>(value, tail, null));
			tail = tail.getNext();
		} else {			// neither head nor tail
			ListNode<T> newNode = new ListNode<T>(value, current.getPrevious(), current);
			if (!current.isHead())
				current.getPrevious().setNext(newNode);
			if (!current.isTail())
				current.getNext().setPrevious(newNode);
		}
		
		size++;
		
		return this;
	}
	
	/***
	 * insert a value at the start of list
	 * @param value the number to add
	 * @return DoublyLinkedList after the addition
	 */
	public DoublyLinkedList<T> insertAtStart(T value) {
		head.setPrevious(new ListNode<T>(value, null, head));
		head = head.getPrevious();
		
		return this;
	}
	
	/***
	 * insert a value at the end of list
	 * @param value the number to add
	 * @return DoublyLinkedList after the addition
	 */
	public DoublyLinkedList<T> insertAtEnd(T value) {
		tail.setNext(new ListNode<T>(value, tail, null));
		tail = tail.getNext();
		
		return this;
	}
	
	/***
	 * remove the first node with value
	 * returns true if successful, false otherwise
	 * @param value the number to remove
	 * @return removal successful or not
	 */
	public boolean remove(T value) {
		if (size == 0)
			return false;
		
		int index = indexOf(value);
		
		ListNode<T> current = head;
		
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		removeNode(current);
		
		return true;
	}
	
	/***
	 * remove the first node
	 * returns true if successful, false otherwise
	 * @return removal successful or not
	 */
	public boolean removeFromStart() {
		if (size == 0)
			return false;
		if (size == 1) {
			size = 0;
			head = null;
			tail = null;
			return true;
		}
		
		head = head.getNext();
		head.setPrevious(null);
		size--;
		
		return true;
	}
	
	/***
	 * remove the last node
	 * returns true if successful, false otherwise
	 * @return removal successful or not
	 */
	public boolean removeFromEnd() {
		if (size == 0)
			return false;
		if (size == 1) {
			size = 0;
			head = null;
			tail = null;
			return true;
		}
		
		tail = tail.getPrevious();
		tail.setNext(null);
		size--;
		
		return true;
	}
	
	/***
	 * remove the node at index
	 * positive means starting from beginning, negative means starting from end
	 * returns true if successful, false if out of boundary
	 * @param index position of number to remove
	 * @return removal succesful or not
	 */
	public boolean removeAtIndex(int index) {
		ListNode<T> current;
		
		if (index < 0) {		// start from end
			if (Math.abs(index) > size)						// out of bound
				return false;
			
			current = tail;
			for (int i = -1; i > index; i--)
				current = current.getPrevious();
		} else {				// start from beginning
			if (index >= size)								// out of bound
				return false;
			
			current = head;
			for (int i = 0; i < index; i++)
				current = current.getNext();
		}
		removeNode(current);
		
		return true;
	}
	
	/***
	 * internal method to delete a specific node
	 * @param nodeToDelete
	 */
	private void removeNode(ListNode<T> nodeToDelete) {
		if (nodeToDelete.isHead()) {
			if (nodeToDelete.isTail())		// just one nodes
				removeFromEnd();
			else
				removeFromStart();
		} else if (nodeToDelete.isTail())
			removeFromEnd();
		else {
			nodeToDelete.getPrevious().setNext(nodeToDelete.getNext());
			nodeToDelete.getNext().setPrevious(nodeToDelete.getPrevious());
			size--;
		}
	}
	
	/***
	 * return the value at index
	 * if out of boundary, return the value at boundary
	 * @param index position of number to get
	 * @return value at index
	 */
	public T get(int index) {
		ListNode<T> current;
		if (index < 0) {		// start from tail
			if (Math.abs(index) > size)
				index = Math.negateExact(size);
			current = tail;
			for (int i = size - 1; i > size + index; i--)
				current = current.getPrevious();
		} else {				// start from head
			if (index >= size)
				index = size - 1;
			current = head;
			for (int i = 0; i < index; i++)
				current = current.getNext();	
		}
		
		return current.getValue();
	}
	
	/***
	 * find the first appearance of value
	 * @param value value to find
	 * @return position of the value
	 */
	public int indexOf(T value) {
		ListNode<T> current = head;
		int index = 0;
		
		while (current.getValue().equals(value)) {
			current = current.getNext();
			index++;
		}
		
		return index;
	}
	
	/***
	 * return number of occurrence of value
	 * @param value value to find occurrence of
	 * @return number of occurance of value
	 */
	public int numOf(T value) {
		int count = 0;
		ListNode<T> current = head;
		
		for (int i = 0; i < size; i++) {
			if (current.getValue().equals(value))
				count++;
			if (!current.isTail())
				current = current.getNext();
		}
		
		return count;
	}
	
	/***
	 * get truncated list from start to end
	 * only supports positive index (for now)
	 * @param start inclusive
	 * @param end exclusive
	 * @return sliced DoublyLinkedList
	 */
	public DoublyLinkedList<T> slice(int start, int end) {
		if (start < 0)
			start = 0;
		if (end >= size)
			end = size;
		
		ListNode<T> current = head;
		
		for (int i = 0; i < start; i++)
			current = current.getNext();
		
		DoublyLinkedList<T> newList = new DoublyLinkedList<T>();
		for (int i = start; i < end; i++) {
			newList.add(current.getValue());
			if (!current.isTail())
				current = current.getNext();
		}
		
		return newList;
	}
	
	/***
	 * return an identical list
	 * @return cloned list
	 */
	public DoublyLinkedList<T> clone() {
		DoublyLinkedList<T> ret = new DoublyLinkedList<T>();
		
		ListNode<T> current = head;
		for (int i = 0; i < size; i++) {
			ret.add(current.getValue());
			if (!current.isTail())
				current = current.getNext();
		}
		
		return ret;
	}
	
	/***
	 * reverse the whole list and return
	 * @return reversed list
	 */
	public DoublyLinkedList<T> reverse() {
		ListNode<T> first, second;
		
		first = head;
		second = first.getPrevious();
		
		for (int i = 0; i < size; i++) {
			ListNode<T> temp;
			if (second != null) {				// not head
				temp = second.getNext();
				second.setNext(second.getPrevious());
				second.setPrevious(temp);
			}
			else								// head
				tail = first;
			first = first.getNext();
			if (first != null)					// not tail
				second = first.getPrevious();
			else {								// second is second to tail
				second = second.getPrevious();		// current second is already reversed
				second.setNext(second.getPrevious());
				second.setPrevious(null);
				head = second;	
			}
		}
		
		return this;
	}
	
	/***
	 * return whole list in Python style
	 * @return list in String
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		ListNode<T> current = head;
		
		for (int i = 0; i < size; i++) {
			builder.append(current.getValue());
			if (!current.isTail()) {			// not the end
				current = current.getNext();
				builder.append(", ");
			}
		}
		
		builder.append(']');
		return builder.toString();
	}
}
