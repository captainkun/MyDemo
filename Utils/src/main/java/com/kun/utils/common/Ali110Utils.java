package com.kun.utils.common;

import com.aliyun.openservices.cms.CMSClient;
import com.aliyun.openservices.cms.exception.CMSException;
import com.aliyun.openservices.cms.metric.MetricAttribute;
import com.aliyun.openservices.cms.model.CustomMetric;
import com.aliyun.openservices.cms.request.CustomMetricUploadRequest;
import com.aliyun.openservices.cms.response.CustomMetricUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 阿里云自定义监控-服务启动状态数据上报工具类
 * <p>规则：原始值(CustomMetric.TYPE_VALUE)为1，则服务正常启动; 为0则为启动失败，阿里发送服务告警邮件和回调</p>
 * @author qu.kun
 * @date 2021/5/8
 */
public class Ali110Utils {
    private static final Logger logger = LoggerFactory.getLogger(Ali110Utils.class);

    public static final String ENDPOINT = "https://metrichub-cms-cn-hangzhou.aliyuncs.com";
    public static final String ACC_KEY = "LTAIEdT6PpRuJEmp";
    public static final String SECRET = "NsmtPlJLeNi3rD6sz1cRAoLqadU8Zm";
    private static ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();

    public static void runAliCloudCMSClient(boolean isRun) {
        final CMSClient cmsClient = new CMSClient(ENDPOINT, ACC_KEY, SECRET);
        final float value = isRun ? 1f : 0f;
        String hostAddress = null;
        String hostName = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAddress = addr.getHostAddress();
            hostName = addr.getHostName();
        } catch (UnknownHostException e) {
            logger.error("获取当前主机IP或主机名失败", e);
        }
        String finalHostAddress = hostAddress;
        String finalHostName = hostName;

        //异步提交事件，初始化定时任务，每秒执行Run方法批量上报事件。可以根据自己的情况调整上报间隔。
        schedule.scheduleAtFixedRate(() -> upload(finalHostName, cmsClient, value, finalHostAddress), 0, 1, TimeUnit.MINUTES);
    }

    private static void upload(String appName, CMSClient cmsClient, float value, String finalHostAddress) {
        CustomMetricUploadRequest request = CustomMetricUploadRequest.builder()
                .append(CustomMetric.builder()
                        // 指标名称
                        .setMetricName("applicationRunMetric")
                        // 设置应用分组ID
                        .setGroupId(102L)
                        .setTime(new Date())
                        // 类型为原始值
                        .setType(CustomMetric.TYPE_VALUE)
                        // 原始值，key只能是该value，不能自定义
                        .appendValue(MetricAttribute.VALUE, value)
                        // 添加维度
                        .appendDimension("applicationName", appName)
                        .appendDimension("ip", finalHostAddress)
                        .build())
                .build();

        try {
            CustomMetricUploadResponse response = cmsClient.putCustomMetric(request);
            if (!"200".equals(response.getCode())) {
                logger.warn("上报自定义监控错误：msg: {}, rid: {}", response.getMessage(), response.getRequestId());
            }
        } catch (CMSException e) {
            logger.error("上报自定义监控异常", e);
        }
    }

}
