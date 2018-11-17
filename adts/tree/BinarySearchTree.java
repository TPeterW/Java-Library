package com.tpwang.adts.tree;

import java.util.*;

public class BinarySearchTree<T> {
	
	BSTNode<T> root;
	private int deepestLevel;
	private Comparator<T> comparator;
	
	public BSTNode<T> getRoot() { return root; }
	
	public BinarySearchTree() {
		root = null;
		deepestLevel = 0;
		comparator = new Comparator<T>() {
			public int compare(T o1, T o2) {
				return o1.hashCode() - o2.hashCode();
			}
		};
	}
	
	public BinarySearchTree(Comparator<T> comparator) {
		root = null;
		deepestLevel = 0;
		this.comparator = comparator;
	}
	
	/***
	 * adds a node to BST
	 * does not accept duplicate in this version
	 * returns true if successful, false if duplicate
	 * @param value
	 * @return
	 */
	public boolean addNode(T value) {
		if (root == null) {
			root = new BSTNode<T>(null, value);
			deepestLevel = 1;
			return true;
		} else {
			return insertFrom(root, value, 1);	
		}
	}
	
	/***
	 * recursively called to insert node
	 * @param current
	 * @param value
	 * @return success
	 */
	private boolean insertFrom(BSTNode<T> current, T value, int currentLevel) {
		if (value == current.getValue())
			return false;
		
		if (comparator.compare(current.getValue(), value) < 0) {			// go to right
			if (!current.hasRightChild()) {			// no right child
				current.setRightChild(new BSTNode<T>(current, value));
				if (deepestLevel < currentLevel + 1)
					deepestLevel = currentLevel + 1;
				return true;
			}
			else {											// has right child 
				return insertFrom(current.getRightChild(), value, currentLevel + 1);
			}
		} else {									// go to left
			if (!current.hasLeftChild()) {
				current.setLeftChild(new BSTNode<T>(current, value));
				if (deepestLevel < currentLevel + 1)
					deepestLevel = currentLevel + 1;
				return true;
			} else
				return insertFrom(current.getLeftChild(), value, currentLevel + 1);
		}
	}
	
	
	/***
	 * deletes a node from BST
	 * returns true if successful, false otherwise
	 * @param value
	 * @return
	 */
	public boolean deleteNode(T valueToDelete) {
		if (root == null)
			return false;
		
		if (!this.containsNode(valueToDelete))
			return false;
		
		removeFrom(root, valueToDelete);
		
		refreshDeepestLevel();
		
		return true;
	}
	
	/***
	 * recursively called to start removing from designated node
	 * @param current
	 * @param valueToDelete
	 */
	private void removeFrom(BSTNode<T> current, T valueToDelete) {
		if (current.getValue().equals(valueToDelete)) {
			if (!current.hasLeftChild() && !current.hasRightChild()) {			// leaf
				if (current.isRoot())
					root = null;
				else
					current.getParent().removeChild(current.getValue());
				return;
			}
			
			if (current.hasLeftChild() && !current.hasRightChild()) {		// has only left
				if (current.isRoot()) {
					root = root.getLeftChild();
					root.removeParent();
				} else
					current.getParent().modifyChild(current.getValue(), current.getLeftChild());
				return;
			}
			
			if (current.hasRightChild() && !current.hasLeftChild()) {		// has only right
				if (current.isRoot()) {
					root = root.getRightChild();
					root.removeParent();
				} else
					current.getParent().modifyChild(current.getValue(), current.getRightChild());
				return;
			}
			
			if (current.hasLeftChild() && current.hasRightChild()) {		// has both child
				// in-order predecessor
				BSTNode<T> predecessor = findLeftPredecessor(current);
				current.setValue(predecessor.getValue());
				replaceNode(predecessor);
			}
			
			
		} else {										// keep looking for it
			if (comparator.compare(current.getValue(), valueToDelete) < 0)	// on the right
				removeFrom(current.getRightChild(), valueToDelete);
			else															// on the left
				removeFrom(current.getLeftChild(), valueToDelete);
		}
	}
	
	/***
	 * recursively replace the value of current node with that of in-order predecessor
	 * @param current
	 */
	private void replaceNode(BSTNode<T> current) {
		if (!current.hasLeftChild() && !current.hasRightChild()) {			// leaf
			current.getParent().removeChild(current.getValue());
		} else if (!current.hasLeftChild() && current.hasRightChild()) {	// only right child
			current.getParent().modifyChild(current.getValue(), current.getRightChild());
		} else if (!current.hasRightChild() && current.hasLeftChild()) {	// only left child
			current.getParent().modifyChild(current.getValue(), current.getLeftChild());
		} else {															// has both child
			current.setValue(current.getLeftChild().getValue());
			replaceNode(current.getLeftChild());
		}
	}
	
	private BSTNode<T> findLeftPredecessor(BSTNode<T> current) {
		if (current.hasLeftChild())			// no mistake
			return findRightMost(current.getLeftChild());
		
		return current;						// no left child
	}
	
	/***
	 * recursively called to find the greatest node starting from current
	 * @param current
	 * @return
	 */
	private BSTNode<T> findRightMost(BSTNode<T> current) {
		if (current.hasRightChild())
			return findRightMost(current.getRightChild());
		
		return current;			// is right most already
	}
	
	/***
	 * checks if BST contains a value
	 * returns true if does, false otherwise
	 * @param value
	 * @return
	 */
	public boolean containsNode(T valueToFind) {
		return findNode(root, valueToFind) != null;
	}
	
	/***
	 * recursively called to find node
	 * @param current
	 * @param valueToFind
	 * @return
	 */
	public BSTNode<T> findNode(BSTNode<T> current, T valueToFind) {
		if (current.getValue().equals(valueToFind))
			return current;
		else {
			if (comparator.compare(current.getValue(), valueToFind) < 0) {			// must be on the right
				if (current.getRightChild() == null)
					return null;
				else
					return findNode(current.getRightChild(), valueToFind);
			} else {										// must be on the left
				if (current.getLeftChild() == null)
					return null;
				else
					return findNode(current.getLeftChild(), valueToFind);
			}
		}
	}
	
	/***
	 * prints out tree in level order
	 * is this dynamic programming technically?
	 */
	public void printLevelOrder() {
		//TODO: 
		ArrayList<BSTNode<T>> currentLevel = new ArrayList<BSTNode<T>>();
		ArrayList<BSTNode<T>> nextLevel = new ArrayList<BSTNode<T>>();
		
		nextLevel.add(root);
		int i, j;
		for (i = 0; i < deepestLevel; i++) {
			currentLevel.clear();
			for (j = 0; j < nextLevel.size(); j++)
				currentLevel.add(nextLevel.get(j));
			
			nextLevel.clear();
			levelOrder(root, i, currentLevel, nextLevel);			// 0 is the root level
		}
	}
	
	/***
	 * prints out tree in pre order
	 */
	public void printPreOrder() {
		preOrder(root);
		System.out.println();
	}
	
	/***
	 * prints out tree in order
	 */
	public void printInOrder() {
		inOrder(root);
		System.out.println();
	}
	
	public void printPostOrder() {
		postOrder(root);
		System.out.println();
	}
	
	/***
	 * recursively called to go through each line
	 * @param current
	 * @param level
	 * @param currentLevel
	 * @param nextLevel
	 */
	private void levelOrder(BSTNode<T> current, int level, 
			List<BSTNode<T>> currentLevel, List<BSTNode<T>> nextLevel) {
		int position;
		for (position = 0; position < currentLevel.size(); position++) {
			System.out.print(currentLevel.get(position).getValue() + "\t");
			if (currentLevel.get(position).hasLeftChild())
				nextLevel.add(currentLevel.get(position).getLeftChild());
			if (currentLevel.get(position).hasRightChild())
				nextLevel.add(currentLevel.get(position).getRightChild());
		}
		System.out.println();
	}
	
	/***
	 * recursively called to print as input order
	 * @param current
	 */
	private void preOrder(BSTNode<T> current) {
		System.out.print(current.getValue() + " ");
		if (current.hasLeftChild())
			preOrder(current.getLeftChild());
		if (current.hasRightChild())
			preOrder(current.getRightChild());
	}
	
	/***
	 * recursively called to print in-order
	 * @param current
	 */
	private void inOrder(BSTNode<T> current) {
		if (current.hasLeftChild())
			inOrder(current.getLeftChild());
		
		System.out.print(current.getValue() + " ");
		
		if (current.hasRightChild())
			inOrder(current.getRightChild());
	}
	
	/***
	 * recursively called to print post order
	 */
	private void postOrder(BSTNode<T> current) {
		if (current.hasLeftChild())
			postOrder(current.getLeftChild());
		if (current.hasRightChild())
			postOrder(current.getRightChild());
		System.out.print(current.getValue() + " ");
	}
	
	/***
	 * checks if a value is at leaf
	 * @param value
	 * @return
	 */
//	public boolean isLeaf(int value) {
//		if (!this.containsNode(value))
//			return false;
//		else {
//			// TODO: find the node, check
//			return false;
//		}
//	}
	
	/***
	 * checks if a value is at root
	 * @param value
	 * @return
	 */
	public boolean isRoot(T value) {
		if (root.getValue().equals(value))
			return true;
		
		return false;
	}
	
	/***
	 * called when a node is deleted
	 */
	private void refreshDeepestLevel() {
		if (root == null) {
			deepestLevel = 0;
			return;
		}
			
		deepestLevel = 1;
		digDeeper(root, 1);
	}
	
	/***
	 * recursively called to find deeper level
	 * @param current
	 * @param currentLevel
	 */
	private void digDeeper(BSTNode<T> current, int currentLevel) {
		if (currentLevel > deepestLevel)
			deepestLevel = currentLevel;
		
		if (current.hasLeftChild())
			digDeeper(current.getLeftChild(), currentLevel + 1);
		
		if (current.hasRightChild())
			digDeeper(current.getRightChild(), currentLevel + 1);
	}
	
	public int getDeepestLevel() {
		return deepestLevel;
	}
}
