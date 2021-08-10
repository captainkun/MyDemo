package com.jike.demo.tim.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jike.demo.tim.pojo.TIMMsgElement;
import lombok.Builder;
import lombok.Data;

/**
 * 消息元素构建器
 * <p><a href="https://cloud.tencent.com/document/product/269/2720">消息格式描述文档</a></p>
 * @author qu.kun
 * @date 2021/7/9
 */
public class TIMMsgElementBuilder {
    /**
     * 文本消息类型
     */
    private static final String MSG_TYPE_TEXT = "TIMTextElem";
    /**
     * 自定义消息类型
     */
    private static final String MSG_TYPE_CUSTOM = "TIMCustomElem";

    /**
     * 构建文本消息元素
     * @param text 文本
     * @return 消息元素
     */
    public static TIMMsgElement buildTextElem(String text) {
        TextElem textElem = TextElem.builder()
                .Text(text)
                .build();

        return TIMMsgElement.builder()
                .MsgType(MSG_TYPE_TEXT)
                .MsgContent(textElem)
                .build();
    }

    /**
     * 构建自定义消息元素
     * @param data 自定义消息数据
     * @param desc 自定义消息描述信息
     * @param url 扩展字段。当接收方为 iOS 系统且应用处在后台时，此字段作为 APNs 请求包 Payloads 中的 Ext 键值下发，Ext 的协议格式由业务方确定，APNs 只做透传。
     * @return 消息元素
     */
    public static TIMMsgElement buildCustomElem(Object data, String desc, String url) {
        CustomElem customElem = CustomElem.builder()
                .Data(JSONObject.toJSONString(data))
                .Desc(desc)
                .Ext(url)
                .build();

        return TIMMsgElement.builder()
                .MsgType(MSG_TYPE_CUSTOM)
                .MsgContent(customElem)
                .build();
    }

    /**
     * 构建自定义消息元素
     * @param data 自定义消息数据
     * @param desc 自定义消息描述信息
     * @return 消息元素
     */
    public static TIMMsgElement buildCustomElem(Object data, String desc) {
        CustomElem customElem = CustomElem.builder()
                .Data(JSONObject.toJSONString(data))
                .Desc(desc)
                .build();

        return TIMMsgElement.builder()
                .MsgType(MSG_TYPE_CUSTOM)
                .MsgContent(customElem)
                .build();
    }


    @Data
    @Builder
    private static class TextElem{
        @JsonProperty("Text")
        private String Text;
    }

    @Data
    @Builder
    private static class CustomElem {
        @JsonProperty("Data")
        private Object Data;
        @JsonProperty("Desc")
        private String Desc;
        @JsonProperty("Ext")
        private String Ext;

    }
}
