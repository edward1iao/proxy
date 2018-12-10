package com.edward1iao.proxy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONObject;
import com.edward1iao.proxy.enums.EnumHttpRequestMethod;
import com.edward1iao.proxy.enums.EnumHttpStatus;
import com.edward1iao.proxy.exception.HttpProxyException;
import com.edward1iao.proxy.model.HttpResponse;

/**
 * @author edward1iao
 * @desc httpClient工具类
 * @time 2018年12月10日 上午11:44:43
 */
public class HttpClientUtils {
	
	private static HttpMethodBase getHttpMethod(EnumHttpRequestMethod enumHttpRequestMethod,String url,Object object){
		if(EnumHttpRequestMethod.GET == enumHttpRequestMethod){
			String lastUrl = url+setParams(object);
			System.out.println("url:"+lastUrl);
			return new GetMethod(lastUrl);
		}else if(EnumHttpRequestMethod.POST == enumHttpRequestMethod){
			PostMethod postMethod = new PostMethod(url);
			setParams(postMethod, object);
			return postMethod;
		}else{
			throw new HttpProxyException("request method error");
		}
	}
	
	public static HttpResponse doGet(String url,Map<String,String> requestHeader,Object object){
		return send(EnumHttpRequestMethod.GET,url,requestHeader,object);
	}
	public static HttpResponse doPost(String url,Map<String,String> requestHeader,Object object){
		return send(EnumHttpRequestMethod.POST,url,requestHeader,object);
	}
	
	public static HttpResponse send(EnumHttpRequestMethod enumHttpRequestMethod,String url,Map<String,String> requestHeader,Object object){
		HttpClient httpClient = new HttpClient();
		HttpMethodBase httpMethodBase = null;
		StringBuilder responseBody = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			httpMethodBase = getHttpMethod(enumHttpRequestMethod, url, object);
			setRequestHeader(httpMethodBase, requestHeader);
			httpClient.executeMethod(httpMethodBase);
			if(HttpStatus.SC_OK != httpMethodBase.getStatusCode())throw new HttpProxyException("请求失败");
			inputStream = httpMethodBase.getResponseBodyAsStream();  
		    br = new BufferedReader(new InputStreamReader(inputStream));  
		    String str= null;  
		    while((str = br.readLine()) != null){  
		    	responseBody.append(str);  
		    }
		    return new HttpResponse(responseBody.toString());
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "errorMsg:"+e.getMessage();
			if(httpMethodBase!=null){
				msg +=",statusCode:"+httpMethodBase.getStatusCode()+",responseBody:"+responseBody.toString();
			}
			return new HttpResponse(msg,EnumHttpStatus.STATUS500.getStatus());
		} finally {
			if(br!=null){
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream!=null){
				try {
					inputStream.close();
					inputStream =null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpMethodBase!=null){
				httpMethodBase.releaseConnection();
				httpMethodBase = null;
			}
		}
	}
	
	private static void setRequestHeader(HttpMethodBase httpMethodBase,Map<String,String> requestHeader){
		if(httpMethodBase==null)return;
		if(requestHeader==null){
			requestHeader = new HashMap<String, String>();
			requestHeader.put("Content-Type", "application/json;charset=utf-8");
			requestHeader.put("Accept", "application/json;charset=utf-8");
			requestHeader.put("Cache-Control", "no-cache");
		}
		for(String key:requestHeader.keySet()){
			httpMethodBase.addRequestHeader(key, requestHeader.get(key));
		}
	}
	
	public static String setParams(Object object){
		if(object==null)return "";
		StringBuilder sb = new StringBuilder();
		JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
		boolean isFirst = true;
		for(String key:jsonObject.keySet()){
			if(isFirst){
				sb.append("?");
				isFirst = false;
			}else{
				sb.append("&");
			}
			sb.append(key+"="+jsonObject.get(key));
		}
		return sb.toString();
	}
	
	private static void setParams(PostMethod postMethod,Object object){
		if(postMethod==null||object==null)return;
		JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
		for(String key:jsonObject.keySet()){
			postMethod.addParameter(key,jsonObject.getString(key));
		}
	}
 
}
