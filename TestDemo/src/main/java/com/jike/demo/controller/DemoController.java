package com.jike.demo.controller;

import com.jike.demo.api.FeignClientApi;
import com.jike.demo.entity.Student;
import com.jike.demo.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author qukun
 * @date 2019/3/1
 */
@RestController
public class DemoController {
    @Autowired
    private IDemoService demoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private FeignClientApi feignClientApi;

    @GetMapping("get")
    public void getSth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String serverName = request.getServerName();
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("MyDIYHeader","测试成功");
        response.setHeader("refresh", "3;url='http://www.baidu.com'");
    }

    @GetMapping("feign")
//    @AvoidReCommit(timeout = 5)
    public Object feignTest(String name) {
        return feignClientApi.getStudentInfo(name);
    }

    @GetMapping("beetl")
    public Object beetl() {
        return demoService.beetl();
    }


}

