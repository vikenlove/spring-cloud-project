package com.emerging.framework.core.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;


abstract class Utils {
	
	 public static int random(final int max) {
	    double result = Math.random() * max;
	    result = Math.ceil(result);
	    return new Double(result).intValue();
	  }

	  public static String uuid() {
	    return StringUtils.remove(UUID.randomUUID().toString(), "-");
	  }
	  
	  /**
		 * 获取32位UUID
		 * 
		 * @return
		 */
		public static String getUUID() {
			return uuid().toUpperCase();
		}
}
