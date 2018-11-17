package com.tpwang.adts.map;

import org.eclipse.jdt.annotation.Nullable;

public class LRUNode<K, V> {

	private K key;
	private V value;
	public @Nullable LRUNode<K, V> prev, next;
	
	public LRUNode(K key, V value) {
		this.key = key;
		this.value = value;
		prev = null;
		next = null;
	}
	
	public V getValue() { return value; }
	public K getKey() { return key; }
}
