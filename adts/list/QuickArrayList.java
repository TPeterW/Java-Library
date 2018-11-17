package com.tpwang.adts.list;

import java.util.Arrays;

import org.eclipse.jdt.annotation.Nullable;

@SuppressWarnings("unchecked")
public class QuickArrayList<T> {
	
	Object[] array;
	private int nextIndex, size;
	
	public QuickArrayList() {
		this(32);
	}
	
	public QuickArrayList(int initSize) {
		nextIndex = 0;
		size = Math.max(initSize, 16);
		array = new Object[initSize];
		Arrays.fill(array, null);
	}
	
	public void add(T e) {
		array[nextIndex++] = e;
		size++;
		if (size > array.length / 2) enlarge();
	}
	
	public @Nullable T get(int index) {
		if (index >= nextIndex) throw new IndexOutOfBoundsException("Index out of bounds");
		return (T) array[index];
	}
	
	public void removeAtIndex(int index) {
		array[index] = null;
		size--;
		if (size > 16 && size < array.length / 2) shrink();
		else move();
	}
	
	public void remove(T e) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == e) array[i] = null;
			size--;
		}
		if (size > 16 && size < array.length / 2) shrink();
		else move();
	}
	
	public void clear() {
		for (int i = 0; i < nextIndex; i++) array[i] = null;
	}
	
	private void move() {
		int p = 0;
		for (Object e : array) {
			if (e != null) {
				array[p++] = e;
			}
		}
		nextIndex = p;
		for (int i = p; i < array.length; i++) array[i] = null;
	}
	
	private void enlarge() {
		Object[] newArray = new Object[array.length * 2];
		int p = 0;
		for (Object e : array) {
			if (e != null) {
				newArray[p++] = e;
			}
		}
		nextIndex = p;
		for (int i = p; i < newArray.length; i++) newArray[i] = null;
		array = newArray;
	}
	
	private void shrink() {
		Object[] newArray = new Object[array.length / 2];
		int p = 0;
		for (Object e : array) {
			if (e != null) {
				newArray[p++] = e;
			}
		}
		nextIndex = p;
		for (int i = p; i < newArray.length; i++) newArray[i] = null;
		array = newArray;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (int i = 0; i < nextIndex; i++) {
			builder.append(array[i].toString());
			if (i < nextIndex - 1) builder.append(", ");
		}
		
		builder.append(']');
		return builder.toString();
	}
	
}
