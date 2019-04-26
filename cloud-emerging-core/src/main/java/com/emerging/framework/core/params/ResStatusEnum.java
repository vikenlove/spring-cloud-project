package com.emerging.framework.core.params;

public enum ResStatusEnum {
	 	SUCCESS(200,"操作成功"),
	    FAIL(400,"请求失败"),
	    EXCEPTION(500,"系统异常");

	    private int code;

	    private String message;

	    ResStatusEnum(int code, String message) {
	        this.code = code;
	        this.message = message;
	    }

	    public int getCode() {
	        return code;
	    }

	    public String getMessage() {
	        return message;
	    }
}
