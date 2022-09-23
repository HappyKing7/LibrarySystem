package com.Log;

import com.Bean.Result;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class Log {
	public void inputLogMap(Map map){
		log.info("入参类型: " + map.getClass());
		log.info( "入参内容: " + map);
	}

	public void inputLogList(List list){
		log.info("入参类型: " + list.getClass());
		log.info("入参: " + list);
	}

	public void inputLogOject(Object object){
		log.info("入参类型: " + object.getClass());
		log.info("入参: " + JSON.toJSONString(object));
	}

	public void outPutLog(Result result){
		log.info("出参类型: " + result.getClass());
		log.info("出参: " + (JSON.toJSONString(result)));
	}

	public void functionLog(String info){
		log.info("功能描述:" + info);
		log.info("sql:");
	}
}
