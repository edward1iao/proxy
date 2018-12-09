package com.edward1iao.proxy.enums;

public enum EnumHttpStatus {
	STATUS0(0,"SUCCESS"),
	STATUS500(500,"500EROR");
	private int status;
	private String desc;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private EnumHttpStatus(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}
}
