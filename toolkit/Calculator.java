package com.tpwang.toolkit;

import java.util.LinkedList;

public class Calculator {
	
	public static final String BACK = "back";
	
	private String display;		// what's currently displayed on the calculator screen
	private String previous;	// the previous result, used for next calculator
	
	private Character pendingOperation;
	
	private LinkedList<String> history;
	
	/***
	 * Constructor
	 */
	public Calculator() {
		display = "0";
		previous = "0";
		pendingOperation = null;
		
		history = new LinkedList<String>();
	}
	
	/***
	 * When a sign is pressed
	 * @param operator the operator pressed
	 * @return what is currently displayed on the screen
	 */
	public String operate(char operator) {
		
		if ("+-*/".contains(display)) {
			if (previous.equals(null))
				previous = "0";
				
			display = Character.toString(operator);
			pendingOperation = operator;		// for next time
			
			return display;
		}
		
		if (pendingOperation != null) {		// has pending operation
			double a = Double.parseDouble(previous);
			double b = Double.parseDouble(display);
			
			switch (pendingOperation) {
				case '+':
					previous = Double.toString(a + b); 
					break;
				case '-':
					previous = Double.toString(a - b);
					break;
				case '*':
					previous = Double.toString(a * b);
					break;
				case '/':
					previous = Double.toString(a / b);
					break;
			}
			
			history.add(previous.intern());
		}
		
		switch (operator) {
			case '+':
				previous = display.intern();
				display = "+";
				break;
			case '-':
				previous = display.intern();
				display = "-";
				break;
			case '*':
				previous = display.intern();
				display = "*";
				break;
			case '/':
				previous = display.intern();
				display = "/";
				break;
			default:
				display = null;
		}
		
		pendingOperation = operator;
		
		// TODO: add to history
		
		return display;
	}
	
	/***
	 * When a number is pressed
	 * @param number
	 * @return what is currently displayed on the screen
	 */
	public String number(String number) {
		
		if (display.toString().equals("0")) {		// current number is 0
			if (number.equals("."))
				display += number;
			else if (number.equals(BACK))
				display = "0";
			else
				display = number;
		} else if (number.equals(BACK)) {
			display = display.substring(0, display.length() - 1);
			if (display.length() < 1)
				display = "0";
		} else if ("+-*/".contains(display)) {
			display = number;
		} else {
			if (number.equals(".")) {		// input is "."
				if (display.contains("."))
					return display;
				else
					display += number.intern();
			} else							// input is normal number
				display += number.intern();
		}
		
		return display;
	}
	
	public String equalsTo() {
		if (pendingOperation == null)		// no pending operation
			return display;
		
		double a = Double.parseDouble(previous);
		double b = Double.parseDouble(display);
		switch (pendingOperation) {
			case '+':
				display = Double.toString(a + b);
				break;
			case '-':
				display = Double.toString(a - b);
				break;
			case '*':
				display = Double.toString(a * b);
				break;
			case '/':
				display = Double.toString(a / b);
				break;
		}
		
		return display;
	}
	
	/***
	 * Look up history stack and return previous results
	 * @param numLevel how many levels to trace back up
	 * @return the previous result
	 */
	public String getPrevious(int numLevel) {
		String prev = display.intern();
		int index = history.size(); 
		
		for (int i = 0; i < numLevel; i++) {
			index--;
			if (index >= 0)
				prev = history.get(index).intern();
			else
				return null;
		}
		
		return prev;
	}
	
	/***
	 * Empty history results
	 */
	public void clearHistory() {
		history.clear();
	}
	
	/***
	 * When CE is pressed
	 * @return what is currently displayed on the screen
	 */
	public String reset() {
		clearHistory();
		previous = "0";
		display = "0";
		pendingOperation = null;
		
		return display;
	}
}
