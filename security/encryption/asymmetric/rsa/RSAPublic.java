package com.tpwang.security.encryption.asymmetric.rsa;

/***
 * @author Tao Peter Wang
 */

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAPublic {
	
	private byte[] publicKeyEncoded;
	
	public RSAPublic() {
		
	}
	
	public void init(byte[] publicKeyEncoded) {
		this.publicKeyEncoded = publicKeyEncoded;
	}
	
	/***
	 * RSA Encode with public key using JDK
	 * @param secretMsg
	 * @return encodedMsg
	 */
	public String RSAEncode(String secretMsg) {
		String encodedMsg = null;
		
		try {
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyEncoded);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = factory.generatePublic(spec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			byte[] result = cipher.doFinal(secretMsg.getBytes());
			encodedMsg = Base64.encodeBase64String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedMsg;
	}
	
	/***
	 * RSA Decode with public key using JDK
	 * @param encodedMsg
	 * @return decodedMsg
	 */
	public String RSADecode(String encodedMsg) {
		String secretMsg = null;
		
		try {
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyEncoded);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = factory.generatePublic(spec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			
			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
			secretMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretMsg;
	}
	
}
