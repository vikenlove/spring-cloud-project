package com.emerging.framework.core.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;



/**
 * RSA算法
 * 
 * @author llz
 *
 */
public class RSAUtils {

	// 日志
	private static Logger LOG = LoggerFactory.getLogger(RSAUtils.class);
	/**
	 * 算法
	 */
	private static final String algorithm = "RSA";

	/**
	 * 秘钥大小
	 */
	private static final int keysize = 1024;

	/**
	 * 最大加密字节
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * 最大解密字节
	 * 
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 生成公钥和私钥
	 * 
	 * @throws NoSuchAlgorithmException
	 *
	 */
	public static HashMap<String, Object> getKeys() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
		keyPairGen.initialize(keysize);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("public", publicKey);
		map.put("private", privateKey);
		return map;
	}

	/**
	 * 获取公钥的base64
	 * 
	 * @param hashMap
	 * @return
	 */
	public static String getPublicKeyEncoded(HashMap<String, Object> hashMap) {
		RSAPublicKey publicKey = (RSAPublicKey) hashMap.get("public");
		LOG.info("PublicKeyEncoded=" + Base64.getBase64(publicKey.getEncoded()));
		return Base64.getBase64(publicKey.getEncoded());
	}

	/**
	 * 获取私钥的base64
	 * 
	 * @param hashMap
	 * @return
	 */
	public static String getPrivateKeyEncoded(HashMap<String, Object> hashMap) {
		RSAPrivateKey privateKey = (RSAPrivateKey) hashMap.get("private");
		LOG.info("PrivateKeyEncoded=" + Base64.getBase64(privateKey.getEncoded()));
		return Base64.getBase64(privateKey.getEncoded());
	}

	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(byte[] modulus, byte[] exponent) throws Exception {
		BigInteger b1 = new BigInteger(modulus);
		BigInteger b2 = new BigInteger(exponent);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent) throws Exception {
		BigInteger b1 = new BigInteger(modulus);
		BigInteger b2 = new BigInteger(exponent);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(byte[] modulus, byte[] exponent) throws Exception {
		BigInteger b1 = new BigInteger(modulus);
		BigInteger b2 = new BigInteger(exponent);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) throws Exception {
		BigInteger b1 = new BigInteger(modulus);
		BigInteger b2 = new BigInteger(exponent);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 公钥加密
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 模长
		int key_len = publicKey.getModulus().bitLength() / 8;
		// 加密数据长度 <= 模长-11
		String[] datas = splitString(data, key_len - 11);
		String mi = "";
		// 如果明文长度大于模长-11则要分组加密
		for (String s : datas) {
			mi += bcd2Str(cipher.doFinal(s.getBytes()));
		}
		return mi;
	}

	/**
	 * 私钥解密
	 *
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		// 如果密文长度大于模长则要分组解密
		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		for (byte[] arr : arrays) {
			ming += new String(cipher.doFinal(arr));
		}
		return ming;
	}

	/**
	 * ASCII码转BCD码
	 *
	 */
	public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}

	public static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}

	/**
	 * BCD转字符串
	 */
	public static String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	/**
	 * 拆分字符串
	 */
	public static String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i = 0; i < x + z; i++) {
			if (i == x + z - 1 && y != 0) {
				str = string.substring(i * len, i * len + y);
			} else {
				str = string.substring(i * len, i * len + len);
			}
			strings[i] = str;
		}
		return strings;
	}

	/**
	 * 拆分数组
	 */
	public static byte[][] splitArray(byte[] data, int len) {
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] arrays = new byte[x + z][];
		byte[] arr;
		for (int i = 0; i < x + z; i++) {
			arr = new byte[len];
			if (i == x + z - 1 && y != 0) {
				System.arraycopy(data, i * len, arr, 0, y);
			} else {
				System.arraycopy(data, i * len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(byte[] datas, String privateKey) throws Exception {
		byte[] keyBytes = Base64.getFromBase64Byte(privateKey);
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		Key key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);

		int inputLen = datas.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(datas, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(datas, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData);
		// return new String(cipher.doFinal(datas));
	}

	/**
	 * 获取私钥对象
	 * 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey createRSAPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64.getFromBase64Byte(privateKey);
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(byte[] datas, String publicKey) throws Exception {
		byte[] keyBytes = Base64.getFromBase64Byte(publicKey);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		Key key = keyFactory.generatePublic(x509EncodedKeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);

		int inputLen = datas.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(datas, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(datas, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return Base64.getBase64(encryptedData);
		// return Base64.getBase64(cipher.doFinal(datas));
	}

	/**
	 * 
	 * 获取公钥对象
	 * 
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey createRSAPublicKey(String publicKey) throws Exception {
		byte[] keyBytes = Base64.getFromBase64Byte(publicKey);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
	}

	/**
	 * 转换为16进制
	 * 
	 * @param byteArray
	 * @return
	 */
	public static String ByteToHex(byte[] byteArray) {
		StringBuffer StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return StrBuff.toString();
	}

	/**
	 * 打印新的公钥私钥
	 * 
	 * @throws Exception
	 */
	public static void printPublicAndPrivateKey() throws Exception {
		// 生成公钥和私钥
		HashMap<String, Object> map = RSAUtils.getKeys();
		String publicKeyEncoded = RSAUtils.getPublicKeyEncoded(map);
		String privateKeyEncoded = RSAUtils.getPrivateKeyEncoded(map);
		System.out.println(publicKeyEncoded);
		System.out.println("----------------------");
		System.out.println(privateKeyEncoded);
		System.out.println("----------------------");
		String data = "我在测试测试我我我啊啊啊啊";
		data = encryptByPublicKey(data.getBytes(), publicKeyEncoded);
		data = decryptByPrivateKey(Base64.getFromBase64Byte(data), privateKeyEncoded);
		System.out.println(data);
	}

	/**
	 * 打印模和指数
	 * 
	 * @throws Exception
	 */
	public static void printModulusAndExponent() throws Exception {
		// 生成公钥和私钥
		HashMap<String, Object> map = RSAUtils.getKeys();
		// 获取
		RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
		RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");

		// 模
		String modulus = publicKey.getModulus().toString();
		System.out.println(Base64.getBase64(modulus));
		System.out.println("-------------------");
		// 公钥指数
		String public_exponent = publicKey.getPublicExponent().toString();
		System.out.println(Base64.getBase64(public_exponent));
		System.out.println("-------------------");

		// 私钥指数
		String private_exponent = privateKey.getPrivateExponent().toString();
		System.out.println(Base64.getBase64(private_exponent));
		System.out.println("-------------------");

		String base64modulus = Base64.getBase64(modulus);
		String base64public_exponent = Base64.getBase64(public_exponent);
		String base64private_exponent = Base64.getBase64(private_exponent);

		base64modulus = Base64.getFromBase64(base64modulus);
		base64public_exponent = Base64.getFromBase64(base64public_exponent);
		base64private_exponent = Base64.getFromBase64(base64private_exponent);

		RSAPublicKey pubKey = RSAUtils.getPublicKey(base64modulus, base64public_exponent);
		RSAPrivateKey priKey = RSAUtils.getPrivateKey(base64modulus, base64private_exponent);

		// 明文
		String ming = "中文中兴通测试";
		// 加密后的密文
		String mi = RSAUtils.encryptByPublicKey(ming, pubKey);
		System.out.println(mi);
		// 解密后的明文
		ming = RSAUtils.decryptByPrivateKey(mi, priKey);
		System.out.println(ming);
	}

	/**
	 * 打印模和指数
	 * 
	 * @throws Exception
	 */
	public static void printModulusAndExponentByByteArray() throws Exception {
		// 生成公钥和私钥
		HashMap<String, Object> map = RSAUtils.getKeys();
		// 获取
		RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
		RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");

		// 模
		byte[] modulus = publicKey.getModulus().toByteArray();
		System.out.println(Base64.getBase64(modulus));
		System.out.println("-------------------");
		// 公钥指数
		byte[] public_exponent = publicKey.getPublicExponent().toByteArray();
		System.out.println(Base64.getBase64(public_exponent));
		System.out.println("-------------------");

		// 私钥指数
		byte[] private_exponent = privateKey.getPrivateExponent().toByteArray();
		System.out.println(Base64.getBase64(private_exponent));
		System.out.println("-------------------");

		String base64modulus = Base64.getBase64(modulus);
		String base64public_exponent = Base64.getBase64(public_exponent);
		String base64private_exponent = Base64.getBase64(private_exponent);

		byte[] basemodulus = Base64.getFromBase64Byte(base64modulus);
		byte[] basepublic_exponent = Base64.getFromBase64Byte(base64public_exponent);
		byte[] baseprivate_exponent = Base64.getFromBase64Byte(base64private_exponent);

		// 使用模和指数生成公钥和私钥
		RSAPublicKey pubKey = RSAUtils.getPublicKey(basemodulus, basepublic_exponent);
		RSAPrivateKey priKey = RSAUtils.getPrivateKey(basemodulus, baseprivate_exponent);

		// 明文
		String ming = "在测试中兴通中文";
		// 加密后的密文
		String mi = RSAUtils.encryptByPublicKey(ming, pubKey);
		System.out.println(mi);
		// 解密后的明文
		ming = RSAUtils.decryptByPrivateKey(mi, priKey);
		System.out.println(ming);
	}

	/**
	 * 打印正在测试用的key
	 * 
	 * @throws Exception
	 */
	public static void printUseKey() throws Exception {
		String publicKeyEncoded = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCip9VTn6BoPBjA7bTrHTaUaW6vu9VuMKOaVKrSF8mYzUww0ZdkXjHmmpP8K40mka7A4nJkZyS8qIa6NhtQp2bmL8LpIREGy1ACgLqw/7EvcD8qgzjQoQm+3C0eevnN7PWz9aGJvl3XmxKJFXRAKrwLnexdNulnFUAjsgbSso4D2wIDAQAB";
		String privateKeyEncoded = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKKn1VOfoGg8GMDttOsdNpRpbq+71W4wo5pUqtIXyZjNTDDRl2ReMeaak/wrjSaRrsDicmRnJLyohro2G1CnZuYvwukhEQbLUAKAurD/sS9wPyqDONChCb7cLR56+c3s9bP1oYm+XdebEokVdEAqvAud7F026WcVQCOyBtKyjgPbAgMBAAECgYAwopZ65qQEBtQv++O42YdUYSIjkbqDtC93GhHpuAsjkV9SyZjG/hNVrKrLIAqYmb7zFcK2mtC1SH7gdwU73JI7j3kkqSQwsE3+Hx7Zeovfgnx8k4dXbJ9WyjM61rEfk1xPuG9fuFQ74fa9RQkZctG7jLi85aXq1ZKN15kRrtQSQQJBANuBfPI2eM+oVW70F/KgCUtN8Uf2U9nX51iIWsgTNiTLSgXclkLfgOyAcxgCrI6nR1Ut6zfM5eArF60B6/Ib6lMCQQC9srX9g2uyaPAL6RQlnAR1/Z7Q67Rg5PtKCBa1M1OiYuoBVstaoBfKLflovuHju/QeQaNLa4JCVkUAl3YkcJ9ZAkEA1mh6MmEwyq3DnZhCGBVoXq0ohEFBEGWLLXgrLIlPdss3Z9ha0cFdPiYNxiM+iC0vSS2MN7olxDxzaOOao6fOzwJAXAUH8S1CHe53m196LXBMXawk0TW1b1dEaroXwdhfFqOi3flrdc+5GpNIrD4EN8Oh/NdcTiadrm5I1KukXLLhKQJASBg1cPDm3ManSQmOd5L1Phr11VH87sqJZqzBGzAALkaRe6mGCxeGg9l/yK1jKKiFI3rK3t9exeZzZ2Uk7xPdEg==";
		// 获取公钥对象
		RSAPublicKey publicKey = createRSAPublicKey(publicKeyEncoded);
		RSAPrivateKey privateKey = createRSAPrivateKey(privateKeyEncoded);
		// 模
		byte[] modulus = publicKey.getModulus().toByteArray();
		System.out.println(ByteToHex(modulus));
		System.out.println(Base64.getBase64(modulus));
		System.out.println("-------------------");
		// 公钥指数
		byte[] public_exponent = publicKey.getPublicExponent().toByteArray();
		System.out.println(ByteToHex(public_exponent));
		System.out.println(Base64.getBase64(public_exponent));
		System.out.println("-------------------");
		// 公钥指数
		byte[] private_exponent = privateKey.getPrivateExponent().toByteArray();
		System.out.println(ByteToHex(private_exponent));
		System.out.println(Base64.getBase64(private_exponent));
		System.out.println("-------------------");

		String data = "123";// TokenUtil.CreateToken();// "123";
		System.out.println(data);
		data = encryptByPublicKey(data.getBytes(), publicKeyEncoded);
		System.out.println(data);
		data = "JEZpdyZHpwXcG9PePlJGZfrbIW9KyckVHrr5GdNyzytPOQzaxRfKE9XsQOicCqy4VGEdvu/q0My2HeP7furk96pSBiCOjjF+ZKY3wvOVmq7wS129CFn+Shxtns4vs2CtnLHGwJZvd3y09kOExhRwInSfUzaihUa4mEKyO12tD88=";
		data = "CvH/dn1zeyPcPLjVmA0Tcvhc34s1z5deVGOLu+bsxS85iCDWwaE2311TUrLZ6ELeeExKEtgmHhU/TF+xJCEEOoWIhT7i5RhRzyScVFD772MZHs4DQ7WVWm8fueUL/+seO1+eVKILDWEYNxJ3j9cYXNstRHUOcY4taQZIpM5S0ek=";
		System.out.println(data);
		System.out.println(decryptByPrivateKey(Base64.getFromBase64Byte(data), privateKeyEncoded));
	}

	/**
	 * 测试使用
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 打印新的公钥私钥
		// printPublicAndPrivateKey();

		// 打印新的模和指数
		// printModulusAndExponent();

		// 打印新的模和指数按字节数组
		// printModulusAndExponentByByteArray();

		// 打印正在测试的相关key信息
		printUseKey();
	}

}
