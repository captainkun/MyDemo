package com.jike.demo.tim.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 发送单聊消息的数据封装
 * <p>
 *     <a href="https://cloud.tencent.com/document/product/269/2282">文档</a>
 * </p>
 * @author qu.kun
 * @date 2021/6/28
 */
@Data
@Builder
public class TIMMessage {
    /**
     * 消息是否同步到发送方 1 同步 2 不同步
     */
    @JsonProperty("SyncOtherMachine")
    private Integer SyncOtherMachine;
    @JsonProperty("From_Account")
    private String From_Account;
    @JsonProperty("To_Account")
    private String To_Account;
    @JsonProperty("MsgRandom")
    private Integer MsgRandom;
    /**
     * 消息元素，使用构造器构造 {@link com.yanwei.cloud.message.domain.im.TIMMsgElementBuilder}
     */
    @JsonProperty("MsgBody")
    private List<TIMMsgElement> MsgBody;
    @JsonProperty("CloudCustomData")
    private String CloudCustomData;
    /**
     * 禁止回调控制选项
     */
    @JsonProperty("ForbidCallbackControl")
    private List<String> ForbidCallbackControl;
}
