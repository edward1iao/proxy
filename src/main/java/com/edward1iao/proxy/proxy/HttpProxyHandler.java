package com.edward1iao.proxy.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONObject;
import com.edward1iao.proxy.annotations.HttpProxy;
import com.edward1iao.proxy.exception.HttpProxyException;
import com.edward1iao.proxy.utils.HttpClientUtils;

public class HttpProxyHandler implements InvocationHandler {
	@Override    
    public Object invoke(Object proxy, Method method, Object[] args)    
            throws Throwable {    
        System.out.println("接口方法调用开始");    
        //执行方法    
        System.out.println("method toGenericString:"+method.toGenericString());  
        System.out.println("method name:"+method.getName());  
        System.out.println("method args:"+JSONObject.toJSONString(args));  
        Annotation[] annotations = method.getAnnotations();
        HttpProxy httpProxy = null;
        if(annotations!=null&&annotations.length>0){
        	for(Annotation annotation :annotations){
        		if(annotation instanceof  HttpProxy){
        			httpProxy =(HttpProxy) annotation;
        			break;
        		}
        	}
        }
        String responseBodyStr = null;
        if(httpProxy!=null) {
        	responseBodyStr = HttpClientUtils.send(httpProxy.requestMethod(),httpProxy.URL(), null,args==null?null:args[0]);
        }else{
        	throw new HttpProxyException("非http请求代理类");
        }
        System.out.println("接口方法调用结束");
        return JSONObject.parseObject(responseBodyStr,method.getReturnType());    
    }
}
