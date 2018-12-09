package com.edward1iao.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.edward1iao.proxy.exception.HttpProxyException;
import com.edward1iao.proxy.interfaces.ITest;
import com.edward1iao.proxy.proxy.HttpProxyHandler;

public class ProxyFactory {
	public static ITest ITEST = getInstance(ITest.class,HttpProxyHandler.class);
	@SuppressWarnings("unchecked")
	public static <T>T getInstance(Class<T> clazz1,Class<? extends InvocationHandler> clazz2)throws HttpProxyException{
		ClassLoader classLoader = clazz1.getClassLoader();  
        Class<?>[] interfaces = new Class[]{clazz1};  
        try {
			return (T) Proxy.newProxyInstance(classLoader, interfaces, clazz2.newInstance());
		} catch (IllegalArgumentException | InstantiationException | IllegalAccessException e) {
			throw new HttpProxyException(e.getMessage());
		}
	}
}
