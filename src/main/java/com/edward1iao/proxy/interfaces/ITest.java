package com.edward1iao.proxy.interfaces;

import com.edward1iao.proxy.annotations.HttpProxy;
import com.edward1iao.proxy.enums.EnumHttpRequestMethod;
import com.edward1iao.proxy.exception.HttpProxyException;
import com.edward1iao.proxy.model.TestModel;

public interface ITest {
	@HttpProxy(URL="https://www.baidu.com/")
	String getTest0()throws HttpProxyException;
	@HttpProxy(URL="http://127.0.0.1/framework/httpGetProxy/")
	TestModel getTest1()throws HttpProxyException;
	@HttpProxy(URL="http://127.0.0.1/framework/httpGetProxy/")
	TestModel getTest3(TestModel testModel)throws HttpProxyException;
	@HttpProxy(URL="http://127.0.0.1/framework/httpPostProxy/",requestMethod=EnumHttpRequestMethod.POST)
	TestModel getTest2()throws HttpProxyException;
	@HttpProxy(URL="http://127.0.0.1/framework/httpPostProxy/",requestMethod=EnumHttpRequestMethod.POST)
	TestModel getTest4(TestModel testModel)throws HttpProxyException;
}
