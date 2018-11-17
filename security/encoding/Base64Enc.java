package com.tpwang.security.encoding;

import org.apache.commons.codec.binary.Base64;

public class Base64Enc {
	
	/***
	 * Base64 Encode using JDK
	 * @param encodedMsg
	 * @return decoded message
	 */ 
	public String Base64encode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			encodedMsg = Base64.encodeBase64String(secretMsg.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedMsg;
	}
	
	/***
	 * Base64 Decode using JDK
	 * @param encodedMsg
	 * @return decoded message
	 */ 
	public String Base64decode(String encodedMsg) {
		String decodedMsg = null;
		
		try {
			byte[] result = Base64.decodeBase64(encodedMsg);
			decodedMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return decodedMsg;
	}
	
}
