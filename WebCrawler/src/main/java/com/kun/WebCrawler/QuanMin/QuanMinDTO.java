package com.kun.WebCrawler.QuanMin;

/**
 * @author qukun
 * @date 2019/6/25
 */
public class QuanMinDTO {
    private String nick;
    private String playurl;
    private String playurl_video;
    private String singer_name;
    private String song_name;
    private String tail_name;
    private String createTime;
    private String type;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public String getPlayurl_video() {
        return playurl_video;
    }

    public void setPlayurl_video(String playurl_video) {
        this.playurl_video = playurl_video;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getTail_name() {
        return tail_name;
    }

    public void setTail_name(String tail_name) {
        this.tail_name = tail_name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
