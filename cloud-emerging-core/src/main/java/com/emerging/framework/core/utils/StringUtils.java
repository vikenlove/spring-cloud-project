package com.emerging.framework.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串工具类
 * @author lincm
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String str;
	public static final String EMPTY_STRING = "";

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/**
	 * 字符串转整数
	 * @param strValue 字符串值
	 * @return 返回转换后的整数值
	 */
	public static Integer string2Integer(String strValue) {
		if (isBlank(strValue)) {
			return null;
		}
		
		Integer intValue = null;
		try {
			intValue = Integer.valueOf(strValue);
		} catch (NumberFormatException e) {
			intValue = null;
		}
		return intValue;
	}
	
	/**
	 * 字符串转长整数
	 * @param strValue 字符串值
	 * @return 返回转换后的长整数值
	 */
	public static Long string2Long(String strValue) {
		if (isBlank(strValue)) {
			return null;
		}
		
		Long longValue = null;
		try {
			longValue = Long.valueOf(strValue);
		} catch (NumberFormatException e) {
			longValue = null;
		}
		return longValue;
	}
	
	/**
	 * 字符串转浮点数
	 * @param strValue 字符串值
	 * @return 返回转换后的浮点数值
	 */
	public static Double string2Double(String strValue) {
		if (isBlank(strValue)) {
			return null;
		}
		
		Double doubleValue = null;
		try {
			doubleValue = Double.valueOf(strValue);
		} catch (NumberFormatException e) {
			doubleValue = null;
		}
		return doubleValue;
	}
	
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	/**
	 * 判断字符串是否存在某个数组中
	 * @param array
	 * @param str
	 * @return
	 */
	public static boolean contain(String[] array,String str){
		  for(int i=0;i<array.length;i++){
		        if(array[i].equals(str)){
		           return true;  
		        }
		  }
		  return false;

		}
	
	/**
	 * 数组转字符串拼接
	 * @Title: StringJoin
	 * @param data
	 * @param charStr
	 * @return
	 * @return String
	 */
	public static String StringJoin(String[] data,String charStr){
		StringBuffer sb = new StringBuffer();
		for(String str:data){
			sb.append(str).append(charStr);
		}
		return StringUtils.substringBeforeLast(sb.toString(),charStr);
	}
	
	/**
	 * 数组转换
	 * @Title: randomStr
	 * @param str
	 * @return
	 * @return String[]
	 */
	public static String randomStr(String str){
		String[] arrays = str.split("#");
		List<String> list = Arrays.asList(arrays);
		Collections.shuffle(list);
		
		String[] listArray =(String[]) list.toArray();
		
		
		return StringJoin(listArray, "#");
	}
	
	 public static String getIpAddr(HttpServletRequest request){  
	        String ipAddress = request.getHeader("x-forwarded-for");  
	            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	                ipAddress = request.getHeader("Proxy-Client-IP");  
	            }  
	            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
	            }  
	            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	                ipAddress = request.getRemoteAddr();  
	                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
	                    //根据网卡取本机配置的IP  
	                    InetAddress inet=null;  
	                    try {  
	                        inet = InetAddress.getLocalHost();  
	                    } catch (UnknownHostException e) {  
	                        e.printStackTrace();  
	                    }  
	                    ipAddress= inet.getHostAddress();  
	                }  
	            }  
	            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
	            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
	                if(ipAddress.indexOf(",")>0){  
	                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
	                }  
	            }  
	            return ipAddress;   
	    }

	/**
	 * 小数点前0补全  ：  .12-》0.12
	 * @param targetStr
	 * @return
	 */
	public static String doubleZeroFill(String targetStr) {
		String result = targetStr;
		if(targetStr.contains(".")){
			try {
				Double num = Double.valueOf(targetStr);
				result = num.toString();
			}catch(Exception ex) {
				System.out.println(targetStr + "解析转换异常");
			}
		}
		return result;
	}
	
}
