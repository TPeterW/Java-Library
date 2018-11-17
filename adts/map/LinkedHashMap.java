package com.tpwang.adts.map;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

public class LinkedHashMap<K, V> {	// supports only access order
	
	int maxSize;
	LRUNode<K, V> head, tail;
	Map<K, LRUNode<K, V>> map;
	List<V> list;
	
	public LinkedHashMap(int size) {
		maxSize = size;
		map = new HashMap<>();
		list = new LinkedList<>();
		head = null;
		tail = null;
	}
	
	public void insert(K key, V value) {
		if (maxSize <= 0) return;
		
		LRUNode<K, V> node = new LRUNode<K, V>(key, value);
		map.put(key, node);
		if (head == null) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			node.prev = tail;
			tail = tail.next;
		}
		// exceeded size limit
		if (map.size() > maxSize) {
			map.remove(head.getKey());
			head = head.next;
			head.prev = null;
		}
	}
	
	public @Nullable V access(K key) {
		if (!map.containsKey(key)) return null;
		
		LRUNode<K, V> ret = map.get(key);
		if (ret.next == null) /* is tail */ return ret.getValue();
		if (ret.prev == null) { // is head
			head = head.next;
			head.prev = null;
			tail.next = ret;
			tail = tail.next;
			return ret.getValue();
		}
		ret.prev.next = ret.next;
		ret.next.prev = ret.prev;
		tail.next = ret;
		tail = tail.next;
		return ret.getValue();
	}
}
