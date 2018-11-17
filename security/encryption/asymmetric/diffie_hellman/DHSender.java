package com.tpwang.security.encryption.asymmetric.diffie_hellman;

/***
 * @author Tao Peter Wang
 */

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

public class DHSender {		// Diffie-Hellman
	
	private static final int KEY_SIZE = 512;
	private KeyPair senderKeyPair;
	private SecretKey senderDESKey;
	
	/***
	 * Constructor
	 */
	public DHSender() {
		try {
			// initialise sender generator
			KeyPairGenerator senderGenerator = KeyPairGenerator.getInstance("DH");
			senderGenerator.initialize(KEY_SIZE);
			
			// generate sender key
			senderKeyPair = senderGenerator.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Initialise DH sender
	 * @param receiverPublicKeyEncoded
	 */
	public void init(byte[] receiverPublicKeyEncoded) {
		try {
			// sender public key
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec spec = new X509EncodedKeySpec(receiverPublicKeyEncoded);
			PublicKey senderPublicKey =  senderKeyFactory.generatePublic(spec);
			
			// construct key
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			
			// sender's local key
			senderDESKey = senderKeyAgreement.generateSecret("DES");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Get sender's public key
	 * @return senderPublicKey
	 */
	public byte[] getPublicKeyEncoded() {
		// sender's public key, sent to receiver
		return senderKeyPair.getPublic().getEncoded();
	}
	
	/***
	 * DH Encode using JDK
	 * @param secretMsg
	 * @param privateKey
	 * @return encodedMsg
	 */
	public String DHEncode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDESKey);
			
			byte[] result = cipher.doFinal(secretMsg.getBytes());
			encodedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedMsg;
	}
}
