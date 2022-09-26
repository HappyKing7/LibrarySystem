package com.Controller;

import com.Bean.BookType.BookTypeInfo;
import com.Bean.Result;
import com.Config.ApiJsonModel;
import com.Log.Log;
import com.Service.BookTypeService.BookTypeService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/booktype")
public class BookTypeContorller {
	@Autowired
	private BookTypeService bts;

	@ApiOperation(value="查询所有图书类型信息", notes = "查询所有图书类型信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "current", value = "当前页数",required = true),
			@ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "查询成功"),
					@ApiResponse(code = 103, message = "没有数据"),
			})
	@RequestMapping("/getBookTypeInfos")
	@ResponseBody
	public Result getBookTypeInfos(@RequestBody Map map)
	{
		Log log = new Log();
		log.inputLogMap(map);
		int current = Integer.valueOf(map.get("current").toString());
		int size = Integer.valueOf(map.get("size").toString());
		Result result =new Result();

		log.functionLog("分页查询所有图书类型信息");
		List<BookTypeInfo> bti = bts.findAllBookTypeInfos((current-1)*size,size,0);
		if(bti.size()!=0) {
			for (int i = 0; i < bti.size(); i++) {
				if(bti.get(i).getBOOK_TYPE_STATUS().equals("0"))
				{
					bti.get(i).setBOOK_TYPE_STATUS("有效");
				}
				else if(bti.get(i).getBOOK_TYPE_STATUS().equals("1"))
				{
					bti.get(i).setBOOK_TYPE_STATUS("失效");
				}
			}

			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(bti);
			result.setSize(size);
			result.setCurrent(current);
			log.functionLog("查询所有图书类型信息数量");
			result.setTotal(bts.findAllBookTypeInfos((current-1)*size,size,1).size());
		}
		else {
			result.setCode(103);
			result.setMsg("没有数据!");
		}

		log.outPutLog(result);
		return result;
	}


	@ApiOperation(value="新增图书信息", notes = "录入新的图书信息",httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bookTypeID", value = "图书编号",required = true),
			@ApiImplicitParam(name = "bookTypeName", value = "图书名称",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "新增成功"),
					@ApiResponse(code = 109, message = "图书类型信息已存在"),
			})
	@RequestMapping("/insertBookTypeInfo")
	@ResponseBody
	public Result insertBookTypeInfo(@RequestBody Map map,@RequestParam(value = "bookTypeID",required = false) String bookTypeID,
									 @RequestParam(value = "bookTypeName",required = false) String bookTypeName)
	{
		Log log = new Log();
		Result result =new Result();
		BookTypeInfo bti = new BookTypeInfo();
		if(map.size()!=0){
			bti = JSON.parseObject(JSON.toJSONString(map), BookTypeInfo.class);
			log.inputLogMap(map);
		}
		else{
			bti.setBOOK_TYPE_ID(bookTypeID);
			bti.setBOOK_TYPE_NAME(bookTypeName);
			log.inputLogOject(bti);
		}

		log.functionLog("校验图书类型信息是否已存在");
		List<BookTypeInfo> btis = bts.findAllBookTypeInfoByBookTypeID(bti.getBOOK_TYPE_ID());
		if(btis.size()!=0){
			result.setCode(109);
			result.setMsg("图书类型信息已存在!");
			return result;
		}

		log.functionLog("新增图书信息");
		bts.insertBookTypeInfo(bti);
		result.setCode(200);
		result.setMsg("新增成功");
		return result;
	}

	@ApiOperation(value="修改图书类型信息", notes = "根据图书类型编号修改图书类型信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "bookTypeID", value = "图书类型编号",required = true),
			@ApiModelProperty(name = "bookTypeName", value = "图书类型名称",required = true),
			@ApiModelProperty(name = "bookTypeStatus", value = "状态",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "操作成功"),
			})
	@RequestMapping("/updateBookTypeInfo")
	@ResponseBody
	public Result updateBookTypeInfo(@RequestBody Map map)
	{
		Log log = new Log();
		log.inputLogMap(map);
		Result result =new Result();

		BookTypeInfo bti = JSON.parseObject(JSON.toJSONString(map),BookTypeInfo.class);
		log.functionLog("修改图书类型信息");
		bts.updateBookTypeInfo(bti);
		result.setCode(200);
		result.setMsg("操作成功");

		log.outPutLog(result);
		return result;
	}

	@ApiOperation(value="关键字查询图书类型信息", notes = "根据关键字查询图书类型信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "key", value = "搜索关键字",required = true),
			@ApiModelProperty(name = "current", value = "当前页数",required = true),
			@ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "新增成功"),
					@ApiResponse(code = 109, message = "没有数据"),
			})
	@RequestMapping("/findBookTypeInfosByKey")
	@ResponseBody
	public Result findBookTypeInfosByKey(@RequestBody Map map)
	{
		Log log = new Log();
		log.inputLogMap(map);
		String key = "";
		int current = 0;
		int size = 0;

		if(map.size()==1)
		{
			Map ruleFormMap = (Map)((Map)map.get("params")).get("ruleForm");
			key =((Map)map.get("params")).get("key").toString();
			current = Integer.valueOf(ruleFormMap.get("current").toString());
			size = Integer.valueOf(ruleFormMap.get("size").toString());
		}else{
			key = map.get("key").toString();
			current = Integer.valueOf(map.get("current").toString());
			size = Integer.valueOf(map.get("size").toString());
		}

		Result result =new Result();
		log.functionLog("根据关键字查询分页图书类型信息");
		List<BookTypeInfo> btis = bts.findAllBookTypeInfoByIDOrName(key,(current-1)*size,size,0);
		if(btis.size()!=0)
		{
			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(btis);
			result.setSize(size);
			result.setCurrent(current);
			log.functionLog("根据关键字查询图书类型信息数量");
			result.setTotal(bts.findAllBookTypeInfoByIDOrName(key,(current-1)*size,size,1).size());
		}
		else {
			result.setCode(103);
			result.setMsg("没有数据!");
		}

		log.outPutLog(result);
		return result;
	}

	@ApiOperation(value="查询所有图书类型信息（用于选择）", notes = "查询所有图书类型信息（用于选择）",httpMethod = "POST")
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "查询成功"),
					@ApiResponse(code = 103, message = "没有数据"),
			})
	@RequestMapping("/getAllBBookTypeInfos")
	@ResponseBody
	public Result getAllBBookTypeInfos(@RequestBody Map map)
	{
		Log log = new Log();
		log.inputLogMap(map);
		Result result =  new Result();

		log.functionLog("分页查询所有图书类型信息");
		List<BookTypeInfo> bti = bts.findAllBookTypeInfos(0,0,1);
		if(bti.size()!=0) {
			log.functionLog("查询所有图书类型信息数量");
			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(bti);
		}
		else{
			result.setCode(103);
			result.setMsg("没有数据!");
		}

		log.outPutLog(result);
		return result;
	}
}
