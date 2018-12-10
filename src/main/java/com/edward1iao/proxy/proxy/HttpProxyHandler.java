package com.edward1iao.proxy.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONObject;
import com.edward1iao.proxy.annotations.HttpProxy;
import com.edward1iao.proxy.exception.HttpProxyException;
import com.edward1iao.proxy.model.HttpResponse;
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
        HttpResponse httpResponse = null;
        if(httpProxy!=null) {
        	httpResponse = HttpClientUtils.send(httpProxy.requestMethod(),httpProxy.URL(), null,args==null?null:args[0]);
        }else{
        	throw new HttpProxyException("非http请求代理类");
        }
        System.out.println("接口方法调用结束");
        if(String.class == method.getReturnType()) return httpResponse.getResponseBody();
        try {
        	return JSONObject.parseObject(httpResponse.getResponseBody(),method.getReturnType());    
		} catch (Exception e) {
			throw new HttpProxyException("返回格式错误："+e.getMessage());
		}
    }
}
