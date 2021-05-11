package com.kun.utils.common;


import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * 企业微信机器人消息发送工具类
 *
 * @author qu.kun
 * @date 2021/3/19
 */
public class WechatRobotMsg {

    private static WechatRobotMsg wechatRobotMsg;

    public static WechatRobotMsg getInstance() {
        if (Objects.isNull(wechatRobotMsg)) {
            return new WechatRobotMsg();
        }
        return wechatRobotMsg;
    }

    /**
     * 获取Markdown类的消息模板
     *
     * @param markdownContent markdown内容
     * @return markdown消息模板
     */
    public MarkdownMsg getMarkdownMsg(String markdownContent) {
        MarkdownMsg markdownMsg = new MarkdownMsg();
        markdownMsg.setMarkdown(new Content(markdownContent));
        return markdownMsg;
    }

    /**
     * 获取文本类消息模板
     *
     * @param textMsgContent 文本消息内容
     * @return 文本消息模板
     */
    public TextMsg getTextMsg(String textMsgContent) {
        TextMsg textMsg = new TextMsg();
        textMsg.setText(new Content(textMsgContent));
        return textMsg;
    }

    /**
     * 获取@用户名的文本消息模板
     *
     * @param textMsgContent       文本消息内容
     * @param mentionedList 需要@用户名，如需@所有人，则集合的值为["@all"]即可
     * @return 文本消息模板
     */
    public TextMsg getMentionedListTextMsg(String textMsgContent, @NotNull List<String> mentionedList) {
        TextMsg textMsg = new TextMsg();
        Content content = new Content(textMsgContent);
        content.setMentioned_list(mentionedList);
        textMsg.setText(content);
        return textMsg;
    }

    /**
     * 获取@电话号码的文本消息模板
     *
     * @param textMsgContent             文本消息内容
     * @param mentionedMobileList 需要@用户电话号码，如需@所有人，则集合的值为["@all"]即可
     * @return 文本消息模板
     */
    public TextMsg getMentionedMobileListTextMsg(String textMsgContent, @NotNull List<String> mentionedMobileList) {
        TextMsg textMsg = new TextMsg();
        Content content = new Content(textMsgContent);
        content.setMentioned_mobile_list(mentionedMobileList);
        textMsg.setText(content);
        return textMsg;
    }

    /**
     * 获取文件类型的消息模板
     *
     * @param mediaId 文件id，通过下文的文件上传接口获取
     * @return 文件类型、息模板
     */
    public FileMsg getFileMsg(String mediaId) {
        FileMsg fileMsg = new FileMsg();
        fileMsg.setFile(new FileContent(mediaId));
        return fileMsg;
    }

    public class MarkdownMsg {
        private final String msgtype = "markdown";
        private Content markdown;

        public String getMsgtype() {
            return msgtype;
        }

        public Content getMarkdown() {
            return markdown;
        }

        public void setMarkdown(Content markdown) {
            this.markdown = markdown;
        }
    }

    public class TextMsg {
        private final String msgtype = "text";
        private Content text;

        public String getMsgtype() {
            return msgtype;
        }

        public Content getText() {
            return text;
        }

        public void setText(Content text) {
            this.text = text;
        }
    }

    public class FileMsg {
        private final String msgtype = "file";
        private FileContent file;

        public String getMsgtype() {
            return msgtype;
        }

        public FileContent getFile() {
            return file;
        }

        public void setFile(FileContent file) {
            this.file = file;
        }
    }

    private class Content {
        private String content;
        private List<String> mentioned_list;
        private List<String> mentioned_mobile_list;

        public Content(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getMentioned_list() {
            return mentioned_list;
        }

        public void setMentioned_list(List<String> mentioned_list) {
            this.mentioned_list = mentioned_list;
        }

        public List<String> getMentioned_mobile_list() {
            return mentioned_mobile_list;
        }

        public void setMentioned_mobile_list(List<String> mentioned_mobile_list) {
            this.mentioned_mobile_list = mentioned_mobile_list;
        }
    }

    private class FileContent {
        private String media_id;

        public FileContent(String media_id) {
            this.media_id = media_id;
        }

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }
    }

}
