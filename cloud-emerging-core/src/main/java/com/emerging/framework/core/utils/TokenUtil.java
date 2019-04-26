package com.emerging.framework.core.utils;

import com.emerging.framework.core.encrypt.Base64;

import java.util.UUID;

/**
 * token生成
 * 
 * @author llz
 *
 */
public class TokenUtil {

	/**
	 * 创建token
	 * 
	 * @return
	 */
	public static String CreateToken() {
		String token = UUID.randomUUID().toString();
		return Base64.getBase64(token);
	}

	/**
	 * 
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String CreateUUID() {
		return UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		System.out.println(CreateToken());
		System.out.println(CreateToken());
		System.out.println(CreateToken());
		System.out.println(CreateToken());
		System.out.println(CreateToken());
	}
}
