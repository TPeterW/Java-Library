package com.tpwang.adts.tree.trie;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

public class TrieNode {
	
	private Character value;
	private boolean isEndOfWord;
	private Map<Character, TrieNode> children;
	
	public TrieNode(@Nullable Character value) {
		this.value = value;
		this.isEndOfWord = false;
		children = new HashMap<>();
	}
	
	public char getValue() { return value; }
	
	public boolean isEndOfWord() { return isEndOfWord; }
	public void setIsEndOfWord(boolean isEndOfWord) { this.isEndOfWord = isEndOfWord; }
	
	public @Nullable TrieNode getChild(char key) {
		return children.getOrDefault(key, null);
	}
	
	/***
	 * add child to the child collection
	 * @param newChild the added child node
	 * @return boolean whether child node already exists
	 */
	public boolean addChild(TrieNode newChild) {
		if (children.containsKey(newChild.getValue())) return true;
		children.put(newChild.getValue(), newChild);
		return false;
	}
	
	/***
	 * remove child from the child collection
	 * @param removedChild the child node to be removed
	 * @return boolean whether child node exists in the children collection
	 */
	public boolean removeChild(char key) {
		if (!children.containsKey(key)) return false;
		children.remove(key);
		return true;
	}
	
	public Map<Character, TrieNode> getChildren() { return children; }
	
}
