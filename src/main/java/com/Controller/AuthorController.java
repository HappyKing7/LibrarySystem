package com.Controller;

import com.Bean.Author.AuthorInfo;
import com.Bean.Press.PressInfo;
import com.Bean.Result;
import com.Bean.SqlCus;
import com.Config.ApiJsonModel;
import com.Log.Log;
import com.Service.AuthorService.AuthorService;
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
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService as;

    @ApiOperation(value="查询所有作者信息", notes = "查询所有作者信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "current", value = "当前页数",required = true),
            @ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "查询成功"),
                    @ApiResponse(code = 103, message = "没有数据"),
            })
    @PostMapping("/getAuthorInfos")
    @ResponseBody
    public Result getAuthorInfos(@RequestBody Map map)
    {
        Log log = new Log();
        log.functionLog("刷新借阅状态");
        int current = Integer.valueOf(map.get("current").toString());
        int size = Integer.valueOf(map.get("size").toString());
        Result result =new Result();

        log.functionLog("分页查询所有作者信息");
        List<AuthorInfo> ai = as.findAllAuthorInfos((current-1)*size,size,0);
        if(ai.size()!=0) {
            for (int i = 0; i < ai.size(); i++) {
                if(ai.get(i).getAUTHOR_STATUS().equals("0"))
                {
                    ai.get(i).setAUTHOR_STATUS("有效");
                }
                else if(ai.get(i).getAUTHOR_STATUS().equals("1"))
                {
                    ai.get(i).setAUTHOR_STATUS("失效");
                }
            }

            result.setCode(200);
            result.setMsg("操作成功");
            result.setData(ai);
            result.setSize(size);
            result.setCurrent(current);
            log.functionLog("查询所有作者信息数量");
            result.setTotal(as.findAllAuthorInfos((current-1)*size,size,1).size());
        }
        else {
            result.setCode(103);
            result.setMsg("没有数据!");
        }

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="新增作者信息", notes = "录入新的作者信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorID", value = "作者编号",required = true),
            @ApiImplicitParam(name = "authorName", value = "作者名称",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "新增成功"),
                    @ApiResponse(code = 109, message = "作者信息已存在"),
            })
    @PostMapping("/insertAuthorInfo")
    @ResponseBody
    public Result insertAuthorInfo(@RequestBody Map map,@RequestParam(value = "authorID",required = false) String authorID,
                                   @RequestParam(value = "authorName",required = false) String authorName)
    {
        Log log = new Log();
        AuthorInfo ai = new AuthorInfo();
        if(map.size()!=0){
            log.inputLogMap(map);
            ai = JSON.parseObject(JSON.toJSONString(map), AuthorInfo.class);
        }
        else{
            ai.setAUTHOR_ID(authorID);
            ai.setAUTHOR_NAME(authorName);
            log.inputLogOject(ai);
        }

        Result result =new Result();

        log.functionLog("校验作者信息是否存在");
        List<AuthorInfo> ais = as.findAllAuthorInfoByAuthorID(ai.getAUTHOR_ID());
        if(ais.size()!=0){
            result.setCode(109);
            result.setMsg("作者信息已存在!");
            log.outPutLog(result);
            return result;
        }

        log.functionLog("新增作者信息");
        as.insertAuthorInfo(ai);
        result.setCode(200);
        result.setMsg("新增成功");

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="修改作者信息", notes = "根据作者编号修改作者信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "authorID", value = "出版社编号",required = true),
            @ApiModelProperty(name = "authorName", value = "出版社名称",required = true),
            @ApiModelProperty(name = "authorStatus", value = "状态",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "修改成功"),
            })
    @RequestMapping("/updateAuthorInfo")
    @ResponseBody
    public Result updateAuthorInfo(@RequestBody Map map)
    {
        Log log = new Log();
        log.inputLogMap(map);
        Result result =new Result();

        AuthorInfo ai = JSON.parseObject(JSON.toJSONString(map),AuthorInfo.class);
        log.functionLog("修改作者信息");
        as.updateAuthorInfo(ai);

        result.setCode(200);
        result.setMsg("操作成功");
        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="关键字查询作者信息", notes = "根据关键字查询作者信息",httpMethod = "POST")
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
    @RequestMapping("/findAuthorInfosByKey")
    @ResponseBody
    public Result findAuthorInfosByKey(@RequestBody Map map)
    {
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

        log.functionLog("根据关键字分页查询作者信息");
        List<AuthorInfo> ais = as.findAllAuthorInfoByIDOrName(key,(current-1)*size,size,0);
        if(ais.size()!=0)
        {
            result.setCode(200);
            result.setMsg("操作成功");
            result.setData(ais);
            result.setSize(size);
            result.setCurrent(current);
            log.functionLog("根据关键字查询作者信息数量");
            result.setTotal(as.findAllAuthorInfoByIDOrName(key,(current-1)*size,size,1).size());
        }
        else {
            result.setCode(103);
            result.setMsg("没有数据!");
        }

        log.outPutLog(result);
        return result;
    }
}