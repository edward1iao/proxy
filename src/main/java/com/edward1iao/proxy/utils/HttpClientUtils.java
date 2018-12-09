package com.edward1iao.proxy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONObject;
import com.edward1iao.proxy.enums.EnumHttpRequestMethod;
import com.edward1iao.proxy.enums.EnumHttpStatus;
import com.edward1iao.proxy.model.HttpResponse;

public class HttpClientUtils {
	
	public static String send(EnumHttpRequestMethod enumHttpRequestMethod,String url,Map<String,String> requestHeader,Object object){
		return EnumHttpRequestMethod.GET == enumHttpRequestMethod?doGet(url, requestHeader,object):doPost(url, requestHeader, object);
	}
	
	public static HttpResponse doGet1(String url,Map<String,String> requestHeader,Object object){
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = null;
		StringBuilder responseBodyStr = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			String lastUrl = url+getParams(object);
			System.out.println("url:"+lastUrl);
			getMethod = new GetMethod(lastUrl);
			setRequestHeader(getMethod, requestHeader);
			httpClient.executeMethod(getMethod);
			inputStream = getMethod.getResponseBodyAsStream();  
		    br = new BufferedReader(new InputStreamReader(inputStream));  
		    String str= null;  
		    while((str = br.readLine()) != null){  
		    	responseBodyStr.append(str);  
		    }
		    return new HttpResponse(responseBodyStr.toString());
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "errorMsg:"+e.getMessage();
			if(getMethod!=null){
				msg +=",statusCode:"+getMethod.getStatusCode()+",responseBody:"+responseBodyStr.toString();
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
			if(getMethod!=null){
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
	}
	
	public static String doGet(String url,Map<String,String> requestHeader,Object object) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = null;
		StringBuilder responseBodyStr = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			String lastUrl = url+getParams(object);
			System.out.println("url:"+lastUrl);
			getMethod = new GetMethod(lastUrl);
			setRequestHeader(getMethod, requestHeader);
			httpClient.executeMethod(getMethod);
			inputStream = getMethod.getResponseBodyAsStream();  
		    br = new BufferedReader(new InputStreamReader(inputStream));  
		    String str= "";  
		    while((str = br.readLine()) != null){  
		    	responseBodyStr.append(str);  
		    }
		} catch (Exception e) {
			e.printStackTrace();
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
			if(getMethod!=null){
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
        return responseBodyStr.toString();
    }
	
	public static String doPost(String url,Map<String,String> requestHeader,Object object){
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = null;
		StringBuilder responseBodyStr = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			postMethod = new PostMethod(url);
			setRequestHeader(postMethod, requestHeader);
			setParams(postMethod, object);
			httpClient.executeMethod(postMethod);
			inputStream = postMethod.getResponseBodyAsStream();  
		    br = new BufferedReader(new InputStreamReader(inputStream)); 
		    System.out.println(postMethod.getResponseHeaders());
		    String str= "";  
		    while((str = br.readLine()) != null){  
		    	responseBodyStr.append(str);  
		    }
		} catch (Exception e) {
			e.printStackTrace();
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
			if(postMethod!=null){
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
        return responseBodyStr.toString();
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
	
	/**
	 * 获取请求参数
	 * @param object
	 * @return
	 */
	public static String getParams(Object object){
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
