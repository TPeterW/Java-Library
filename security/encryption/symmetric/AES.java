package com.tpwang.security.encryption.symmetric;

/***
 * @author Tao Peter Wang
 */

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
	
	private Key key;
	private Cipher cipher;
	
	/***
	 * Constructors
	 */
	public AES() {
		this(128);
	}
	
	public AES(int keySize) {
		if (keySize >= 256)
			keySize = 256;
		else if (keySize >= 192)
			keySize = 192;
		else
			keySize = 128;
		
		try {
			// generate
			KeyGenerator generator = KeyGenerator.getInstance("AES");	// set algorithm
//			generator.init(keySize);	// set key size
			generator.init(new SecureRandom());
			
			SecretKey secretKey = generator.generateKey();
			byte[] byteKey = secretKey.getEncoded();	// byte encode
			
			// convert key
			key = new SecretKeySpec(byteKey, "AES");
			
			// instance for ciphering
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");		// algorithm/method/padding
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * AES Encode using JDK
	 * @param secretMsg
	 * @return encodedMsg
	 */
	public String AESEncode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] result = cipher.doFinal(secretMsg.getBytes());	// input is byte[]
			encodedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedMsg;
	}
	
	/***
	 * AES Dncode using JDK
	 * @param encodedMsg
	 * @return decodedMsg
	 */
	public String AESDecode(String encodedMsg) {
		String secretMsg = null;
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
			secretMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretMsg;
	}
	
}
