package com.tpwang.security.encryption.asymmetric.elgamal;

/***
 * @author Tao Peter Wang
 */

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class ElGamalSender {
	
	private byte[] publicKeyEncoded;
	
	public ElGamalSender(byte[] publicKeyEncoded) {
		this.publicKeyEncoded = publicKeyEncoded;
	}
	
	public String ElGamalEncode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyEncoded);
			KeyFactory factory = KeyFactory.getInstance("Elgamal");
			PublicKey publicKey = factory.generatePublic(spec);
			
			Cipher cipher = Cipher.getInstance("Elgamal");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			byte[] result = cipher.doFinal(secretMsg.getBytes());
			encodedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedMsg;
	}
	
}
