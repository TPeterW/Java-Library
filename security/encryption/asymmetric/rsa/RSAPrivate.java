package com.tpwang.security.encryption.asymmetric.rsa;

/***
 * @author Tao Peter Wang
 */

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAPrivate {
	
	private KeyPair keyPair;
	
	/***
	 * Constructors
	 */
	public RSAPrivate() {
		this(512);
	}
	
	public RSAPrivate(int keySize) {
		if (keySize < 0 || keySize % 64 != 0)
			keySize = 512;
		
		try {
			// initialise generator
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(keySize);
			
			keyPair = generator.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Get public key
	 * @return publicKey
	 */
	public byte[] getPublicKeyEncoded() {
		return keyPair.getPublic().getEncoded();
	}
	
	/***
	 * RSA Decode with private key using JDK
	 * @param encodedMsg
	 * @return decodedMsg
	 */
	public String RSADecode(String encodedMsg) {
		String secretMsg = null;
		
		try {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = factory.generatePrivate(spec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
			secretMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretMsg;
	}
	
	/***
	 * RSA Encode with private key using JDK
	 * @param secretMsg
	 * @return encodedMsg
	 */
	public String RSAEncode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = factory.generatePrivate(spec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			
			byte[] result = cipher.doFinal(secretMsg.getBytes());
			encodedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return encodedMsg;
	}
	
	
}
