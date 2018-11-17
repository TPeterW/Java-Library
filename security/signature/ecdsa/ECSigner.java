package com.tpwang.security.signature.ecdsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class ECSigner {
	
	private KeyPair keyPair;
	
	/***
	 * Constructor
	 */
	public ECSigner() {
		try {
			// initialise key
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(256);
			
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
		return ((ECPublicKey)keyPair.getPublic()).getEncoded();
	}
	
	/***
	 * Sign with ECDSA
	 * @param origMsg
	 * @return signed message
	 */
	public String encodeSignature(String origMsg) {
		String signedMsg = null;
		
		try {
			// generate private key
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
			KeyFactory factory = KeyFactory.getInstance("EC");
			PrivateKey privateKey = factory.generatePrivate(spec);
			
			// prepare signature
			Signature signature = Signature.getInstance("SHA1withECDSA");
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
