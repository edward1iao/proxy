package com.edward1iao.proxy.exception;

public class HttpProxyException extends RuntimeException {

	private static final long serialVersionUID = 5333478137720279604L;

	private String msg;
    private int code = 500;
    
    public HttpProxyException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public HttpProxyException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public HttpProxyException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public HttpProxyException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
