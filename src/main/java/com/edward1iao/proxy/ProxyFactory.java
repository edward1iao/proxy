package com.edward1iao.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import com.edward1iao.proxy.exception.HttpProxyException;
import com.edward1iao.proxy.interfaces.ITest;
import com.edward1iao.proxy.proxy.HttpProxyHandler;

/**
 * @author edward1iao
 * @desc 代理工厂类 
 * @time 2018年12月10日 下午12:39:27
 */
public class ProxyFactory {
	public static HashMap<Class<? extends InvocationHandler>, HashMap<Class<?>, Object>> proxyCacheMap = new HashMap<Class<? extends InvocationHandler>, HashMap<Class<?>,Object>>();
	public static ITest ITEST = getHttpInstance(ITest.class);
	@SuppressWarnings("unchecked")
	public static <T>T getInstance(Class<T> clazz1,Class<? extends InvocationHandler> clazz2)throws HttpProxyException{
		Object object = getProxyCache(clazz1, clazz2);
		if(object!=null)return (T)object;
		ClassLoader classLoader = clazz1.getClassLoader();  
        Class<?>[] interfaces = new Class[]{clazz1};  
        try {
			return (T) Proxy.newProxyInstance(classLoader, interfaces, clazz2.newInstance());
		} catch (IllegalArgumentException | InstantiationException | IllegalAccessException e) {
			throw new HttpProxyException(e.getMessage());
		}
	}
	public static <T>T getHttpInstance(Class<T> clazz1)throws HttpProxyException{
		return getInstance(clazz1, HttpProxyHandler.class);
	}
	private static Object getProxyCache(Class<?> clazz1,Class<? extends InvocationHandler> clazz2){
		HashMap<Class<?>, Object> proxyCache = proxyCacheMap.get(clazz2);
		if(proxyCache!=null){
			Object object = proxyCache.get(clazz1);
			if(object!=null)return object;
		}
		return null;
	}
}
