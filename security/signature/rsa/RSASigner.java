package com.tpwang.security.signature.rsa;

/***
 * @author Tao Peter Wang
 */

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class RSASigner {
	
	private KeyPair keyPair;
	
	/***
	 * Constructor
	 */
	public RSASigner() {
		try {
			// initialise key
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(512);
			
			// generate key
			keyPair = generator.generateKeyPair();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Get public key
	 * @return public key in bytes
	 */
	public byte[] getPublicKeyEncoded() {
		return ((RSAPublicKey)keyPair.getPublic()).getEncoded();
	}
	
	/***
	 * Sign with RSA
	 * @param origMsg
	 * @return signed message
	 */
	public String encodeSignature(String origMsg) {
		String signedMsg = null;
		
		try {
			// generate private key
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = factory.generatePrivate(spec);
			
			// prepare signature
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(privateKey);
			
			// sign
			signature.update(origMsg.getBytes());
			byte[] result = signature.sign();
			signedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return signedMsg;
	}
	
}
