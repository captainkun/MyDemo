package com.jike.demo;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * @author qukun
 * @date 2019/3/4
 */
public class DBUtilsDemo {
    public static void main(String[] args) {
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://132.232.235.192:5435/edu",
                            "read_user", "c5JJUqmtTeaSaOTN2gs7");
            c.setAutoCommit(false);
            System.out.println("=====数据库连接成功====");
            stmt = c.createStatement();
            rs = stmt.executeQuery("select * from service_user.department where type in(20,30,40)");


            Map<Long, Long> schoolAreaToArea = new HashMap<>();
            Map<Long, String> schoolAreaToName = new HashMap<>();
            Map<Long, String> schoolAreaToAddress = new HashMap<>();
            Map<Long, List<Long>> areaToschoolAreas = new HashMap<>();

            while (rs.next()) {
                long id = rs.getLong("id");
                long parent_id = rs.getLong("parent_id");
                String name = rs.getString("name");
                String address = rs.getString("address");

                schoolAreaToArea.put(id, parent_id);
                schoolAreaToName.put(id, name);
                schoolAreaToAddress.put(id, address);
            }

            schoolAreaToArea.forEach((schoolArea, area) -> {
                Long bigArea = schoolAreaToArea.get(area);
                if ((bigArea == null) || bigArea.equals(10000L)) return;
                System.out.println("schoolAreaAndAreaMap.put(" + schoolArea + "L"+ ", " +  +bigArea + "L);");
            });


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            closeResources(rs, stmt, c);
        }
    }

    private static void closeResources(ResultSet rs, Statement stmt, Connection c) {
        //关闭资源
        try {
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


