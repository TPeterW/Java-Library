package com.tpwang.test.adts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tpwang.adts.map.LinkedHashMap;

class LinkedHashMapTest {

	@Test
	void testBasics() {
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(3);
		map.insert(1, 1);
		map.insert(2, 2);
		map.insert(3, 3);
		assertEquals(1, map.access(1).intValue());
		assertEquals(2, map.access(2).intValue());
		assertEquals(3, map.access(3).intValue());
	}
	
	@Test
	void testOversize() {
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(3);
		map.insert(1, 1);
		map.insert(2, 2);
		map.insert(3, 3);
		map.insert(4, 4);
		assertEquals(null, map.access(1));
		assertEquals(2, map.access(2).intValue());
		assertEquals(3, map.access(3).intValue());
		assertEquals(4, map.access(4).intValue());
	}
	
	@Test
	void testAccess() {
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(3);
		map.insert(1, 1);
		map.insert(2, 2);
		map.insert(3, 3);
		map.access(1);
		map.insert(4, 4);
		assertEquals(1, map.access(1).intValue());
		assertEquals(null, map.access(2));
		assertEquals(3, map.access(3).intValue());
		assertEquals(4, map.access(4).intValue());
	}
	
	@Test
	void testAccessEmpty() {
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(3);
		assertEquals(null, map.access(5));
	}
	
	@Test
	void testAccessOne() {
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(3);
		map.insert(1, 1);
		assertEquals(1, map.access(1).intValue());
		map.insert(2, 2);
		assertEquals(2, map.access(2).intValue());
	}
}
