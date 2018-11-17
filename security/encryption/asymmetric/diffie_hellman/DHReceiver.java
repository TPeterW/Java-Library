package com.tpwang.security.encryption.asymmetric.diffie_hellman;

/***
 * @author Tao Peter Wang
 */

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DHReceiver {	// Diffie-Hellman
	
	private KeyPair receiverKeyPair;
	private SecretKey receiverDESKey;
	
	/***
	 * Constructor
	 * @param senderPublicKeyEncoded
	 */
	public DHReceiver(byte[] senderPublicKeyEncoded) {
		try {
			// initialise receiver key
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec spec = new X509EncodedKeySpec(senderPublicKeyEncoded);
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(spec);		// public key
			// generate receiver key based on sender public key
			DHParameterSpec paramSpec = ((DHPublicKey)receiverPublicKey).getParams();
			
			// initialise receiver generator
			KeyPairGenerator receiverGenerator = KeyPairGenerator.getInstance("DH");
			receiverGenerator.initialize(paramSpec);
			
			// generate receiver key
			receiverKeyPair = receiverGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			
			// construct key
			KeyAgreement receiverAgreement = KeyAgreement.getInstance("DH");
			receiverAgreement.init(receiverPrivateKey);
			receiverAgreement.doPhase(receiverPublicKey, true);
			
			// create local key based on sender's key
			receiverDESKey = receiverAgreement.generateSecret("DES");			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/***
	 * Get receiver's public key
	 * @return receiverPublicKey
	 */
	public byte[] getPublicKeyEncoded() {
		// receiver's public key, sent to sender
		return receiverKeyPair.getPublic().getEncoded();
	}
	
	/***
	 * DH Decode using JDK
	 * @param encodedMsg
	 * @return decodedMsg
	 */
	public String DHDecode(String encodedMsg) {
		String secretMsg = null;
		
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, receiverDESKey);
			
			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
			secretMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretMsg;
	}
}
