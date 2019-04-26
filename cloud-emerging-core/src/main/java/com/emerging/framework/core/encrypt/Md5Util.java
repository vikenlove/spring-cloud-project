package com.emerging.framework.core.encrypt;

import java.security.MessageDigest;

/**
 * MD5工具
 * 
 * @author llz
 *
 */
public class Md5Util {

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		try {
			MessageDigest md5 = null;
			StringBuffer hexValue = new StringBuffer();
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
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将md5转成16进制字符串(小写,长度32位)
	 * 
	 * @param message
	 * @return
	 */
	public static String getMD5(String message) {
		String md5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); // 创建一个md5算法对象
			byte[] messageByte = message.getBytes("UTF-8");
			byte[] md5Byte = md.digest(messageByte); // 获得MD5字节数组,16*8=128位
			md5 = bytesToHex(md5Byte); // 转换为16进制字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
	}

	/**
	 * 二进制转十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer hexStr = new StringBuffer();
		int num;
		for (int i = 0; i < bytes.length; i++) {
			num = bytes[i];
			if (num < 0) {
				num += 256;
			}
			if (num < 16) {
				hexStr.append("0");
			}
			hexStr.append(Integer.toHexString(num));
		}
		return hexStr.toString().toUpperCase();
	}

	public static void main(String[] args) {
		String params = "appID=1101&appKey=101&area=123&authendtime=2019-04-06&authid=4326&authsigh=1234&authstarttime=2018-04-07&authtime=200&authtype=1&nsrsbh=12314123&orgid=231&orgname=测试企业&softid=ZXT_JXManager&swjgdm=123&ts=151947288003d338f047a547308ba5b483e8f49767";
		params = "appID=20001&appKey=100&appcorpid=92110102MA0172LH47&ts=123456783df96c5abe97f2967d38010870030a5f";
		System.out.println(MD5(params));
		System.out.println(getMD5(params));
	}
}