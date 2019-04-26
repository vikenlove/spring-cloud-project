package com.emerging.framework.core.utils;

import com.emerging.framework.core.encrypt.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * aes加解密
 * 
 * @author llz
 *
 */
public class PTAESUtils {

	public static String encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return Base64.getBase64(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(Base64.getFromBase64Byte(content));
			return new String(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 算法名称
	public static final String KEY_ALGORITHM = "AES";
	// 加解密算法/模式/填充方式
	public static final String algorithmStr = "AES/CBC/PKCS7Padding";
	//
	static {
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
	}

	public static byte[] init(byte[] keyBytes) {
		// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
		int base = 16;
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}

		return keyBytes;
	}

	/**
	 * 加密方法
	 *
	 * @param content
	 *            要加密的字符串
	 * @param keyBytes
	 *            加密密钥
	 * @param iv
	 * 
	 * @return
	 */
	public static byte[] encrypt(byte[] content, byte[] keyBytes, byte[] iv) {
		keyBytes = init(keyBytes);
		// 转化成JAVA的密钥格式
		byte[] encryptedText = null;
		try {
			Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
			// 初始化cipher
			Cipher cipher = Cipher.getInstance(algorithmStr, "BC");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			encryptedText = cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	/**
	 * 解密方法
	 *
	 * @param encryptedData
	 *            要解密的字符串
	 * @param keyBytes
	 *            解密密钥
	 * @param iv
	 * 
	 * @return
	 */
	public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, byte[] iv) {
		keyBytes = init(keyBytes);
		byte[] encryptedText = null;
		try {
			Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
			// 初始化cipher
			Cipher cipher = Cipher.getInstance(algorithmStr, "BC");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			encryptedText = cipher.doFinal(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	/**
	 * 解密
	 * 
	 * @param cont
	 * @param password
	 * @param salt
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cont, String password, String salt, String iv) throws Exception {
		String key = PBKDF2Utils.getEncryptedPassword(password, salt);
		byte[] conts = Base64.getFromBase64Byte(cont);
		conts = decrypt(conts, PBKDF2Utils.fromHex(key), PBKDF2Utils.fromHex(iv));
		return new String(conts,"utf-8");
	}

	public static void main(String[] args) throws Exception {
		String pass = "d6ba777768a625e3f180b6e920ba3208";
		String content = "北京中兴通软件科技";
		String enresult = encrypt(content, pass);
		System.out.println(enresult);
		String result = decrypt(enresult, pass);
		System.out.println(result);

		// String key = "12345678";
		// String iv = "0102030405060708";
		// String str="中兴通测试";
		// byte[] strs=encrypt(str.getBytes(),key.getBytes(), iv.getBytes());
		// System.out.println(Base64.getBase64(strs));
		// strs=decrypt(strs, key.getBytes(), iv.getBytes());
		// System.out.println(new String(strs));

		String salt = "5774303f9a36355879e94cbba6e05503";
		String password = "d6ba777768a625e3f180b6e920ba3208";

		String key = PBKDF2Utils.getEncryptedPassword(password, salt);// "9cb310b4cadef707fbcde199a1b983e2";
		String iv = "9eeb38b3be8392bb2bf0df1f25aebd3b";
		String cont = "mWsLXsd5qLcz/RjH6T2ikBqrGuANzbauWL0HeVYtVLqtWZubhHPZF1mNLIW5XocC";

		byte[] conts = Base64.getFromBase64Byte(cont);
		conts = decrypt(conts, PBKDF2Utils.fromHex(key), PBKDF2Utils.fromHex(iv));
		System.out.println(new String(conts));
	}
}
