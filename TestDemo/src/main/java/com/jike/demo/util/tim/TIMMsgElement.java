package com.jike.demo.util.tim;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 消息元素，请使用构造器构造 {@link TIMMsgElementBuilder}
 * @author qu.kun
 * @date 2021/7/9
 */
@Data
@Builder
public class TIMMsgElement {
    @JsonProperty("MsgType")
    private String MsgType;
    @JsonProperty("MsgContent")
    private Object MsgContent;
}
