package com.tpwang.security.encryption.asymmetric.elgamal;

/***
 * @author Tao Peter Wang
 */

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ElGamalReceiver {
	
	private KeyPair keyPair;
	
	/***
	 * Constructor
	 */
	public ElGamalReceiver() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			
			AlgorithmParameterGenerator paramGenerator = AlgorithmParameterGenerator.getInstance("Elgamal");
			paramGenerator.init(200);		// multiple of 8
			
			AlgorithmParameters params = paramGenerator.generateParameters();
			DHParameterSpec dhParamSpec = (DHParameterSpec)params.getParameterSpec(DHParameterSpec.class);
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("Elgamal");
			keyPairGenerator.initialize(dhParamSpec, new SecureRandom());
			keyPair = keyPairGenerator.generateKeyPair();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Get public key
	 * @return publicKeyEncoded
	 */
	public byte[] getPublicKeyEncoded() {
		return keyPair.getPublic().getEncoded();
	}
	
	/***
	 * ElGamal Decode
	 * @param encodedMsg
	 * @return decodedMsg
	 */
	public String ElGamalDecode(String encodedMsg) {
		String secretMsg = null;
		
		try {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
			KeyFactory factory = KeyFactory.getInstance("Elgamal");
			PrivateKey privateKey = factory.generatePrivate(spec); 
			
			Cipher cipher = Cipher.getInstance("Elgamal");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			byte[] result = cipher.doFinal(Base64.decodeBase64(encodedMsg));
			encodedMsg = new String(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretMsg;
	}
	
}
