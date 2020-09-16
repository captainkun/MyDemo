package com.jike.demo.util;

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
    /**
     * 序列化
     * @param object 序列化对象
     * @param targetPath 序列化写入磁盘路径及文件名
     */
    public static void serializable(Object object, String targetPath) {
        try (FileOutputStream fos = new FileOutputStream(targetPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     * @param targetPath 反序列化文件路径及文件名
     */
    public static void deSerializable(String targetPath) {
        try(FileInputStream sfis = new FileInputStream(targetPath);
            ObjectInputStream sois = new ObjectInputStream(sfis)) {
            Object object = sois.readObject();
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
