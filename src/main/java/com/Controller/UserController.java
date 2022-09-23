package com.Controller;

import com.Bean.Result;
import com.Bean.User.UserInfo;
import com.Bean.User.UserLogin;
import com.Bean.User.UserRegisterInfo;
import com.Config.ApiJsonModel;
import com.Log.Log;
import com.Service.UserService.UserInfoService;
import com.Service.UserService.UserLoginService;
import com.Service.UserService.UserRegisterService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRegisterService urs;
	@Autowired
	private UserLoginService uls;
	@Autowired
	private UserInfoService uis;

	@ApiOperation(value="用户登录", notes = "根据账户和密码登陆系统",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "userID", value = "用户账号",required = true),
			@ApiModelProperty(name = "userPassword", value = "用户密码",required = true)
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "成功"),
					@ApiResponse(code = 101, message = "账号或密码不正确"),
					@ApiResponse(code = 102, message = "该账号已经注销"),
					@ApiResponse(code = 107, message = "该用户信息不存在")
			})
	@PostMapping("/userLogin")
	@ResponseBody
	public Result userLogin(@RequestBody Map map) {
		Log log = new Log();
		log.inputLogMap(map);
		Result result =new Result();

		UserLogin ul = JSON.parseObject(JSON.toJSONString(map), UserLogin.class);
		log.functionLog("校验用户账号密码是否正确");
		List<UserLogin> userLoginList = uls.findUserLoginInfoByUserIDAndPass(ul);
		if (userLoginList.size()==0)
		{
			result.setCode(101);
			result.setMsg("账号或密码不正确");
		}else
		{
			if(userLoginList.get(0).getUSER_STATUS().equals("1"))
			{
				result.setCode(102);
				result.setMsg("该账号已经注销");
			}
			else{
				String userID = userLoginList.get(0).getUSER_ID();
				log.functionLog("校验用户信息是否存在");
				List<UserInfo> uisList = uis.findUserInfoByUserID(userID);
				if (uisList.size()==0)
				{
					result.setCode(107);
					result.setMsg("该用户信息不存在");
				}
				else{
					result.setCode(200);
					result.setMsg("登录成功");
					result.setData(userLoginList.get(0));
				}

			}
		}

		log.outPutLog(result);
		return result;
	}

	@ApiOperation(value="用户注册", notes = "根据用户输入信息注册新用户",httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userTYPE", value = "用户类型",required = true),
			@ApiImplicitParam(name = "userID", value = "用户账号",required = true),
			@ApiImplicitParam(name = "userPassword", value = "用户密码",required = true),
			@ApiImplicitParam(name = "userName", value = "用户名称",required = true),
			@ApiImplicitParam(name = "userGender", value = "用户性别",required = true),
			@ApiImplicitParam(name = "userPsptID", value = "用户证件号码",required = true),
			@ApiImplicitParam(name = "userPhone", value = "用户手机号码",required = true),
			@ApiImplicitParam(name = "userEmail", value = "用户邮箱",required = false)
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "注册成功"),
					@ApiResponse(code = 119, message = "用户ID已注册")
			})
	@PostMapping("/userRegister")
	@ResponseBody
	public Result userRegister(@RequestBody Map map,@RequestParam(value = "userTYPE",required = false) String userTYPE,
							   @RequestParam(value = "userID",required = false) String userID,
							   @RequestParam(value = "userPassword",required = false) String userPassword,
							   @RequestParam(value = "userName",required = false) String userName,
							   @RequestParam(value = "userGender",required = false) String userGender,
							   @RequestParam(value = "userPsptID",required = false) String userPsptID,
							   @RequestParam(value = "userPhone",required = false) String userPhone,
							   @RequestParam(value = "userEmail",required = false) String userEmail) {
		Log log =new Log();
		Result result =new Result();
		UserRegisterInfo uri = new UserRegisterInfo();
		if(map.size()!=0)
		{
			log.inputLogMap(map);
			uri = JSON.parseObject(JSON.toJSONString(map), UserRegisterInfo.class);
		}
		else{
			uri.setUSER_TYPE(userTYPE);
			uri.setUSER_ID(userID);
			uri.setUSER_PASSWORD(userPassword);
			uri.setUSER_NAME(userName);
			uri.setUSER_GENDER(userGender);
			uri.setUSER_PSPT_ID(userPsptID);
			uri.setUSER_PHONE(userPhone);
			uri.setUSER_EMAIL(userEmail);
			log.inputLogOject(uri);
		}

		log.functionLog("校验用户账号是否存在");
		if(urs.findUserRegisterInfoByUserID(uri).size()!=0)
		{
			result.setCode(119);
			result.setMsg("用户ID已注册");
		}else {
			urs.insertUserRegisterInfo(uri);
			urs.insertUserInfo(uri);
			result.setCode(200);
			result.setMsg("注册成功");
		}

		log.outPutLog(result);
		return result;
	}


	@ApiOperation(value="查询用户信息", notes = "根据账户查询用户信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "userID", value = "用户账号",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "成功"),
					@ApiResponse(code = 107, message = "该用户信息不存在"),
			})
	@PostMapping("/getUserInfoByUserID")
	@ResponseBody
	public Result getUserInfoByUserID(@RequestBody Map map) {
		Log log = new Log();
		log.inputLogMap(map);
		Result result = new Result();
		String userID = map.get("userID").toString();

		log.functionLog("查询用户信息");
		List<UserInfo> uisList = uis.findUserInfoByUserID(userID);
		if (uisList.size()==0)
		{
			result.setCode(107);
			result.setMsg("该用户信息不存在");
		}
		else
		{
			result.setCode(200);
			result.setMsg("用户信息存在");
			result.setData(uisList);
		}

		log.outPutLog(result);
		return result;
	}

	@ApiOperation(value="用户更新个人信息", notes = "根据用户输入信息更新个人信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "userID", value = "用户账号",required = true),
			@ApiModelProperty(name = "userName", value = "用户名称",required = true),
			@ApiModelProperty(name = "userGender", value = "用户性别",required = true),
			@ApiModelProperty(name = "userPsptID", value = "用户证件号码",required = true),
			@ApiModelProperty(name = "userPhone", value = "用户手机号码",required = true),
			@ApiModelProperty(name = "userEmail", value = "用户邮箱",required = true)
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "注册成功"),
			})
	@PostMapping("/updayeUserInfo")
	@ResponseBody
	public Result updayeUserInfo(@RequestBody Map map) {
		Log log = new Log();
		log.inputLogMap(map);
		Result result =new Result();
		UserInfo ui = JSON.parseObject(JSON.toJSONString(map), UserInfo.class);

		log.functionLog("更新用户信息");
		uis.updayeUserInfo(ui);
		result.setCode(200);
		result.setMsg("操作成功");

		log.outPutLog(result);
		return  result;
	}


	@ApiOperation(value="用户更新密码", notes = "根据用户输入信息更新用户密码",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "userID", value = "用户账号",required = true),
			@ApiModelProperty(name = "userOldPassword", value = "旧密码",required = true),
			@ApiModelProperty(name = "userNewPassword", value = "新密码",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "注册成功"),
					@ApiResponse(code = 108, message = "修改失败，请联系管理员"),
					@ApiResponse(code = 110, message = "密码错误"),
					@ApiResponse(code = 109, message = "新密码不能跟旧密码相同"),
			})
	@PostMapping("/updayeUserPassword")
	@ResponseBody
	public Result updayeUserPassword(@RequestBody Map map) {
		Log log = new Log();
		log.inputLogMap(map);

		Result result =new Result();
		String userID = map.get("userID").toString();
		String oldPassWord = map.get("userOldPassword").toString();
		String newPassWord = map.get("userNewPassword").toString();
		UserRegisterInfo uri = new UserRegisterInfo();
		uri.setUSER_ID(userID);

		log.functionLog("更新用户密码");
		List<UserRegisterInfo> uisList = urs.findUserRegisterInfoByUserID(uri);
		if(uisList.size()==0){
			result.setCode(108);
			result.setMsg("修改失败，请联系管理员");
		}else{
			if (!uisList.get(0).getUSER_PASSWORD().equals(oldPassWord))
			{
				result.setCode(110);
				result.setMsg("密码错误");
			}
			else
			{
				if (uisList.get(0).getUSER_PASSWORD().equals(newPassWord))
				{
					result.setCode(109);
					result.setMsg("新密码不能跟旧密码相同");
				}
				else{
					uls.updayeUserPassword(userID,newPassWord);
					result.setCode(200);
					result.setMsg("操作成功");
				}
			}
		}

		log.outPutLog(result);
		return  result;
	}

	@ApiOperation(value="查询所有用户信息", notes = "管理员查询所有用户信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "current", value = "当前页数",required = true),
			@ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "查询成功"),
			})
	@PostMapping("/getAllUserInfos")
	@ResponseBody
	public Result getAllUserInfos(@RequestBody Map map) {
		Log log = new Log();
		log.inputLogMap(map);
		Result result =new Result();
		int current = Integer.valueOf(map.get("current").toString());
		int size = Integer.valueOf(map.get("size").toString());

		log.functionLog("分页查询所有用户信息");
		List<UserInfo> userInfoList = uis.getAllUserInfos((current-1)*size,size,0);
		for (int i = 0; i < userInfoList.size(); i++) {
			UserRegisterInfo uri = new UserRegisterInfo() ;
			uri.setUSER_ID(userInfoList.get(i).getUSER_ID());
			log.functionLog("查询用户类型和状态信息");
			List<UserRegisterInfo> uris = urs.findUserRegisterInfoByUserID(uri);
			String type = uris.get(0).getUSER_TYPE();
			String status = uris.get(0).getUSER_STATUS();

			if(status.equals("0"))
			{
				userInfoList.get(i).setUSER_STATUS("正常");
			}else{
				userInfoList.get(i).setUSER_STATUS("注销");
			}

			if(type.equals("0"))
			{
				userInfoList.get(i).setUSER_TYPE("管理员");
			}else if(type.equals("1")){
				userInfoList.get(i).setUSER_TYPE("工作人员");
			}else if(type.equals("2")){
				userInfoList.get(i).setUSER_TYPE("用户");
			}

			if(userInfoList.get(i).getUSER_GENDER().equals("F"))
			{
				userInfoList.get(i).setUSER_GENDER("女");
			}else{
				userInfoList.get(i).setUSER_GENDER("男");
			}
		}

		result.setCode(200);
		result.setMsg("操作成功");
		result.setData(userInfoList);
		result.setSize(size);
		result.setCurrent(current);
		log.functionLog("查询用户数量");
		result.setTotal(uis.getAllUserInfos((current-1)*size,size,1).size());

		log.outPutLog(result);
		return result;
	}


	@ApiOperation(value="关键字查询用户信息", notes = "根据关键字查询用户信息",httpMethod = "POST")
	@ApiJsonModel({
			@ApiModelProperty(name = "key", value = "搜索关键字",required = true),
			@ApiModelProperty(name = "current", value = "当前页数",required = true),
			@ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
	})
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "查询成功"),
					@ApiResponse(code = 103, message = "没有数据"),
			})
	@PostMapping("/findUserInfoByKey")
	@ResponseBody
	public Result findUserInfoByKey(@RequestBody Map map) {
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
		}
		else{
			key = map.get("key").toString();
			current = Integer.valueOf(map.get("current").toString());
			size = Integer.valueOf(map.get("size").toString());
		}

		log.functionLog("关键字查询用户信息");
		List<UserInfo> userInfoList = uis.findUserInfoByKey(key,key,key,key,key,(current-1)*size,size,0);
		if(userInfoList.size()!=0)
		{
			for (int i = 0; i < userInfoList.size(); i++) {
				UserRegisterInfo uri = new UserRegisterInfo() ;
				uri.setUSER_ID(userInfoList.get(i).getUSER_ID());
				log.functionLog("查询用户类型");
				String type = urs.findUserRegisterInfoByUserID(uri).get(0).getUSER_TYPE();
				log.functionLog("查询用户状态");
				String status = urs.findUserRegisterInfoByUserID(uri).get(0).getUSER_STATUS();

				if(status.equals("0"))
				{
					userInfoList.get(i).setUSER_STATUS("正常");
				}else{
					userInfoList.get(i).setUSER_STATUS("注销");
				}

				if(type.equals("0"))
				{
					userInfoList.get(i).setUSER_TYPE("管理员");
				}else if(type.equals("1")){
					userInfoList.get(i).setUSER_TYPE("工作人员");
				}else if(type.equals("2")){
					userInfoList.get(i).setUSER_TYPE("用户");
				}

				if(userInfoList.get(i).getUSER_GENDER().equals("F"))
				{
					userInfoList.get(i).setUSER_GENDER("女");
				}else{
					userInfoList.get(i).setUSER_GENDER("男");
				}
			}

			result.setCode(200);
			result.setMsg("操作成功");
			result.setData(userInfoList);
			result.setSize(size);
			result.setCurrent(current);
			result.setTotal(uis.findUserInfoByKey(key,key,key,key,key,(current-1)*size,size,1).size());
		}
		else {
			result.setCode(103);
			result.setMsg("没有数据");
		}

		log.outPutLog(result);
		return result;
	}
}
