package com.jike.demo.tim.constant;

/**
 * redis key 常量
 * @author qu.kun
 * @date 2021/7/8
 */
public class RedisKeyConstant {

    /**
     * 通讯消息redisKey前缀
     */
    public static final String IM_MSG_REDIS_PRE_KEY = "message:im:to_user_id:";
    /**
     * 已发送未读短信提醒的通讯消息redisKey前缀
     */
    public static final String HAD_SEND_IM_MSG_REDIS_PRE_KEY = "message:im:had_send_msg_to_userId:";
    /**
     * 用户咨询管家消息redisKey前缀
     */
    public static final String HAD_COUNSEL_MSG_REDIS_KEY = "message:im:counsel:had_msg:";
    /**
     * 预约短信听信
     */
    public static final String RESERVE_MSG_REDIS_KEY = "message:im:reserve_msg:user_id:";

}
