package com.edward1iao.proxy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.edward1iao.proxy.enums.EnumHttpRequestMethod;

@Retention(RetentionPolicy.RUNTIME)//设置生命周期为运行时有效
@Target({ElementType.METHOD})//设置为可以是用在方法上
public @interface HttpProxy {
	EnumHttpRequestMethod requestMethod() default EnumHttpRequestMethod.GET;
	String URL();
}
