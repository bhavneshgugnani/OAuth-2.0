package org.bhavnesh.google.oauth.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESSecurityProvider {
	private SecretKey secretKey = null;
	private Cipher cipher = null;

	public AESSecurityProvider() throws NoSuchAlgorithmException, NoSuchPaddingException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		this.secretKey = keyGenerator.generateKey();
		initCipher();
	}

	public AESSecurityProvider(String encodedKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		initCipher();
	}

	private void initCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.cipher = Cipher.getInstance("AES");
	}

	public String encrypt(String plainText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] plainTextByte = plainText.getBytes();
		this.cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = this.cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	public String decrypt(String encryptedText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
		byte[] decryptedByte = this.cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	public String getEncodedKey() {
		// Base 64 encoded version of key, can be used to generate secretkey
		// instance again later
		// Available as constructor argument
		return Base64.getEncoder().encodeToString(this.secretKey.getEncoded());
	}
}
