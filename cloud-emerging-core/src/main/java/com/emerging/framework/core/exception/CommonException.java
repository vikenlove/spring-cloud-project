package com.emerging.framework.core.exception;

public class CommonException extends RuntimeException {

	
	/**
		 * 
		 */
	private static final long serialVersionUID = -4535356599717560623L;
	private Integer code;
	private String data;

	/**
	 * Creates a new GroupException object.
	 */
	public CommonException() {

		super();
	}

	/**
	 * @param message
	 */
	public CommonException(final String message,Integer code,String data) {
		super(message);
		this.code = code;
		this.data = data;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CommonException(final String message, final Throwable cause) {

		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public CommonException(final Throwable cause) {

		super(cause);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
