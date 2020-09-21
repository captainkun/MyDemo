package com.jike.demo.util;

import java.sql.*;

/**
 * @author qu.kun
 * @date 2020/9/21
 * @description
 */
public class JdbcUtils {
    private static final String USER = "yuntu_mall";
    private static final String PWD = "Yt2019sit2";
    private static final String URL = "jdbc:mysql://sit2-mysql-yuntujk.mysql.rds.aliyuncs.com:3306/yanwei_cloud_mall_sit2?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=true&allowMultiQueries=true";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
//    private static final String USER = "yuntu_mall";
//    private static final String PWD = "Yt2018";
//    private static final String URL = "jdbc:mysql://192.168.0.232:3306/yanwei_cloud_mall?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=true&allowMultiQueries=true";
//    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 注册驱动(可以省略)
     */
    static {
        try {
            Class.forName(DRIVER); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
    }

    /**
     * 得到数据库的连接
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    /**
     * 关闭所有打开的资源
     */
    public static void close(Connection conn, Statement stmt){
        if(stmt != null) {
            try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if(conn != null) {
            try { conn.close(); }catch (SQLException e) { e.printStackTrace(); }
        }

    }

    /**
     * 关闭所有打开的资源 重载
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        close(conn, stmt);
    }
}
