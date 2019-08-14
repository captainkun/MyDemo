package com.jike.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jike.demo.entity.Student;
import org.apache.logging.log4j.util.Strings;
import com.google.gson.Gson;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.assertj.core.util.Lists;
import org.springframework.scheduling.Trigger;
import org.springframework.util.Assert;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author qukun
 * @date 2019/3/11
 */

public class Demo {

    private static Map<String, Integer> classesCacheByGrade = new HashMap<>();

    @Test
    public void test01() throws IOException {

        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet("sheet1");

        for (int row = 0; row < 10; row++) {
            HSSFRow rows = sheet.createRow(row);
            for (int col = 0; col < 10; col++) {
                // 向工作表中添加数据
                rows.createCell(col).setCellValue("哈哈哈" + row + col);
            }
        }

        File xlsFile = new File("poi.xls");
        FileOutputStream xlsStream = new FileOutputStream(xlsFile);
        workbook.write(xlsStream);

    }

    @Test
    public void test02() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = "2019/08/01 13:20:5";
        System.out.println("我要被删除");
        System.out.println("2111111");
    }

    @Test
    public void test04() throws Exception {
        System.out.println(new Timestamp(new Date().getTime()));
        System.out.println(new Date());
    }

    private long runTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请问 3 + (2 - 5) = ?");
        String text = scanner.next();
        while (true) {
            if (String.valueOf(3 + (2 - 5)).equals(text)) {
                System.out.println("BingGo!");
                break;
            } else if ("?".equals(text)) {
                System.out.println("秘籍开启");
                System.out.println("正确答案为:0,请再次输入答案:");
                text = scanner.next();
            } else {
                System.out.println("回答错误,请重新输入答案:");
                text = scanner.next();
            }
        }
    }


}
