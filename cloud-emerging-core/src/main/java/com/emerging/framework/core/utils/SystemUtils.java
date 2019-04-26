package com.emerging.framework.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 工具类
 * 
 * @author llz
 *
 */
public class SystemUtils extends Utils{

	 
	  /**
		 * 获取32位UUID
		 * 
		 * @return
		 */
		public static String getUUID() {
			return uuid().toUpperCase();
		}

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            加密字符串
	 * @return
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		StringBuffer hexValue = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			char[] charArray = str.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++) {
				byteArray[i] = (byte) charArray[i];
			}
			byte[] md5Bytes = md5.digest(byteArray);

			for (int j = 0; j < md5Bytes.length; j++) {
				int val = ((int) md5Bytes[j]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexValue.toString();
	}

	/*
	 * MD5 32位小写加密算法
	 */
	public static String encryption(String plain) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			System.out.println(SystemUtils.getUUID().toUpperCase());
		}
		System.out.println(SystemUtils.MD5(SystemUtils.MD5("zxtcsywy")).toUpperCase());
	}
}
