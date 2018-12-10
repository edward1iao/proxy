package com.edward1iao.proxy.model;

public class TestModel{
	private String test1;
	private String test2;
	public String getTest1() {
		return test1;
	}
	public void setTest1(String test1) {
		this.test1 = test1;
	}
	public String getTest2() {
		return test2;
	}
	public void setTest2(String test2) {
		this.test2 = test2;
	}
	@Override
	public String toString() {
		return "TestModel [test1=" + test1 + ", test2=" + test2 + "]";
	}
	public TestModel(String test1, String test2) {
		super();
		this.test1 = test1;
		this.test2 = test2;
	}
	public TestModel() {
		super();
	}
	
}
