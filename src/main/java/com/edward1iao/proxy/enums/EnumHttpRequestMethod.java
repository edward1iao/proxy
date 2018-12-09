package com.edward1iao.proxy.enums;
/**
 * http请求方式	Int	1:get，2:post
 * liaoxinyue
 * Wed Dec 05 15:10:19 CST 2018
 */
public enum EnumHttpRequestMethod {
	/**
	 *[1,"GET请求"]
	 */
	GET(1,"GET请求"),
	/**
	 *[2,"POST请求"]
	 */
	POST(2,"POST请求");
	private int type;
	private String desc;
	public void setType(int type){
		this.type=type;
	}
	public int getType(){
		return type;
	}
	public void setDesc(String desc){
		this.desc=desc;
	}
	public String getDesc(){
		return desc;
	}
	private EnumHttpRequestMethod(int type,String desc){
		this.type=type;
		this.desc=desc;
	}
	/**
	 * 查找枚举,不存在返回null
	 */
	public static EnumHttpRequestMethod getEnum(Integer type,String desc){
		for(EnumHttpRequestMethod e:values()){
			if((type!=null&&e.getType()==type)||(desc!=null&&e.getDesc().equals(desc))){
				return e;
			}
		}
		return null;
	}
}
