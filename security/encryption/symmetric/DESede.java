package com.tpwang.security.encryption.symmetric;

/***
 * @author Tao Peter Wang
 */

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DESede {
	
	private Key convertedKey;
	private Cipher cipher;
	
	/***
	 * Constructors
	 */
	public DESede() {
		this(168);
	}
	
	public DESede(int keySize) {
		if (keySize > 112)
			keySize = 168;
		else if (keySize != 112)
			keySize = 112;
			
		try {
			// generate
			KeyGenerator generator = KeyGenerator.getInstance("DESede");	// set algorithm
//			generator.init(keySize);	// set key size
			generator.init(new SecureRandom());		// default length
			
			SecretKey secretKey = generator.generateKey();
			byte[] byteKey = secretKey.getEncoded();	// byte encode
			
			// convert
			DESedeKeySpec spec = new DESedeKeySpec(byteKey);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			convertedKey = factory.generateSecret(spec);
			
			// instance for ciphering
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");		// encryption method
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * DESede Encode
	 * @param secretMsg
	 * @return encodedMsg
	 */
	public String DESedeEncode(String secretMsg) {
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
	 * DESede Decode
	 * @param encodedMsg
	 * @return decodedMsg
	 */
	public String DESedeDecode(String encodedMsg) {
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
}
