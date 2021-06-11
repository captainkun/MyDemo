package com.qk.admin.pojo;

/**
 * @author qu.kun
 * @date 2021/3/19
 */
public class WechatRobotMsgInfo {
    private String msgtype = "markdown";

    private Content markdown;

    public static class Content{
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Content getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Content markdown) {
        this.markdown = markdown;
    }
}
