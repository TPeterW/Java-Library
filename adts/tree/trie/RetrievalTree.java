package com.tpwang.adts.tree.trie;

public class RetrievalTree {
	
	TrieNode root;
	
	public RetrievalTree() {
		root = new TrieNode(null);
	}
	
	public void insert(String word) {
		TrieNode current = root;
		for (char c : word.toCharArray()) {
			if (current.getChild(c) == null) {
				current.addChild(new TrieNode(c));
				current = current.getChild(c);
			} else	// child exists
				current = current.getChild(c);
		}
		current.setIsEndOfWord(true);
	}
	
	public boolean contains(String word) {
		TrieNode current = root;
		for (char c : word.toCharArray()) {
			if (current.getChild(c) == null) return false;
			current = current.getChild(c);
		}
		
		return current.isEndOfWord();
	}
	
}
