package com.tpwang.maths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author Tao Peter Wang
 *
 * int reverseInt(int x)
 * 		reverse an integer
 * 		@param x
 * 		@return reversed integer
 * 
 * int numPerfectSquares(int n)
 * 		Find minimum number of perfect square numbers 
 * 		@param n
 * 		@return Boolean
 * 
 * boolean isPerfectSquare(int n)
 * 		Check if a number is a perfect square
 * 		@param n
 * 		@return Boolean
 * 
 * int romanToInt(String s)
 * 		Converts Roman numerals to integer values
 * 		@param s
 * 		@return int
 * 
 * int atoi(String str)
 * 		Converts String to integers, return 0 if out of boundary;
 * 		accepts white space and following non-digit characters
 * 		@param str
 * 		@return Integer
 * 		
 * 
 */

@SuppressWarnings("unused")
public class NumberOperator {
	/**
	 * converts Roman numerals to integer values
	 * @param s
	 * @return int
	 */
	public int romanToInt(String s) {
        HashMap<Character, Integer> table = new HashMap<Character, Integer>();
        table.put('M', 1000);
        table.put('D', 500);
        table.put('C', 100);
        table.put('L', 50);
        table.put('X', 10);
        table.put('V', 5);
        table.put('I', 1);
        table.put('E', 0);
        
        s += 'E';
        
        int result = 0;
        
        int temp = 0;
        
        for(int i = 0; i < s.length() - 1; i++){
            if(table.get(s.charAt(i)) > table.get(s.charAt(i + 1))){
                temp += table.get(s.charAt(i));
                result += temp;
                temp = 0;
            }
            else if(table.get(s.charAt(i)) < table.get(s.charAt(i + 1))){
                temp += table.get(s.charAt(i));
                result -= temp;
                temp = 0;
            }
            else{
                temp += table.get(s.charAt(i));
            }
        }
        
        return result;
    }
	
	/**
	 * reverse an integer
	 * @param x
	 * @return reversed integer
	 */
	public int reverseInt(int x){
		// clever way
		int reversed = 0;
        int original = x;
        
        while(original != 0)
        {
            reversed = reversed * 10 + original % 10;
            original /= 10;
        }
        
        return reversed;
		
		
        // dumb way
//		if(x == 0)
//			return 0;
//		
//		Boolean numPositive;
//		
//		if(x >= 0)
//			numPositive = true;
//		else
//			numPositive = false;
//		
//		x = Math.abs(x);
//		
//		String numEmpty = Integer.toString(x);
//		String numStr = "";
//		
//		for(int i = 1; i <= numEmpty.length(); i++){
//			numStr += numEmpty.charAt(numEmpty.length() - i);
//		}
//		
//		while(numStr.charAt(0) == '0'){
//			numStr = numStr.substring(1);
//		}
//		
//		if(canParseInt(numStr))
//			x = Integer.parseInt(numStr);
//		else
//			return 0;
//		
//		if(numPositive)
//			return x;
//		else
//			return -x;
	}
	
	// checks if a String can be parsed as Integer
	// damn Java, why don't you have tryParse?
	public boolean canParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
	/**
	 * Find minimum number of perfect square numbers 
	 * @param n
	 * @return Boolean
	 */
	public int numPerfectSquares(int n){
//		LinkedList<Integer> allPerfectNumbers = findAllPerfectSquares(n);
////		System.out.println(allPerfectNumbers.toString() + "   num is " + n);
//		
//		return getNumPerfectSquares(n, allPerfectNumbers, 0, 0);
		
		int[] dp = new int[n+1];
	    Arrays.fill(dp, Integer.MAX_VALUE);

	    dp[0] = 0;
	    dp[1] = 1;
	    for (int i = 2; i <= n; i++) {
	        for (int j = (int)Math.sqrt(i); j >= 1; j--) {
	            if (j * j == i) {
	                dp[i]  = 1;
	                break;
	            }
	            else
	                dp[i]  = Math.min(dp[i], dp[j*j] + dp[i-j*j]);
	        }
	    }
	    return dp[n];
	}
	
	/**
	 * Check if a number is a perfect square
	 * @param n
	 * @return Boolean
	 */
    public boolean isPerfectSquare(int n){
        int root = (int)Math.sqrt(n);
        if (root * root == n){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * own implementation of String to Integer, does slightly more things
     * @param str
     * @return
     */
    public int atoi(String str) {
        if(str.equals("") || str.equals(" "))
            return 0;
        
        boolean positive = true;
        str = clearWhiteSpace(str);
        
        if(str.charAt(0) == '-') {
            positive = false;
            str = str.substring(1);
        }
        else if(str.charAt(0) == '+') {
            positive = true;
            str = str.substring(1);
        }
        
        if(!Character.isDigit(str.charAt(0)))
            return 0;
        
        str = getRidOfAppendix(str);
        System.out.println(str);
        
        if(outOfRange(str, positive) == true) {
            return positive == true ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        
        int num = 0;
        
        for(int i = 0; i < str.length(); i++){
            switch(str.charAt(i)) {
                case '0':
                    num *= 10;
                    break;
                case '1':
                    num *= 10;
                    num += 1;
                    break;
                case '2':
                    num *= 10;
                    num += 2;
                    break;
                case '3':
                    num *= 10;
                    num += 3;
                    break;
                case '4':
                    num *= 10;
                    num += 4;
                    break;
                case '5':
                    num *= 10;
                    num += 5;
                    break;
                case '6':
                    num *= 10;
                    num += 6;
                    break;
                case '7':
                    num *= 10;
                    num += 7;
                    break;
                case '8':
                    num *= 10;
                    num += 8;
                    break;
                case '9':
                    num *= 10;
                    num += 9;
                    break;
            }
        }
        
        if(positive)
            return num;
        else
            return -num;
    }
    
    // checks if a String is out of range
    private boolean outOfRange(String str, boolean positive) {
        if(str.length() > 10){
            return true;   	
        }
            
        if(str.length() < 10){
            return false;
        }
        
        if(str.charAt(0) != '2') {
            return str.charAt(0) > '2' ? true : false;
        }
        else if(str.charAt(1) != '1') {
        	return str.charAt(1) > '1' ? true : false;
        }
        else if(str.charAt(2) != '4') {
        	return str.charAt(2) > '4' ? true : false;
        }
        else if(str.charAt(3) != '7') {
        	return str.charAt(3) > '7' ? true : false;
        }
        else if(str.charAt(4) != '4') {
        	return str.charAt(4) > '4' ? true : false;
        }
        else if(str.charAt(5) != '8') {
        	return str.charAt(5) > '8' ? true : false;
        }
        else if(str.charAt(6) != '3') {
        	return str.charAt(6) > '3' ? true : false;
        }
        else if(str.charAt(7) != '6') {
        	return str.charAt(7) > '6' ? true : false;
        }
        else if(str.charAt(8) != '4') {
        	return str.charAt(8) > '4' ? true : false;
        }
        else if(positive == true) {
            if (str.charAt(9) != '7')
            	return str.charAt(9) > '7' ? true : false;
        }
        else if(positive == false) {
            if (str.charAt(9) != '8')
            	return str.charAt(9) > '7' ? true : false;
        }
        
        return false;
    }
    
    // clear white spaces in the front
    private String clearWhiteSpace(String str) {
        while(str.charAt(0) == ' ') {
            str = str.substring(1);
        }
        return str;
    }
    
    // get rid of non-digit characters at the end
    private String getRidOfAppendix(String str) {
        for(int i = 0, endIndex = 0; i < str.length(); i++) {
            if(Character.isDigit(str.charAt(i))) {
                endIndex++;
            }
            else {
                str = str.substring(0, endIndex);
                break;
            }
        }
        return str;
    }
    
    // recursive function to get number of perfect squares
    private int getNumPerfectSquares(int n, LinkedList<Integer> list, int index, int current){
		if(list.contains(n)){
//			System.out.println("+" + n + " contain");
			return current + 1;
		}
		
		if(list.get(index) > n){
			return getNumPerfectSquares(n, list, index + 1, current);
		}
    	
    	int min = 4;
    	int original;
    	for(int i = index; i < list.size(); i++){
    		original = n;
			original -= list.get(i);
//			System.out.println("+" + list.get(i) + " subtract");
			int result = getNumPerfectSquares(original, list, i, current + 1);
			
			min = Math.min(min, result);
		}
    	
    	
		return min;
    }
	
	// find all perfect square numbers smaller than "n"
	private LinkedList<Integer> findAllPerfectSquares(int n){
		if(n == 0){
			return new LinkedList<Integer>();
		}
        LinkedList<Integer> allMatches = new LinkedList<Integer>();
        int root = 1;
        while (true) {
            allMatches.addFirst(root * root);
            root++;
            if(root * root > n){
            	return allMatches;
            }
        }
    }
}