package com.Controller;

import com.Bean.Press.PressInfo;
import com.Bean.Result;
import com.Config.ApiJsonModel;
import com.Log.Log;
import com.Service.PressService.PressService;
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
@RequestMapping("/press")
public class PressController {
    @Autowired
    private PressService ps;

    @ApiOperation(value="查询所有出版社信息", notes = "查询所有出版社信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "current", value = "当前页数",required = true),
            @ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "查询成功"),
                    @ApiResponse(code = 103, message = "没有数据"),
            })
    @PostMapping("/getPressInfos")
    @ResponseBody
    public Result getPressInfos(@RequestBody Map map)
    {
        Log log = new Log();
        log.inputLogMap(map);
        int current = Integer.valueOf(map.get("current").toString());
        int size = Integer.valueOf(map.get("size").toString());
        Result result =new Result();

        log.functionLog("分页查询出版社信息");
        List<PressInfo> ai = ps.findAllPressInfos((current-1)*size,size,0);
        if(ai.size()!=0) {
            for (int i = 0; i < ai.size(); i++) {
                if(ai.get(i).getPRESS_STATUS().equals("0"))
                {
                    ai.get(i).setPRESS_STATUS("有效");
                }
                else if(ai.get(i).getPRESS_STATUS().equals("1"))
                {
                    ai.get(i).setPRESS_STATUS("失效");
                }
            }

            result.setCode(200);
            result.setMsg("操作成功");
            result.setData(ai);
            result.setSize(size);
            result.setCurrent(current);
            log.functionLog("查询出版社数量");
            result.setTotal(ps.findAllPressInfos((current-1)*size,size,1).size());
        }
        else {
            result.setCode(103);
            result.setMsg("没有数据!");
        }

        log.outPutLog(result);
        return result;
    }


    @ApiOperation(value="新增出版社信息", notes = "录入新的出版社信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pressID", value = "出版社编号",required = true),
            @ApiImplicitParam(name = "pressName", value = "出版社名称",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "新增成功"),
                    @ApiResponse(code = 109, message = "出版社信息已存在"),
            })
    @PostMapping("/insertPressInfo")
    @ResponseBody
    public Result insertPressInfo(@RequestBody Map map,@RequestParam(value = "pressID",required = false) String pressID,
                                  @RequestParam(value = "pressName",required = false) String pressName)
    {
        Log log = new Log();
        PressInfo pi = new PressInfo();
        if(map.size()!=0){
            pi = JSON.parseObject(JSON.toJSONString(map),PressInfo.class);
            log.inputLogMap(map);
        }
        else{
            pi.setPRESS_ID(pressID);
            pi.setPRESS_NAME(pressName);
            log.inputLogOject(pi);
        }

        Result result =new Result();
        log.functionLog("查询出版社信息是否存在");
        List<PressInfo> pis = ps.findAllPressInfoByID(pi.getPRESS_ID());
        if(pis.size()!=0){
            result.setCode(109);
            result.setMsg("出版社信息已存在!");
            return result;
        }

        ps.insertPressInfo(pi);
        result.setCode(200);
        result.setMsg("新增成功");
        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="修改出版社信息", notes = "根据出版社编号修改出版社信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "pressID", value = "出版社编号",required = true),
            @ApiModelProperty(name = "pressName", value = "出版社名称",required = true),
            @ApiModelProperty(name = "pressStatus", value = "状态",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "修改成功"),
            })
    @PostMapping("/updatePressInfo")
    @ResponseBody
    public Result updatePressInfo(@RequestBody Map map)
    {
        Log log = new Log();
        log.inputLogMap(map);
        Result result =new Result();

        PressInfo pi = JSON.parseObject(JSON.toJSONString(map),PressInfo.class);
        log.functionLog("修改出版社信息");
        ps.updatePressInfo(pi);

        result.setCode(200);
        result.setMsg("操作成功");
        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="关键字查询出版社信息", notes = "根据出关键字查询出版社信息",httpMethod = "POST")
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
    @PostMapping("/findPressInfosByKey")
    @ResponseBody
    public Result findPressInfosByKey(@RequestBody Map map)
    {
        Log log = new Log();
        Result result =new Result();
        String key = "";
        int current = 0;
        int size = 0;

        log.inputLogMap(map);
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

        log.functionLog("根据出关键字分页查询出版社信息");
        List<PressInfo> pi = ps.findAllPressInfoByIDOrName(key,(current-1)*size,size,0);
        if(pi.size()!=0)
        {
            result.setCode(200);
            result.setMsg("操作成功");
            result.setData(pi);
            result.setSize(size);
            result.setCurrent(current);
            log.functionLog("根据出关键字查询出版社信息数量");
            result.setTotal(ps.findAllPressInfoByIDOrName(key,(current-1)*size,size,1).size());
        }
        else {
            result.setCode(103);
            result.setMsg("没有数据!");
        }

        log.outPutLog(result);
        return result;
    }
}