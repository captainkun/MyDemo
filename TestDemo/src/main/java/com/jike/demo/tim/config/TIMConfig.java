package com.jike.demo.tim.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 腾讯IM配置项
 * 优先读取阿波罗配置的值，默认的值是个人测试应用的值，故可随意替换
 * @author qu.kun
 * @date 2021/6/25
 */
@Component
public class TIMConfig {
    public static Integer SDK_APP_ID = 1400528804;
    public static Integer EXPIRE_TIME = 604800;
    public static String SECRET_KEY = "1eabde0ef2cbad977feac09aea6c52300a5b662e25ee24b6fff55c760dc8967b";
    public static String ADMIN_ID = "administrator";

    /**
     * 腾讯云 SDKAppId，需要替换为您自己账号下的 SDKAppId。
     *
     * 进入腾讯云云通信[控制台](https://console.cloud.tencent.com/avc ) 创建应用，即可看到 SDKAppId，
     * 它是腾讯云用于区分客户的唯一标识。
     */
    @Value("${tencent.im.sdkAppId:1400528804}")
    public void setAppId(Integer sdkAppId){
        SDK_APP_ID = sdkAppId;
    }


    /**
     * 签名过期时间，建议不要设置的过短
     * <p>
     * 时间单位：秒
     * 默认时间：7 x 24 x 60 x 60 = 604800 = 7 天
     */
    @Value("${tencent.im.expireTime:604800}")
    public void setExpireTime(Integer expireTime) {
        EXPIRE_TIME = expireTime;
    }


    /**
     * 计算签名用的加密密钥，获取步骤如下：
     * <p>
     * step1. 进入腾讯云云通信[控制台](https://console.cloud.tencent.com/avc ) ，如果还没有应用就创建一个，
     * step2. 单击“应用配置”进入基础配置页面，并进一步找到“帐号体系集成”部分。
     * step3. 点击“查看密钥”按钮，就可以看到计算 UserSig 使用的加密的密钥了，请将其拷贝并复制到如下的变量中
     * <p>
     * 注意：该方案仅适用于调试Demo，正式上线前请将 UserSig 计算代码和密钥迁移到您的后台服务器上，以避免加密密钥泄露导致的流量盗用。
     * 文档：https://cloud.tencent.com/document/product/269/32688#Server
     */
    @Value("${tencent.im.secretkey:1eabde0ef2cbad977feac09aea6c52300a5b662e25ee24b6fff55c760dc8967b}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    /**
     * App 管理员帐号
     * @param adminId 管理账号
     */
    @Value("${tecent.im.adminId:administrator}")
    public void setAdminId(String adminId) {
        ADMIN_ID = adminId;
    }

}
