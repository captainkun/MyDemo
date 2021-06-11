package com.qk.admin.notify;

import com.alibaba.fastjson.JSONObject;
import com.kun.utils.common.WechatRobotMsg;
import com.qk.admin.pojo.WechatRobotMsgInfo;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 企业微信机器人告警
 * @author qu.kun
 * @date 2021/3/30
 */
public class CompanyWechatNotifier extends AbstractStatusChangeNotifier {
    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private RestTemplate restTemplate;

    private static final String ROBOT_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=42960ed4-9d68-4649-a2b0-1307bf94b726";
    private static boolean IS_FIRST_RUN = true;

    public CompanyWechatNotifier(InstanceRepository repositpry) {
        super(repositpry);
        setIgnoreChanges(new String[]{"OFFLINE:UNKNOWN"});
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < 3; i++) {
            System.out.println("================" + i);
            WechatRobotMsg instance = WechatRobotMsg.getInstance();
            System.out.println(instance);

            String sb = "告警" + i;
            WechatRobotMsg.TextMsg textMsg = instance.getTextMsg(sb);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(ROBOT_URL, textMsg, String.class);
            System.out.println(stringResponseEntity.getBody());
        }


    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
//        if (IS_FIRST_RUN) {
//            IS_FIRST_RUN = false;
//            return Mono.empty();
//        }
        String serviceName = instance.getRegistration().getName();
        String serviceUrl = instance.getRegistration().getServiceUrl();
        String status = instance.getStatusInfo().getStatus();
        Map<String, Object> details = instance.getStatusInfo().getDetails();
        StringBuilder sb = new StringBuilder();
        sb.append("### SpringBootAdmin监控报警").append("\n");
        sb.append("环境：").append(env).append("\n");
        sb.append("服务名称：").append(serviceName).append("\n");
        sb.append("服务地址：").append(serviceUrl).append("\n");
        sb.append("状态：").append(status).append("\n");
        sb.append("> 详情：").append(JSONObject.toJSONString(details)).append("\n");

        WechatRobotMsgInfo wechatRobotMsgInfo = new WechatRobotMsgInfo();
        WechatRobotMsgInfo.Content robotContent = new WechatRobotMsgInfo.Content();
        robotContent.setContent(sb.toString());
        wechatRobotMsgInfo.setMarkdown(robotContent);

        return Mono.fromRunnable(() -> {
            restTemplate.postForEntity(ROBOT_URL, wechatRobotMsgInfo, String.class);
        });
    }





}
