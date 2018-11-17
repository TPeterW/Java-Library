package com.tpwang.test.adts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tpwang.adts.list.QuickArrayList;

class ArrayListTest {

	@Test
	void testBasics() {
		QuickArrayList<Integer> list = new QuickArrayList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		assertEquals(0, list.get(0).intValue());
		assertEquals(1, list.get(1).intValue());
		assertEquals(2, list.get(2).intValue());
	}
	
	@Test
	void testOversize() {
		QuickArrayList<Integer> list = new QuickArrayList<>();
		for (int i = 0; i < 32; i++) {
			list.add(i);
		}
		for (int i = 0; i < 32; i++) {
			assertEquals(i, list.get(i).intValue());
		}
	}
	
	@Test
	void testRemove() {
		QuickArrayList<Integer> list = new QuickArrayList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.remove(1);
		assertEquals(0, list.get(0).intValue());
		assertEquals(2, list.get(1).intValue());
		assertEquals(3, list.get(2).intValue());
		assertEquals(4, list.get(3).intValue());
	}
	
	@Test
	void testRemoveAtIndex() {
		QuickArrayList<Integer> list = new QuickArrayList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.removeAtIndex(1);
		assertEquals(0, list.get(0).intValue());
		assertEquals(2, list.get(1).intValue());
		assertEquals(3, list.get(2).intValue());
		assertEquals(4, list.get(3).intValue());
	}
	
	@Test
	void testClear() {
		QuickArrayList<Integer> list = new QuickArrayList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.clear();
		assertEquals(null, list.get(0));
	}

}
