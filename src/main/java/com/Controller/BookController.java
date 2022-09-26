package com.Controller;

import com.Bean.Author.AuthorInfo;
import com.Bean.Book.BookInfos;
import com.Bean.Borrowed.BorrowedInfo;
import com.Bean.Press.PressInfo;
import com.Bean.Result;
import com.Bean.SqlCus;
import com.Bean.User.UserRegisterInfo;
import com.Config.ApiJsonModel;
import com.Log.Log;
import com.Service.AuthorService.AuthorService;
import com.Service.BookService.BookService;
import com.Service.BorrowedService.BorrowedService;
import com.Service.PressService.PressService;
import com.Service.UserService.UserRegisterService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bs;
    @Autowired
    private AuthorService as;
    @Autowired
    private PressService ps;
    @Autowired
    private BorrowedService bds;
    @Autowired
    private UserRegisterService urs;

    @ApiOperation(value="查询所有图书信息", notes = "查询所有图书信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "current", value = "当前页数",required = true),
            @ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "查询成功"),
                    @ApiResponse(code = 103, message = "没有数据"),
            })
    @PostMapping("/getBookInfos")
    @ResponseBody
    public Result getBookInfos(@RequestBody Map map)
    {
        Log log = new Log();
        log.inputLogMap(map);
        Result result =new Result();
        int current = Integer.valueOf(map.get("current").toString());
        int size = Integer.valueOf(map.get("size").toString());

        log.functionLog("分页查询所有图书信息");
        List<BookInfos> bks = bs.findAllBookInfos((current-1)*size,size,0);
        if(bks.size()!=0)
        {
            log.functionLog("获取作者名称和出版社名称");
            for (int i = 0; i < bks.size(); i++) {

                List<AuthorInfo> ai = as.findAllAuthorInfoByAuthorID(bks.get(i).getAUTHOR_ID());
                if(ai.size()!=0){
                    bks.get(i).setAUTHOR_NAME(ai.get(0).getAUTHOR_NAME());
                }
                List<PressInfo> pi = ps.findAllPressInfoByID(bks.get(i).getPRESS_ID());
                if(pi.size()!=0){
                    bks.get(i).setPRESS_NAME(pi.get(0).getPRESS_NAME());
                }

                if(bks.get(i).getBOOK_STATUS().equals("0"))
                {
                    bks.get(i).setBOOK_STATUS("有效");
                }else if(bks.get(i).getBOOK_STATUS().equals("1"))
                {
                    bks.get(i).setBOOK_STATUS("失效");
                }
            }

            result.setCode(200);
            result.setMsg("操作成功");
            result.setData(bks);
            result.setSize(size);
            result.setCurrent(current);

            log.functionLog("查询所有图书信息数量");
            result.setTotal(bs.findAllBookInfos((current-1)*size,size,1).size());
        }
        else {
            result.setCode(103);
            result.setMsg("没有数据!");
        }

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="关键字查询图书信息", notes = "根据关键字查询图书信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "key", value = "搜索关键字",required = true),
            @ApiModelProperty(name = "current", value = "当前页数",required = true),
            @ApiModelProperty(name = "size", value = "每页显示的页码数量",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "新增成功"),
                    @ApiResponse(code = 103, message = "没有数据"),
            })
    @PostMapping("/findBookInfosByKey")
    @ResponseBody
    public Result findBookInfosByKey(@RequestBody Map map)
    {
        Log log = new Log();
        log.inputLogMap(map);
        String key = "";
        int current = 0;
        int size = 0;
        Result result =new Result();
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

        log.functionLog("根据作者名称获取作者编号");
        List<AuthorInfo> ais = as.findAllAuthorInfoByIDOrName(key,(current-1)*size,size,0);
        if(ais.size()!=0){
            key = ais.get(0).getAUTHOR_ID();
        }

        log.functionLog("根据出版社名称获取出版社编号");
        List<PressInfo> pis = ps.findAllPressInfoByIDOrName(key,(current-1)*size,size,0);
        if(pis.size()!=0){
            key = pis.get(0).getPRESS_ID();
        }

        log.functionLog("关键字分页查询图书信息");
        List<BookInfos> bks = bs.findBookInfosByKey(key,(current-1)*size,size,1);
        if(bks.size()!=0)
        {
            log.functionLog("获取作者名称和出版社名称");
            for (int i = 0; i < bks.size(); i++) {
                List<AuthorInfo> ai = as.findAllAuthorInfoByAuthorID(bks.get(i).getAUTHOR_ID());
                if(ai.size()!=0){
                    bks.get(i).setAUTHOR_NAME(ai.get(0).getAUTHOR_NAME());
                }
                List<PressInfo> pi = ps.findAllPressInfoByID(bks.get(i).getPRESS_ID());
                if(pi.size()!=0){
                    bks.get(i).setPRESS_NAME(pi.get(0).getPRESS_NAME());
                }

                if(bks.get(i).getBOOK_STATUS().equals("0"))
                {
                    bks.get(i).setBOOK_STATUS("有效");
                }else if(bks.get(i).getBOOK_STATUS().equals("1"))
                {
                    bks.get(i).setBOOK_STATUS("失效");
                }
            }

            result.setCode(200);
            result.setMsg("操作成功");
            result.setData(bks);
            result.setSize(size);
            result.setCurrent(current);
            log.functionLog("关键字查询图书信息数量");
            result.setTotal(bs.findBookInfosByKey(key,(current-1)*size,size,0).size());
        }
        else {
            result.setCode(103);
            result.setMsg("没有数据!");
        }

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="删除图书信息", notes = "根据图书编号删除图书信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookID", value = "图书编号",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "操作成功"),
            })
    @PostMapping("/deleteBookInfo")
    @ResponseBody
    public Result deleteBookInfo(@RequestBody Map map,@RequestParam(value = "bookID",required = false) String bookID1){
        Log log = new Log();
        Result result =new Result();
        String bookID = "";
        if(map.size()!=0)
        {
            bookID = ((Map)map.get("params")).get("book_ID").toString();
            log.inputLogMap(map);
        }
        else{
            bookID = bookID1;
            List list = new ArrayList();
            list.add(bookID);
            log.inputLogList(list);
        }

        log.functionLog("根据图书编号删除图书信息");
        bs.deleteBookInfo(bookID);

        result.setCode(200);
        result.setMsg("操作成功");

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="新增图书信息", notes = "录入新的图书信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "borrowedID", value = "图书编号",required = true),
            @ApiModelProperty(name = "bookName", value = "图书名称",required = true),
            @ApiModelProperty(name = "bookTypeID", value = "图书类型编号",required = true),
            @ApiModelProperty(name = "authorID", value = "作者编号",required = true),
            @ApiModelProperty(name = "pressID", value = "出版社编号",required = true),
            @ApiModelProperty(name = "borrowedNumber", value = "借阅数量",required = true,dataType = "int"),
            @ApiModelProperty(name = "remainingNumber", value = "剩余数量",required = true,dataType = "int"),
            @ApiModelProperty(name = "bookStatus", value = "图书状态",required = true),

    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "新增成功"),
                    @ApiResponse(code = 104, message = "图书信息已存在"),
                    @ApiResponse(code = 105, message = "作者信息不存在，请重新填写!"),
                    @ApiResponse(code = 105, message = "出版社信息不存在，请重新填写!"),
            })
    @PostMapping("/insertBookInfo")
    @ResponseBody
    public Result insertBookInfo(@RequestBody Map map){
        Log log = new Log();
        log.inputLogMap(map);
        BookInfos bi = JSON.parseObject(JSON.toJSONString(map), BookInfos.class);
        Result result =new Result();

        log.functionLog("查询图书信息是否已存在");
        List<BookInfos> bks = bs.findBookInfoByKey(bi.getBOOK_ID());
        if(bks.size()!=0){
            result.setCode(104);
            result.setMsg("图书信息已存在!");
            return result;
        }

        log.functionLog("查询图书作者信息是否存在");
        List<AuthorInfo> ai = as.findAllAuthorInfoByAuthorID(bi.getAUTHOR_ID());
        if(ai.size()==0){
            result.setCode(105);
            result.setMsg("作者信息不存在，请重新填写!");
            return result;
        }

        log.functionLog("查询出版社信息是否存在");
        List<PressInfo> pi = ps.findAllPressInfoByID(bi.getPRESS_ID());
        if(pi.size()==0){
            result.setCode(106);
            result.setMsg("出版社信息不存在，请重新填写!");
            return result;
        }

        log.functionLog("新增图书信息");
        bs.insertBookInfo(bi);
        result.setCode(200);
        result.setMsg("新增成功");

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="更新图书信息", notes = "通过输入的信息更新图书信息",httpMethod = "POST")
    @ApiJsonModel({
            @ApiModelProperty(name = "borrowedID", value = "图书编号",required = true),
            @ApiModelProperty(name = "bookName", value = "图书名称",required = true),
            @ApiModelProperty(name = "bookTypeID", value = "图书类型编号",required = true),
            @ApiModelProperty(name = "authorID", value = "作者编号",required = true),
            @ApiModelProperty(name = "pressID", value = "出版社编号",required = true),
            @ApiModelProperty(name = "borrowedNumber", value = "借阅数量",required = true,dataType = "int"),
            @ApiModelProperty(name = "remainingNumber", value = "剩余数量",required = true,dataType = "int"),
            @ApiModelProperty(name = "bookStatus", value = "图书状态",required = true),

    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "新增成功"),
                    @ApiResponse(code = 104, message = "图书信息已存在"),
                    @ApiResponse(code = 105, message = "作者信息不存在，请重新填写!"),
                    @ApiResponse(code = 105, message = "出版社信息不存在，请重新填写!"),
            })
    @PostMapping("/updateBookInfo")
    @ResponseBody
    public Result updateBookInfo(@RequestBody Map map){
        Log log = new Log();
        log.inputLogMap(map);
        BookInfos bi = JSON.parseObject(JSON.toJSONString(map), BookInfos.class);
        Result result =new Result();

        log.functionLog("查询图书作者信息是否存在");
        List<AuthorInfo> ai = as.findAllAuthorInfoByAuthorID(bi.getAUTHOR_ID());
        if(ai.size()==0){
            result.setCode(105);
            result.setMsg("作者信息不存在，请重新填写!");
            return result;
        }

        log.functionLog("查询图书出版社信息是否存在");
        List<PressInfo> pi = ps.findAllPressInfoByID(bi.getPRESS_ID());
        if(pi.size()==0){
            result.setCode(106);
            result.setMsg("出版社信息不存在，请重新填写!");
            return result;
        }

        log.functionLog("更新图书信息");
        bs.updateBookInfo(bi);
        result.setCode(200);
        result.setMsg("操作成功");

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="借阅", notes = "员工选择图书进行借阅",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookID", value = "图书编号",required = true),
            @ApiImplicitParam(name = "userID", value = "借书者编号",required = true),
            @ApiImplicitParam(name = "num", value = "借阅数量",required = true),
            @ApiImplicitParam(name = "borrowedNumber", value = "书籍借阅数量",required = true),
            @ApiImplicitParam(name = "remainingNumber", value = "书籍剩余数量",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "操作成功"),
                    @ApiResponse(code = 116, message = "用户ID不存在"),
                    @ApiResponse(code = 117, message = "用户已被注销"),
                    @ApiResponse(code = 115, message = "用户有未还的书籍，不能进行借阅"),
                    @ApiResponse(code = 118, message = "用户有逾期的书籍，不能进行借阅"),
                    @ApiResponse(code = 112, message = "借阅数量大于存书数量"),
                    @ApiResponse(code = 111, message = "该图书已没有存书"),
            })
    @PostMapping("/borrowedBook")
    @ResponseBody
    public Result borrowedBook(@RequestBody Map map,@RequestParam(value = "bookID",required = false) String bookID1,
                               @RequestParam(value = "userID",required = false) String userID1,
                               @RequestParam(value = "num",required = false) Integer num1,
                               @RequestParam(value = "borrowedNumber",required = false) Integer borrowedNumber1,
                               @RequestParam(value = "remainingNumber",required = false) Integer remainingNumber1)
    {
        Log log = new Log();
        Result result =new Result();

        int num = 0;
        String bookID = "";
        String userID ="";
        int borrowedNumber = 0;
        int remainingNumber = 0;

        if(map.size()!=0){
            log.inputLogMap(map);
            Map bookFormMap = (Map)((Map)map.get("params")).get("updateForm");
            num = Integer.valueOf(((Map)map.get("params")).get("num").toString());
            bookID = bookFormMap.get("bookID").toString();
            userID = ((Map)map.get("params")).get("user_ID").toString();
            borrowedNumber = Integer.valueOf(bookFormMap.get("borrowedNumber").toString());
            remainingNumber = Integer.valueOf(bookFormMap.get("remainingNumber").toString());
        }
        else {
            num = num1;
            bookID = bookID1;
            userID = userID1;
            borrowedNumber = borrowedNumber1;
            remainingNumber = remainingNumber1;
            List list = new ArrayList();
            list.add(num);
            list.add(bookID);
            list.add(userID);
            list.add(borrowedNumber);
            list.add(remainingNumber);
            log.inputLogList(list);
        }

        UserRegisterInfo uri = new UserRegisterInfo();
        uri.setUSER_ID(userID);
        log.functionLog("查询用户ID是否存在");
        if(urs.findUserRegisterInfoByUserID(uri).size()==0)
        {
            result.setCode(116);
            result.setMsg("用户ID不存在");
        }
        else
        {
            log.functionLog("查询用户ID是否被注销");
            if(!urs.findUserRegisterInfoByUserID(uri).get(0).getUSER_STATUS().equals("0"))
            {
                result.setCode(117);
                result.setMsg("用户已被注销");
            }
            else{
                log.functionLog("查询用户借阅情况");
                List<BorrowedInfo> bis = bds.findAllBorrowedInfosByUserID(userID,0,0,2);
                if(bis.size()!=0)
                {
                    if(bis.get(0).getBORROWED_STATUS().equals("0"))
                    {
                        result.setCode(115);
                        result.setMsg("用户有未还的书籍，不能进行借阅");
                    }else if(bis.get(0).getBORROWED_STATUS().equals("1"))
                    {
                        result.setCode(118);
                        result.setMsg("用户有逾期的书籍，不能进行借阅");
                    }
                }
                else
                {
                    if(remainingNumber==0){
                        result.setCode(111);
                        result.setMsg("该图书已没有存书");
                    }else{
                        if(num>remainingNumber){
                            result.setCode(112);
                            result.setMsg("借阅数量大于存书数量");
                        }
                        else{
                            log.functionLog("更新图书信息");
                            bs.updateBookNumber(bookID,borrowedNumber+num,remainingNumber-num);
                            BorrowedInfo bi = new BorrowedInfo();

                            Date day=new Date();
                            SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
                            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

                            Calendar c = Calendar.getInstance();
                            c.add(Calendar.DAY_OF_MONTH, 7);

                            bi.setBORROWED_ID(df1.format(day));
                            bi.setUSER_ID(userID);
                            bi.setBOOK_ID(bookID);
                            bi.setBORROWED_NUMBER(num);
                            bi.setBORROWED_START_DATE(df2.format(day));
                            bi.setBORROWED_END_DATE(df2.format(c.getTime()));
                            bi.setBORROWED_STATUS("0");

                            log.functionLog("添加借阅信息");
                            bds.insertBorrowedInfo(bi);

                            result.setCode(200);
                            result.setMsg("借阅成功");
                        }
                    }
                }
            }
        }

        log.outPutLog(result);
        return result;
    }

    @ApiOperation(value="借阅", notes = "员工选择图书进行借阅",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookID", value = "图书编号",required = true),
            @ApiImplicitParam(name = "userID", value = "借书者编号",required = true),
            @ApiImplicitParam(name = "num", value = "借阅数量",required = true),
            @ApiImplicitParam(name = "borrowedNumber", value = "书籍借阅数量",required = true),
            @ApiImplicitParam(name = "remainingNumber", value = "书籍剩余数量",required = true),
    })
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "操作成功"),
                    @ApiResponse(code = 112, message = "用户没有借过这本书籍"),
                    @ApiResponse(code = 113, message = "用户还有？本书没有还"),
                    @ApiResponse(code = 114, message = "用户多换了？本书"),
            })
    @PostMapping("/returnBook")
    @ResponseBody
    public Result returnBook(@RequestBody Map map,@RequestParam(value = "bookID",required = false) String bookID1,
                             @RequestParam(value = "userID",required = false) String userID1,
                             @RequestParam(value = "num",required = false) Integer num1,
                             @RequestParam(value = "borrowedNumber",required = false) Integer borrowedNumber1,
                             @RequestParam(value = "remainingNumber",required = false) Integer remainingNumber1)
    {
        Log log = new Log();
        int num = 0;
        String bookID = "";
        String userID ="";
        int borrowedNumber = 0;
        int remainingNumber = 0;

        if(map.size()!=0){
            Map bookFormMap = (Map)((Map)map.get("params")).get("updateForm");
            num = Integer.valueOf(((Map)map.get("params")).get("num").toString());
            bookID = bookFormMap.get("bookID").toString();
            userID = ((Map)map.get("params")).get("user_ID").toString();
            borrowedNumber = Integer.valueOf(bookFormMap.get("borrowedNumber").toString());
            remainingNumber = Integer.valueOf(bookFormMap.get("remainingNumber").toString());
            log.inputLogMap(map);
        }
        else {
            num = num1;
            bookID = bookID1;
            userID = userID1;
            borrowedNumber = borrowedNumber1;
            remainingNumber = remainingNumber1;

            List list = new ArrayList();
            list.add(num);
            list.add(bookID);
            list.add(userID);
            list.add(borrowedNumber);
            list.add(remainingNumber);
            log.inputLogList(list);
        }

        Result result = new Result();
        log.functionLog("查询用户借阅情况");
        List<BorrowedInfo> bi = bds.findBorrowedInfosByUserIDAndBorrowedIDAndStatus(userID,bookID,"0");
        if (bi.size()==0)
        {
            result.setCode(112);
            result.setMsg("用户没有借过这本书籍");
        }
        else
        {
            if(bi.get(0).getBORROWED_NUMBER()>num){
                result.setCode(113);
                result.setMsg("用户还有"+ (bi.get(0).getBORROWED_NUMBER()-num) +"本书没有还");
            }
            else if(bi.get(0).getBORROWED_NUMBER()<num){
                result.setCode(114);
                result.setMsg("用户多换了"+ (num-bi.get(0).getBORROWED_NUMBER()) +"本书");
            }
            else
            {
                log.functionLog("更新书籍信息");
                bs.updateBookNumber(bookID,borrowedNumber-num,remainingNumber+num);
                log.functionLog("更新借阅信息");
                bds.updateBorrowedInfosStatus(bi.get(0).getBORROWED_ID(),"2");
                result.setCode(200);
                result.setMsg("还书完成");
            }
        }

        log.outPutLog(result);
        return result;
    }
}