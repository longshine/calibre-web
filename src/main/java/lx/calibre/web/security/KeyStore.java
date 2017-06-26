package lx.calibre.web.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

public class KeyStore {

	private final Provider provider;
	private final String algorithm;
	private final KeyPair keyPair;

	public KeyStore(Provider provider, String algorithm, int keySize) throws NoSuchAlgorithmException {
		this.provider = provider;
		this.algorithm = algorithm;
		this.keyPair = generateKeyPair(provider, algorithm, keySize);
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public byte[] encrypt(byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(algorithm, provider);
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
		return cipher.doFinal(data);
	}

	public byte[] decrypt(byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(algorithm, provider);
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		return cipher.doFinal(data);
	}

	public String decrypt(String hex) throws GeneralSecurityException, UnsupportedEncodingException {
		byte[] data = Hex.decode(hex);
		data = decrypt(data);
		return URLDecoder.decode(StringUtils.reverse(new String(data)), "utf-8");
	}

	public static KeyPair generateKeyPair(Provider provider, String algorithm, int keySize)
			throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm, provider);
		keyPairGen.initialize(keySize, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

}
