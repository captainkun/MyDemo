package com.jike.demo.tim.enums;

/**
 * 腾讯IM自定义消息类型
 * @author qu.kun
 * @date 2021/7/12
 */
public enum TIMMsgTypeEnum {
    /**
     * 系统消息回复
     */
    SYSTEM_REPLY,

    /**
     * 红包类型
     */
    RED_PACKET,
    /**
     * 红包领取类型
     */
    RED_PACKET_RECEIVE,
    /**
     * 红包失效
     */
    RED_PACKET_INVALID,

    /**
     * 单个商品员工价
     */
    EMPLOYEE_PRICE_SPU,
    /**
     * 单个商品员工价-申请成功后回复
     */
    EMPLOYEE_PRICE_SPU_REPLY,

    /**
     * 所有商品员工价
     */
    EMPLOYEE_PRICE,
    /**
     * 所有商品员工价-申请成功后回复
     */
    EMPLOYEE_PRICE_REPLY

}
