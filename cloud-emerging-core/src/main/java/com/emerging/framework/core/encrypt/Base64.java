package com.emerging.framework.core.encrypt;



import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64 加密解密工具类
 * 
 * @author wt
 *
 */
public class Base64 {

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}

		return s;
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getBase64(byte[] str) {
		String s = null;
		if (str != null) {
			s = new BASE64Encoder().encode(str);
		}

		return s;
	}

	/**
	 * 解密
	 * 
	 * @param s
	 * @return
	 */
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 解密
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] getFromBase64Byte(String s) {
		byte[] b = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	public static void main(String[] args) {
		String encodedString = Base64.getBase64("123456");
		System.out.println(encodedString);

		String b = getFromBase64(encodedString);
		System.out.println(b);

	}

}
