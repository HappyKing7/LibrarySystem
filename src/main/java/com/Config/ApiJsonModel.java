package com.Config;

import io.swagger.annotations.ApiModelProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Swagger扩展注解
 * 用于application/json请求
 * 并使用诸如Map或JSONObject等非具体实体类接收参数时,对参数进行进一步描述
 *
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonModel {
	/**
	 * 原生Swagger注解 用于描述具体字段
	 * accessMode,extensions配置将无效
	 *
	 * @return value
	 */
	ApiModelProperty[] value() default {};
}
