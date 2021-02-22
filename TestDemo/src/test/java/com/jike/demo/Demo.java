package com.jike.demo;

import com.alibaba.fastjson.JSON;
import com.jike.demo.concurrent.RunnableImpl;
import com.jike.demo.concurrent.ThreadExtend;
import com.jike.demo.entity.SerializerWrapper;
import com.jike.demo.entity.Student;
import com.jike.demo.entity.User;
import com.jike.demo.util.JdbcUtils;
import com.jike.demo.util.SerializableUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.assertj.core.util.Lists;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.ext.DebugInterceptor;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
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
        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
        // 行和列都是从0开始计数，且起始结束都会合并
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(titleRange);

        CellRangeAddress orderRange = new CellRangeAddress(1, 1, 1, 2);
        sheet.addMergedRegion(orderRange);
        CellRangeAddress shopRange = new CellRangeAddress(2, 2, 1, 2);
        sheet.addMergedRegion(shopRange);
        CellRangeAddress remarkRange = new CellRangeAddress(3, 3, 1, 2);
        sheet.addMergedRegion(remarkRange);

        CellRangeAddress supplierRange = new CellRangeAddress(1, 1, 4, 7);
        sheet.addMergedRegion(supplierRange);
        CellRangeAddress sendDateRange = new CellRangeAddress(2, 2, 4, 7);
        sheet.addMergedRegion(sendDateRange);
        CellRangeAddress doOrderDateRange = new CellRangeAddress(3, 3, 4, 7);
        sheet.addMergedRegion(doOrderDateRange);

        CellRangeAddress addrRange = new CellRangeAddress(4, 4, 1, 7);
        sheet.addMergedRegion(addrRange);

        // 创建字体设置 加粗
        HSSFFont font = workbook.createFont();
        font.setBold(true);

        // 创建单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle.setFont(font);

        // 创建title
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("岳池九龙商品订货单");

        String[] attrs = new String[]{"条码", "编码", "品名", "单位", "规格", "数量", "单价", "金额"};

        for (int row = 1; row < 10; row++) {
            HSSFRow rows = sheet.createRow(row);
            if (row == 1) {
                rows.createCell(0).setCellValue("单号：");
                rows.createCell(3).setCellValue("供应商：");
                continue;
            }
            if (row == 2) {
                rows.createCell(0).setCellValue("门店：");
                rows.createCell(3).setCellValue("送货日期：");
                continue;
            }
            if (row == 3) {
                rows.createCell(0).setCellValue("备注：");
                rows.createCell(3).setCellValue("制单日期：");
                continue;
            }
            if (row == 4) {
                rows.createCell(0).setCellValue("收货地址：");
                continue;
            }
            if (row == 5) {
                for (int i = 0; i < attrs.length; i++) {
                    HSSFCell cell = rows.createCell(i);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(attrs[i]);
                }
                continue;
            }


            for (int col = 0; col < 8; col++) {
                // 向工作表中添加数据
                rows.createCell(col).setCellValue("哈哈哈" + row + col);
            }
        }

        // 创建总计 这里的10，就是最后一行，取查出来的数据量+1即可
        HSSFRow totalRow = sheet.createRow(10);
        HSSFCell totalCell = totalRow.createCell(0);
        totalCell.setCellValue("总计：");

        File xlsFile = new File("poi.xls");
        FileOutputStream xlsStream = new FileOutputStream(xlsFile);
        workbook.write(xlsStream);

    }

    @Test
    public void test02() throws Exception {
        Student student = new Student();
        student.setAge(10);
        student.setStudentName("学生姓名");
        SerializableUtils.serializable(student, "SerializableObject.txt");
        SerializableUtils.deSerializable("SerializableObject.txt");

//        SerializableUtils.serializable(new Student(){{setAge(1);}}, "SerializableObject.txt");


//        mutexGetAndSetIfNeeded(student.toString(), () -> new Student(){{ setAge(90);}});
//        mutexGetAndSetIfNeeded(student, () -> student);


    }

    public <V> V mutexGetAndSetIfNeeded(Object serializedValue, Supplier<? extends V> supplier) {
        V v = supplier.get();
        SerializerWrapper<V> builder = SerializerWrapper.builder(v);

        SerializerWrapper<V> wrapper = (SerializerWrapper<V>) serializedValue;
        V data = wrapper.getData();
        System.out.println(data);
        return data;
    }

    @Test
    public void jdbcTest() throws Exception {
        Connection connection = JdbcUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select 1");
        System.out.println(resultSet.next());
    }

//    private static final Object LOCK = new Object();

    @Test
    public void twoWayToCreateThread() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        String mainThreadName = currentThread.getName();
        System.out.println(mainThreadName + " run start");

        // first: extend Thread.class
        ThreadExtend threadExtend = new ThreadExtend();

        // second: implements Runnable.class
        Thread runnableThread = new Thread(new RunnableImpl());

        // start thread, execute inner run method
        threadExtend.start();
        runnableThread.start();

        // join thread, wait for this thread to die, then run the following method.
        // tips: join method should behind with start method, or it will not be available
        threadExtend.join();
        runnableThread.join();

        System.out.println(mainThreadName + " run over");


    }

    private void setNickName(Student student) {
        student.setNickname("惊叫唤");
    }

    @Test
    public void test04() throws Exception {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
//            Student student = new Student();
//            student.setAge(i);
//            student.setId(i++);
//            student.setStudentName("a" + i);
//            studentList.add(student);
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

    /*
    北京时间东八区：serverTimezone=GMT%2B8
    上海时间：serverTimezone=Asia/Shanghai
     */
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://192.168.0.232:3306/yanwei_cloud_mall?serverTimezone=GMT%2B8";
    private static final String userName = "yuntu_mall";
    private static final String password = "Yt2018";

    @Test
    public void beetlTest() {
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, userName, password);
        DBStyle mysql = new MySqlStyle();
        // sql语句放在classpath的/sql 目录下
        SQLLoader loader = new ClasspathLoader("/sql");
        // 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的，
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        // 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, new Interceptor[]{new DebugInterceptor()});

        try {
            // 输出User类到控制台
            sqlManager.genPojoCodeToConsole("user");
            // 输出简单SQL模板到控制台
            sqlManager.genSQLTemplateToConsole("user");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //使用内置的生成的sql 新增用户，如果需要获取主键，可以传入KeyHolder
        User user = new User();
        user.setAge(19);
        user.setName("xiandafu");
        user.setCreateDate(new Date());
        sqlManager.insert(user);

        //使用内置sql查询用户
        int id = 1;
        user = sqlManager.unique(User.class, id);
        System.out.println(user);

        //模板更新,仅仅根据id更新值不为null的列
        User newUser = new User();
        newUser.setId(1);
        newUser.setAge(20);
        sqlManager.updateTemplateById(newUser);

        //模板查询
        User query = new User();
        query.setName("xiandafu");
        List<User> list = sqlManager.template(query);
        System.out.println(list);

        //Query查询
        LambdaQuery<User> userLambdaQuery = sqlManager.lambdaQuery(User.class);
        List<User> users = userLambdaQuery.andEq(User::getName, "xiandafy").select();
        System.out.println(users);

        //使用user.md 文件里的select语句，参考下一节。
        User query2 = new User();
        query.setName("xiandafu");
        List<User> list2 = sqlManager.select("user.select", User.class, query2);
        System.out.println(list2);

        //使用user.md 文件里的sample语句，参考下一节。
        User query3 = new User();
        query3.setName("xiandafu");
        query3.setAge(20);
        List<User> list3 = sqlManager.select("user.sample", User.class, query3);
        System.out.println(list3);
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

    @Test
    public void tempTest() {
        System.out.println(222);
    }


}
