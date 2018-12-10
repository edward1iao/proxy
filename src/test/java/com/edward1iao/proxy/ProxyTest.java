package com.edward1iao.proxy;

import org.junit.Test;

import com.edward1iao.proxy.annotations.HttpProxy;
import com.edward1iao.proxy.enums.EnumHttpRequestMethod;
import com.edward1iao.proxy.exception.HttpProxyException;
import com.edward1iao.proxy.model.TestModel;

public class ProxyTest 
{
    @Test
    public void test()
    {
    	System.out.println(ProxyFactory.getHttpInstance(IMoni.class).httpPostProxy(new TestModel("ssdfadsf", null)));
    	System.out.println(ProxyFactory.getHttpInstance(IBaidu.class).doGet());
    }
    interface IBaidu{
    	@HttpProxy(URL="https://www.baidu.com/")
    	String doGet();
    }
    interface IMoni{
    	@HttpProxy(URL="http://172.29.224.89/framework/httpPostProxy/",requestMethod=EnumHttpRequestMethod.POST)
    	TestModel httpPostProxy(TestModel testModel)throws HttpProxyException;
    }
}
