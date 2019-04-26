package com.emerging.framework.core.params;

import java.io.Serializable;

public class ResponseData<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4948307887400544317L;

	private int status = 200;

    private String message;
    
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data==null?null:data;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}
