package com.edward1iao.proxy.model;

import java.io.Serializable;

import com.edward1iao.proxy.enums.EnumHttpStatus;

public class HttpResponse implements Serializable {
	private static final long serialVersionUID = 2407745443815459323L;
	private String responseBody;
	private Integer status;
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public HttpResponse() {
		super();
	}
	public HttpResponse(String responseBody) {
		super();
		this.responseBody = responseBody;
		this.status = EnumHttpStatus.STATUS0.getStatus();
	}
	public HttpResponse(String responseBody, Integer status) {
		super();
		this.responseBody = responseBody;
		this.status = status;
	}
	@Override
	public String toString() {
		return "HttpResponse [responseBody=" + responseBody + ", status=" + status + "]";
	}
}
