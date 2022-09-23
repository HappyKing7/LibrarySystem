package com.Controller;

import com.Bean.Borrowed.BorrowedInfo;
import com.Bean.Result;
import com.Config.ApiJsonModel;
import com.Log.Log;
import com.Service.BorrowedService.BorrowedService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/borrowed")
public class BorrowedController {
	@Autowired
	private BorrowedService bs;

	@ApiOperation(value="查询所有借阅信息", notes = "查询所有借阅信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "current", value = "当前页数",required = true),
			@ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "查询成功"),
					@ApiResponse(code = 103, message = "没有数据"),
			})
	@PostMapping("/getBorrowedInfos")
	@ResponseBody
	public Result getBorrowedInfos(@RequestBody Map map){
		Log log = new Log();
		log.inputLogMap(map);
		int current = Integer.valueOf(map.get("current").toString());
		int size = Integer.valueOf(map.get("size").toString());
		Result result =new Result();

		log.functionLog("分页查询所有借阅信息");
		List<BorrowedInfo> bis = bs.findAllBorrowedInfos((current-1)*size,size,0);
		if(bis.size()!=0) {
			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(bis);
			result.setSize(size);
			result.setCurrent(current);
			log.functionLog("查询所有借阅信息数量");
			result.setTotal(bs.findAllBorrowedInfos((current-1)*size,size,1).size());
		} else {
			result.setCode(103);
			result.setMsg("没有数据!");
		}

		log.outPutLog(result);
		return result;
	}


	@ApiOperation(value="借阅编号查询借阅信息", notes = "根据借阅编号查询借阅信息",httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "borrowedID", value = "借阅编号",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "新增成功"),
					@ApiResponse(code = 109, message = "没有数据"),
			})
	@RequestMapping("/findBorrowedInfosByBorrowedID")
	@ResponseBody
	public Result findAllBorrowedInfosByBorrowedID(@RequestBody Map map,@RequestParam(value = "borrowedID",required = false) String borrowedID)
	{
		Log log = new Log();
		BorrowedInfo bi = new BorrowedInfo();
		List<BorrowedInfo> bis = new ArrayList<>();
		log.functionLog("根据借阅编号查询借阅信息");
		if(map.size()!=0){log.inputLogMap(map);

			bi = JSON.parseObject(JSON.toJSONString(map),BorrowedInfo.class);
			bis = bs.findBorrowedInfosByBorrowedID(bi.getBORROWED_ID());
		}else{
			List list = new ArrayList();
			list.add(borrowedID);
			log.inputLogMap(map);
			bis = bs.findBorrowedInfosByBorrowedID(borrowedID);
		}

		Result result =new Result();
		if(bis.size()!=0)
		{
			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(bis);
		}
		else{
			result.setCode(103);
			result.setMsg("没有数据!");
		}

		log.outPutLog(result);
		return result;
	}

	@ApiOperation(value="根据用户账号查询借阅信息", notes = "根据用户账号查询借阅信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "userID", value = "用户账号",required = true),
			@ApiModelProperty(name = "current", value = "当前页数",required = true),
			@ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "新增成功"),
					@ApiResponse(code = 109, message = "没有数据"),
			})
	@RequestMapping("/findAllBorrowedInfosByUserID")
	@ResponseBody
	public Result findAllBorrowedInfosByUserID(@RequestBody Map map){
		Log log = new Log();
		log.inputLogMap(map);
		Result result =new Result();
		String userID = "";
		int current = 0;
		int size = 0;

		if(map.size()==1)
		{
			Map ruleFormMap = (Map)((Map)map.get("params")).get("ruleForm");
			userID =((Map)map.get("params")).get("user_ID").toString();
			current = Integer.valueOf(ruleFormMap.get("current").toString());
			size = Integer.valueOf(ruleFormMap.get("size").toString());
		}else{
			userID = map.get("userID").toString();
			current = Integer.valueOf(map.get("current").toString());
			size = Integer.valueOf(map.get("size").toString());
		}

		log.functionLog("根据用户账号分页查询借阅信息");
		List<BorrowedInfo> bis = bs.findAllBorrowedInfosByUserID(userID,(current-1)*size,size,0);
		if(bis.size()!=0)
		{
			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(bis);
			result.setSize(size);
			result.setCurrent(current);
			log.functionLog("根据用户账号查询借阅信息数量");
			result.setTotal(bs.findAllBorrowedInfosByUserID(userID,(current-1)*size,size,1).size());
		}
		else {
			result.setCode(103);
			result.setMsg("没有数据!");
		}

		log.outPutLog(result);
		return result;
	}

	@ApiOperation(value="根据关键词查询借阅信息", notes = "根据借阅编号或书籍编号或用户账号查询借阅信息",httpMethod = "POST")
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
	@RequestMapping("/findBorrowedInfosByKey")
	@ResponseBody
	public Result findBorrowedInfosByKey(@RequestBody Map map) {
		Log log = new Log();
		log.inputLogMap(map);
		Result result =new Result();
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

		log.functionLog("根据关键词分页查询借阅信息");
		List<BorrowedInfo> bis = bs.findBookInfosByBorrowedIDOrUserIDOrBookID(key,key,key,(current-1)*size,size,0);
		if(bis.size()!=0)
		{
			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(bis);
			result.setSize(size);
			result.setCurrent(current);
			log.functionLog("根据关键词查询借阅信息数量");
			result.setTotal(bs.findBookInfosByBorrowedIDOrUserIDOrBookID(key,key,key,(current-1)*size,size,1).size());
		}
		else {
			result.setCode(103);
			result.setMsg("没有数据!");
		}

		log.outPutLog(result);
		return result;
	}

	@ApiOperation(value="刷新借阅状态", notes = "系统根据时间自动刷新借阅状态",httpMethod = "POST")
	@RequestMapping("/updateBorrowedInfosStatus")
	@ResponseBody
	public Result updateBorrowedInfosStatus(@RequestBody Map map) {
		Log log = new Log();
		log.inputLogMap(map);
		Result result = new Result();

		log.functionLog("查询所有借阅信息");
		List<BorrowedInfo> bis = bs.findAllBorrowedInfos(0,0,1);
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(bis.size()!=0)
		{
			for (int i = 0; i < bis.size(); i++) {
				if(df.format(day).compareTo(bis.get(i).getBORROWED_END_DATE())>0 && bis.get(i).getBORROWED_STATUS().equals("0"))
				{
					log.functionLog("刷新借阅状态");
					bs.updateBorrowedInfosStatus(bis.get(i).getBORROWED_ID(),"1");
				}
			}
		}

		result.setCode(200);
		result.setMsg("操作成功");

		log.outPutLog(result);
		return result;
	}
}
