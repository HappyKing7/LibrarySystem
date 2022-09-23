package com.Bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class Result {
	@ApiModelProperty(value = "请求结果标识")
	private int code; // 200是正常，非200表示异常

	@ApiModelProperty(value = "请求结果详情")
	private String msg;

	@ApiModelProperty(value = "返回结果")
	private Object data;

	@ApiModelProperty(value = "当前页数（用于分页显示）")
	private int current;

	@ApiModelProperty(value = "每页显示的页码数量（用于分页显示）")
	private int size;

	@ApiModelProperty(value = "数据总数（用于分页显示）")
	private int total;

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setResult(int code,String msg,Object data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
