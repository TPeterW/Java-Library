package com.tpwang.security.encryption.symmetric;

/***
 * @author Tao Peter Wang
 */

import java.security.Key;
//import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DES {
	
	private Key convertedKey;
	private Cipher cipher;
	
	/***
	 * Constructors
	 */
	public DES() {
		this(56);
	}
	
	private DES(int keySize) {
		try {
			// generate
			KeyGenerator generator = KeyGenerator.getInstance("DES");	// set algorithm
			generator.init(keySize);	// set key size
			
			SecretKey secretKey = generator.generateKey();
			byte[] byteKey = secretKey.getEncoded();	// byte encode
			
			// convert
			DESKeySpec spec = new DESKeySpec(byteKey);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
			convertedKey = factory.generateSecret(spec);
			
			// instance for ciphering
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");		// encryption method
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * DES Encode using JDK
	 * @param secretMsg
	 * @return encoded message
	 */
	public String DESEncode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			// encode
			cipher.init(Cipher.ENCRYPT_MODE, convertedKey);
			
			byte[] result = cipher.doFinal(secretMsg.getBytes());		// input is byte[]
			encodedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedMsg;
	}
	
	/***
	 * DES Decode using JDK
	 * @param encodedMsg
	 * @return decoded message
	 */ 
	public String DESDecode(String encodedMsg) {
		String secretMsg = null;
		
		try {
			// decode
			cipher.init(Cipher.DECRYPT_MODE, convertedKey);
			
			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
			secretMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretMsg;
	}
	
	
	
	
	/* Below is for Bouncy Castle */
	
//	/***
//	 * DES Decode using Bouncy Castle
//	 * @param secretMsg
//	 * @return encodedMsg
//	 */
//	public String bcDESEncode(String secretMsg) {
//		String encodedMsg = null;
//		
//		Security.addProvider(new BouncyCastleProvider());
//		
//	 	// needs to change the way generator is called 
//		// KeyGenerator.getInstance("DES", "BC");
//		
//		try {
//			cipher.init(Cipher.ENCRYPT_MODE, convertedKey);
//			
//			byte[] result = cipher.doFinal(secretMsg.getBytes());
//			encodedMsg = Base64.encodeBase64String(result);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return encodedMsg;
//	}
//	
//	/***
//	 * DES Encode using Bouncy Castle
//	 * @param encodedMsg
//	 * @return decodedMsg
//	 */
//	public String bcDESDecode(String encodedMsg) {
//		String decodedMsg = null;
//		
//		try {
//			cipher.init(Cipher.DECRYPT_MODE, convertedKey);
//			
//			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
//			decodedMsg = new String(result);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		return decodedMsg;
//	}
//	
}
