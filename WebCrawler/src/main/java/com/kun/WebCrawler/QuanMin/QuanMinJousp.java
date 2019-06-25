package com.kun.WebCrawler.QuanMin;

import java.text.SimpleDateFormat;
import org.jsoup.Connection;
//jsoup版本： 1.11.3
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 全民K歌 歌曲链接爬取
 * @author qukun
 * @date 2019/6/25
 */
public class QuanMinJousp {
    public static void main(String[] args)throws Exception {
        //这里是分享地址：
        String url  ="https://kg3.qq.com/node/play?s=AOTm8TAmI2vB_AtI&shareuid=63999f852d24348c30&topsource=a0_pn201001003_z11_u142089374_l1_t1534154511__";
        Connection tempConn = Jsoup.connect(url);

        //模拟浏览器的请求头
        tempConn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");

        //开始连接HTTP请求
        Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
        //这里就是获取该页面的HTML元素。
        Document documentDemo = demo.parse();
//        System.out.println(documentDemo.toString());

        //获取所有<script>标签
        Elements scriptElements = documentDemo.getElementsByTag("script");
        //下载链接存在的位置
        String initScriptStr = scriptElements.get(2).toString();
        //将数据转换为json
        String jsonStr = initScriptStr.substring(initScriptStr.indexOf("{"), initScriptStr.indexOf("; </script>"));
        System.out.println(jsonStr);
        JSONObject json = JSONObject.parseObject(jsonStr);

        //获取最终的那段数据
        JSONObject jsonDetail = JSONObject.parseObject(json.get("detail").toString());
        QuanMinDTO quanMinDTO = new QuanMinDTO();
        quanMinDTO.setNick(jsonDetail.get("nick").toString());
        quanMinDTO.setPlayurl(jsonDetail.get("playurl").toString());
        quanMinDTO.setPlayurl_video(jsonDetail.get("playurl_video").toString());
        quanMinDTO.setSinger_name(jsonDetail.get("singer_name").toString());
        quanMinDTO.setSong_name(jsonDetail.get("song_name").toString());
        quanMinDTO.setTail_name(jsonDetail.get("tail_name").toString());
        Long timestamp =new Long(jsonDetail.get("ctime").toString()+"000") ;
        System.out.println(timestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        quanMinDTO.setCreateTime(simpleDateFormat.format(timestamp));
        System.out.println(JSON.toJSONString(quanMinDTO));

    }
}
