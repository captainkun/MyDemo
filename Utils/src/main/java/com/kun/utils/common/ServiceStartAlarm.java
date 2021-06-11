package com.kun.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 服务启动告警
 * @author qu.kun
 * @date 2021/5/10
 */
public class ServiceStartAlarm {
    private static final Logger logger = LoggerFactory.getLogger(ServiceStartAlarm.class);

    public static void alarm2WechatRobot(Exception exception) {

        String hostAddress = null;
        String hostName = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAddress = addr.getHostAddress();
            hostName = addr.getHostName();
            System.out.println(hostAddress);
            System.out.println(hostName);
        } catch (UnknownHostException e) {
            logger.error("获取当前主机IP或主机名失败", e);
        }
        StackTraceElement[] stackTraceElement= exception.getStackTrace();
        String className = stackTraceElement[0].getClassName();
        System.out.println(className);
        System.out.println(stackTraceElement[stackTraceElement.length - 1].getClassName());




    }

}
