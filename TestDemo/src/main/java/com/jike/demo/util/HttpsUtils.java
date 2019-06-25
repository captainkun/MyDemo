package com.jike.demo.util;

import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qukun
 * @date 2019/2/19
 */
public class HttpsUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpsUtils.class);
    public static String doPost(String url, String json) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String result = null;
        try {
            StringEntity s = new StringEntity(json,"utf-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);

            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (res.getEntity().isStreaming() && "image/jpeg".equals(res.getEntity().getContentType().getValue())){
                    File file = new File("C:/Users/Administrator/Desktop/5.jpg");
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    OutputStream outputStream = new FileOutputStream(file);
                    res.getEntity().writeTo(outputStream);
                    outputStream.flush();
                    return "ojbk";
                }
                result = EntityUtils.toString(res.getEntity());// 返回json格式：

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.error("【post关闭】 异常IOException = ", e);
            }
        }
        return result;
    }
}
