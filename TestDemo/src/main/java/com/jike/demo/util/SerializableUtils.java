package com.jike.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author qu.kun
 * @date 2020/9/16
 * @description 序列化&反序列化工具类
 */
public class SerializableUtils {
    private static final Logger logger = LoggerFactory.getLogger(SerializableUtils.class);

    /**
     * 序列化
     *
     * @param object     序列化对象
     * @param targetPath 序列化写入磁盘路径及文件名
     */
    public static void serializable(Object object, String targetPath) {
        try (FileOutputStream fos = new FileOutputStream(targetPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
            logger.info("-------对象序列化成功:{}-------", targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     *
     * @param targetPath 反序列化文件路径及文件名
     */
    public static void deSerializable(String targetPath) {
        try (FileInputStream sfis = new FileInputStream(targetPath);
             ObjectInputStream sois = new ObjectInputStream(sfis)) {
            Object object = sois.readObject();
            logger.info("-------对象反序列化成功:{}-------", object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
