package com.emerging.framework.core.utils;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

/**
 * Json工具类
 * 
 * @author llz
 *
 */
public class JsonUtil {

	private static Gson gson = new Gson();

	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJsonToObject(String str, Class<T> type) {
		return gson.fromJson(str, type);
	}

	/**
	 * 把对象转换为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String GetObjectToJson(Object obj) {
		return gson.toJson(obj);
	}
	
	/** 
     * json字符串转成对象 
     * @param str   
     * @param type  
     * @return  
     */  
    public static <T> List<T> fromJson(String str, Class<T[]> type){  
    	T[] list = gson.fromJson(str, type);  
    	return Arrays.asList(list);
    }  
}
