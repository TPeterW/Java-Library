package com.tpwang.security.signature.dsa;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DSAReader {
	
	private byte[] publicKeyEncoded;
	
	/***
	 * Constructor
	 * @param publicKeyEncoded
	 */
	public DSAReader(byte[] publicKeyEncoded) {
		this.publicKeyEncoded = publicKeyEncoded;
	}
	
	/***
	 * Verify signature with DSA
	 * @param origMsg original message
	 * @param signedMsg signed message
	 * @return whether signature is verified
	 */
	public boolean verifySignature(String origMsg, String signedMsg) {
		boolean verified = false;
		
		try {
			// generate public key
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyEncoded);
			KeyFactory factory = KeyFactory.getInstance("DSA");
			PublicKey publicKey = factory.generatePublic(spec);
			
			// prepare signature
			Signature signature = Signature.getInstance("SHA1withDSA");
			signature.initVerify(publicKey);
			
			// verify signature
			signature.update(origMsg.getBytes());
			verified = signature.verify(Base64.decodeBase64(signedMsg));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return verified;
	}
	
}
