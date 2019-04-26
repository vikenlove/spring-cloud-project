package com.emerging.framework.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据流工具类
 * 
 * @author wt
 *
 */
public class RederUtil {
	/**
	 * 读取输入流参数
	 * 
	 * @author wt
	 *
	 */
	public static String getInputStream(HttpServletRequest request) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		in.close();
		return sb.toString();

	}

}
