package com.emerging.framework.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
/**
 * 获取签名
 * @ClassName: SignUtil 
 * @Description: 
 * @author wxy 
 * @date 2018年5月9日 下午3:53:20 
 *
 */
public class SignUtil {
	/**
	 * 把对象的变量升序排列后按照http请求格式进行拼接
	 * @param obj
	 * @param includeVariables
	 * @param exincludeVariables
	 * @return
	 */
	public static String httpParamsAsc(Object obj,Map<String, String> exincludeVariables) {
		try {
			List<String> keyValues = new ArrayList<String>();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				if (exincludeVariables.containsKey(fieldName)) {
					continue;
				}

				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				String getter = "get" + firstLetter + fieldName.substring(1);

				Method method = obj.getClass().getMethod(getter, new Class[] {});
				Object value = method.invoke(obj, new Object[] {});
				if (value != null && StringUtils.isNotEmpty(value.toString())) {
					keyValues.add(fieldName + "=" + value.toString());
				}
			}
			Collections.sort(keyValues);
			StringBuffer params = new StringBuffer();
			for (String keyValue : keyValues) {
				params.append("&" + keyValue);
			}
			String result = params.toString();
			if (result.startsWith("&")) {
				result = result.substring(1);
			}
			return result;
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
}
