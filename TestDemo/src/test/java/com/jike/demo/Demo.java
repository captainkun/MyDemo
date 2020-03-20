package com.jike.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jike.demo.entity.Son;
import com.jike.demo.entity.Student;
import com.jike.demo.enums.SeasonEnum;
import com.sun.org.apache.regexp.internal.CharacterIterator;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.assertj.core.util.Lists;
import org.junit.Test;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qukun
 * @date 2019/3/11
 */

public class Demo {

    private static Map<String, Integer> classesCacheByGrade = new HashMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
        Date date = new Date();
        System.out.println(sdf);
        System.out.println(sdf.parse("2019-01-01"));

    }
    @Test
    public void test022() throws Exception {
        String date = "2020-02-26";
        LocalDate parse = LocalDate.parse(date);
        System.out.println(parse);
    }

    private void initStudent(Student student) {
        student = new Student();
        student.setStudentName("阿波吃的");
    }

    @Test
    public void test04() throws Exception {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Student student = new Student();
            student.setAge(i);
            student.setId(i++);
            student.setStudentName("a" + i);
            studentList.add(student);
        }
        long start = System.currentTimeMillis();
        Map<Integer, Long> collect = studentList.stream().collect(Collectors.toMap(Student::getAge, Student::getId));
        Map<Integer, String> collect1 = studentList.stream().collect(Collectors.toMap(Student::getAge, Student::getStudentName));
        System.out.println(System.currentTimeMillis() - start);

        long start2 = System.currentTimeMillis();
        Map<Integer, Long> map1 = new HashMap<>();
        Map<Integer, String> map2 = new HashMap<>();
        studentList.forEach(student -> {
            map1.put(student.getAge(), student.getId());
            map2.put(student.getAge(), student.getStudentName());
        });
        System.out.println(System.currentTimeMillis() - start2);


        long start3 = System.currentTimeMillis();
        Map<Integer, Long> map3 = new HashMap<>();
        Map<Integer, String> map4 = new HashMap<>();
        for (Student student : studentList) {
            map3.put(student.getAge(), student.getId());
            map4.put(student.getAge(), student.getStudentName());
        }
        System.out.println(System.currentTimeMillis() - start3);

        long start4 = System.currentTimeMillis();
        Map<Integer, Long> map5 = new HashMap<>();
        Map<Integer, String> map6 = new HashMap<>();
        studentList.forEach(student -> {
            map5.put(student.getAge(), student.getId());
            map6.put(student.getAge(), student.getStudentName());
        });
        System.out.println(System.currentTimeMillis() - start4);



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
