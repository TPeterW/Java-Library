package com.tpwang.test.adts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tpwang.adts.tree.trie.RetrievalTree;

class RetrievalTreeTest {

	@Test
	void testBasics() {
		RetrievalTree trie = new RetrievalTree();
		trie.insert("apple");
		trie.insert("application");
		trie.insert("apply");
		assertEquals(true, trie.contains("apple"));
		assertEquals(true, trie.contains("application"));
		assertEquals(true, trie.contains("apply"));
		assertEquals(false, trie.contains("app"));
	}

}
