package com.tpwang.security.signature.dsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/***
 * @author Tao Peter Wang
 */

public class DSASigner {
	
	private KeyPair keyPair;
	
	/***
	 * Constructor
	 */
	public DSASigner() {
		try {
			// initialise key
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
			keyPairGenerator.initialize(512);
			
			// generate key
			keyPair = keyPairGenerator.generateKeyPair();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Get public key
	 * @return public key in bytes
	 */
	public byte[] getPublicKeyEncoded() {
		return ((DSAPublicKey)keyPair.getPublic()).getEncoded();
	}
	
	/***
	 * Sign with DSA
	 * @param origMsg
	 * @return singed message
	 */
	public String encodeSignature(String origMsg) {
		String signedMsg = null;
		
		try {
			// generate private key
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
			KeyFactory factory = KeyFactory.getInstance("DSA");
			PrivateKey privateKey = factory.generatePrivate(spec);
			
			// prepare signature
			Signature signature = Signature.getInstance("SHA1withDSA");
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
