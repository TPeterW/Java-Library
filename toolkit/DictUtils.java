package com.tpwang.toolkit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author Tao Peter Wang
 */

public class DictUtils {
	
	/**
	 * Easy convert to list
	 * (Method taken from 知乎: http://zhihu.com/question/53584423/answer/136602479)
	 * @param items array of input objects
	 * @return converted list
	 */
	public static List<Object> array(Object... items){
	    return Arrays.asList(items);
	}

	/**
	 * Easy convert to map
	 * (Method taken from 知乎: http://zhihu.com/question/53584423/answer/136602479)
	 * @param items array of input objects, arranged [key, value, key, value, ...]
	 * @return converted map
	 */
	public static Map<Object, Object> map(Object... items){
	    Map<Object, Object> result = new HashMap<Object, Object>();
	    for(int i = 0; i < items.length; i += 2){
	        result.put(items[i], items[i+1]);
	    }
	    return result;
	}
}
