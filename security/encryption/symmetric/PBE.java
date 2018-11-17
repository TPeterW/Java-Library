package com.tpwang.security.encryption.symmetric;

/***
 * @author Tao Peter Wang
 */

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class PBE {
	
	private byte[] salt;
	private Key key;
	private Cipher cipher;
	private PBEParameterSpec paramSpec;
	
	private static String password = "Muajajajajaja~~~";
	
	public PBE() {
		this(128);
	}
	
	public PBE(int keySize) {
		
		try {
			// initialise salt
			SecureRandom random = new SecureRandom();
			salt = random.generateSeed(8);		// salt is 8 bytes
			
			// generate key
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray());	// based on password
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");// set algorithm
			key = factory.generateSecret(spec);
			
			// encode
			paramSpec = new PBEParameterSpec(salt, 100);	// salt, # of iterations
			cipher = Cipher.getInstance("PBEWITHMD5andDES");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String PBEEncode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			
			byte[] result = cipher.doFinal(secretMsg.getBytes());
			encodedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedMsg;
	}
	
	public String PBEDecode(String encodedMsg) {
		String secretMsg = null;
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			
			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
			secretMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretMsg;
	}
	
}
