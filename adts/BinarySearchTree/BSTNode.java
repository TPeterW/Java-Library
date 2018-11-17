package com.tpwang.adts.BinarySearchTree;

import com.tpwang.adts.Node;

public class BSTNode extends Node {
	private BSTNode parent, leftChild, rightChild;
	
	public BSTNode (BSTNode parent, int value) {
		this.parent = parent;
		this.setValue(value);
	}
	
	public BSTNode getParent() { return parent; }
	public BSTNode setParent(BSTNode parent) { this.parent = parent; return this; }
	
	public BSTNode getLeftChild() { return leftChild; }
	public BSTNode setLeftChild(BSTNode leftChild) { this.leftChild = leftChild; return this; }
	
	public BSTNode getRightChild() { return rightChild; }
	public BSTNode setRightChild(BSTNode rightChild) { this.rightChild = rightChild; return this; }
	
	
	public boolean hasLeftChild() {
		return leftChild != null;
	}
	
	public boolean hasRightChild() {
		return rightChild != null;
	}
	
	public void removeChild(int value) {
		if (leftChild != null && leftChild.getValue() == value)
			leftChild = null;
		if (rightChild != null && rightChild.getValue() == value)
			rightChild = null;
	}
	
	public void modifyChild(int valueToModify, BSTNode newChild) {
		if (leftChild != null && leftChild.getValue() == valueToModify)
			leftChild = newChild;
		
		if (rightChild != null && rightChild.getValue() == valueToModify)
			rightChild = newChild;
	}
	
	public boolean isRoot() {
		return this.getParent() == null;
	}
	
	public void removeParent() {
		this.parent = null;
	}
	
}
