package com.tpwang.adts.tree;

import com.tpwang.adts.Node;

public class BSTNode<T> extends Node<T> {
	
	private BSTNode<T> parent, leftChild, rightChild;
	
	public BSTNode (BSTNode<T> parent, T value) {
		this.parent = parent;
		this.setValue(value);
	}
	
	public BSTNode<T> getParent() { return parent; }
	public BSTNode<T> setParent(BSTNode<T> parent) { this.parent = parent; return this; }
	
	public BSTNode<T> getLeftChild() { return leftChild; }
	public BSTNode<T> setLeftChild(BSTNode<T> leftChild) { this.leftChild = leftChild; return this; }
	
	public BSTNode<T> getRightChild() { return rightChild; }
	public BSTNode<T> setRightChild(BSTNode<T> rightChild) { this.rightChild = rightChild; return this; }
	
	
	public boolean hasLeftChild() {
		return leftChild != null;
	}
	
	public boolean hasRightChild() {
		return rightChild != null;
	}
	
	public void removeChild(T value) {
		if (leftChild != null && leftChild.getValue().equals(value))
			leftChild = null;
		if (rightChild != null && rightChild.getValue().equals(value))
			rightChild = null;
	}
	
	public void modifyChild(T valueToModify, BSTNode<T> newChild) {
		if (leftChild != null && leftChild.getValue().equals(valueToModify))
			leftChild = newChild;
		
		if (rightChild != null && rightChild.getValue().equals(valueToModify))
			rightChild = newChild;
	}
	
	public boolean isRoot() {
		return this.getParent() == null;
	}
	
	public void removeParent() {
		this.parent = null;
	}
	
}
