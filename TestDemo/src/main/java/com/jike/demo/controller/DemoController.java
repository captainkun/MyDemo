package com.jike.demo.controller;

import com.jike.demo.api.FeignClientApi;
import com.jike.demo.entity.Student;
import com.jike.demo.service.IDemoService;
import com.kun.utils.common.WechatRobotMsg;
import me.zhyd.oauth.AuthRequestBuilder;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

        AuthRequest authRequest = AuthRequestBuilder.builder()
                .source("gitee")
                .authConfig((source) -> {
                    // 通过 source 动态获取 AuthConfig
                    // 此处可以灵活的从 sql 中取配置也可以从配置文件中取配置
                    return AuthConfig.builder()
                            .clientId("clientId")
                            .clientSecret("clientSecret")
                            .redirectUri("redirectUri")
                            .build();
                })
                .build();
        authRequest.login(AuthCallback.builder().oauth_token("asdf").build());
        System.out.println(authRequest.authorize(AuthStateUtils.createState()));
    }

    @GetMapping("feign")
//    @AvoidReCommit(timeout = 5)
    public Object feignTest(String name) {
//        return feignClientApi.getStudentInfo(name);
        return "调用服务成功";
    }


    @GetMapping("threadTest")
    public void threadTest() {
        demoService.threadTest();
    }

    @GetMapping("noResult")
    public void noResult() {
        System.out.println(22292);
    }

    @GetMapping("result")
    public Object result() {
        return new Student(){{setStudentName("老司机");}};
    }

    public static void main(String[] args) {
        String url = "http://localhost:1024/result";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity requestEntity = new HttpEntity("{\"age\":12}", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, new HashMap<>());
        System.out.println("调用接口返回结果:"+ responseEntity);
        System.out.println("调用接口返回状态是否200:"+ responseEntity.getStatusCode().is2xxSuccessful());
        System.out.println("返回结果："+ requestEntity.getBody());
        System.out.println("================================");
        String url2 = "http://127.0.0.1:1024/noResult";
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(url2, HttpMethod.GET, requestEntity, String.class, new HashMap<>());
        System.out.println("调用接口返回结果:"+ responseEntity2);
        System.out.println("调用接口返回状态是否200:"+ responseEntity2.getStatusCode().is2xxSuccessful());
        System.out.println("返回结果："+ responseEntity2.getBody());

    }

    @PostMapping("upload")
    public void upload(MultipartFile file) {

        WechatRobotMsg wechatRobotMsg = WechatRobotMsg.getInstance();
        System.out.println(wechatRobotMsg);

    }

    @GetMapping(value = "/hello/{name}")
    public String apiHello(@PathVariable String name) {
        return demoService.sayHello(name);
    }

}

