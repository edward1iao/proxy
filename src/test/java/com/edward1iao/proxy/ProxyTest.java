package com.edward1iao.proxy;

import org.junit.Test;

import com.edward1iao.proxy.interfaces.ITest;
import com.edward1iao.proxy.model.TestModel;
import com.edward1iao.proxy.proxy.HttpProxyHandler;

public class ProxyTest 
{
    @Test
    public void test()
    {
    	System.out.println(ProxyFactory.getInstance(ITest.class, HttpProxyHandler.class).getTest1());
		System.out.println(ProxyFactory.ITEST.getTest4(new TestModel("adsf","asdfddd")));
    }
}
