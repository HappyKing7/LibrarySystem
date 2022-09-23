package com.Controller;

import com.Bean.Result;
import com.Bean.SqlCus;
import com.Bean.User.UserLogin;
import com.Service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private testService ts;

}