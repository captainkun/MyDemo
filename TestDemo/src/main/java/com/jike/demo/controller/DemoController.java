package com.jike.demo.controller;

import com.jike.demo.annotation.AvoidReCommit;
import com.jike.demo.entity.Student;
import com.jike.demo.service.IDemoService;
import com.jike.demo.service.impl.SonService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author qukun
 * @date 2019/3/1
 */
@RestController
public class DemoController {
    @Autowired
    private IDemoService demoService;

    @GetMapping("get")
    public void getSth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String serverName = request.getServerName();
        IOUtils.write(serverName,response.getOutputStream(), Charset.defaultCharset());
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("MyDIYHeader","测试成功");
        response.setHeader("refresh", "3;url='http://www.baidu.com'");
    }

    @PostMapping("test")
    @AvoidReCommit(timeout = 5000)
    public Object test(@RequestBody @Validated Student student) {
        try {
            System.out.println("进入Controller");
            System.out.println(student);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }


}

