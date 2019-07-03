package com.jike.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.util.Strings;
import com.google.gson.Gson;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.assertj.core.util.DateUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

        String sb = "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001128, '极运营/人事管理/员工信息/删除员工信息', '{1459,1398,1463,1368}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001132, '极运营/人事管理/员工信息/批量导入员工信息', '{1459,1398,1461,1273,1366,1360}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001133, '极运营/人事管理/员工信息/新增员工信息', '{1459,1398,1460,1377}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001136, '极运营/人事管理/员工信息/查看员工信息', '{1398,1459,1346,1348,1365}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001142, '极运营/人事管理/员工信息/编辑员工信息', '{1459,1398,1462,1345,1348,1346}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001137, '极运营/人事管理/教师授权/查看教师授权', '{1396,1398,1378}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001143, '极运营/人事管理/教师授权/编辑教师授权', '{1396,1398,1400,1359}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001130, '极运营/人事管理/权限设置/删除角色权限', '{1467,1398,1470,1351}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001134, '极运营/人事管理/权限设置/新增角色', '{1467,1398,1468,1388,1362}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001139, '极运营/人事管理/权限设置/查看角色', '{1467,1398,1345}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001144, '极运营/人事管理/权限设置/编辑角色权限', '{1467,1398,1469,1388,1350,1376}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001129, '极运营/人事管理/用户授权/删除用户权限', '{1464,1398,1466,1370}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001138, '极运营/人事管理/用户授权/查看用户授权', '{1464,1398,1345,1349}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001141, '极运营/人事管理/用户授权/添加用户权限', '{1464,1398,1465,1349,1346,1353}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001131, '极运营/人事管理/部门设置/删除部门', '{1471,1398,1474,1381}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001135, '极运营/人事管理/部门设置/新增部门', '{1471,1398,1472,1369}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001140, '极运营/人事管理/部门设置/查看部门', '{1471,1398,1382}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001145, '极运营/人事管理/部门设置/编辑部门', '{1471,1398,1473,1373}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001146, '极运营/前台业务/学生信息/查看并编辑学生信息', '{1420,1398,1088,1090,1102,1231,1242,1243,1246,1253}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001147, '极运营/前台业务/批量转班/批量转班操作', '{1064,1093,1022,1021,1185,1255,1204,1398,1407}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001152, '极运营/前台业务/批量转班/查看转班记录', '{1398,1407,1234}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001148, '极运营/前台业务/报名/学生报名', '{1403,1398,1010,1021,1041,1061,1072,1073,1076,1079,1081,1082,1083,1084,1088,1090,1102,1202,1231,1235,1246,1254,1286}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001176, '极运营/前台业务/收费对账/对账操作及查看对账记录', '{1398,1618,1612,1611,1613,1614,1615,1610}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001150, '极运营/前台业务/结转/查看结转记录', '{1430,1336,1102,1088,1010,1246,1398}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001153, '极运营/前台业务/结转/结转操作', '{1090,1167,1239,1398,1430}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001149, '极运营/前台业务/缴款/新增缴款', '{1339,1273,1337,1344,1398,1404}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001151, '极运营/前台业务/缴款/查看缴款记录', '{1344,1329,1341,1398,1404}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001154, '极运营/前台业务/订单管理/订单管理', '{1398,1405,1406,1077,1072,1088,1102,1071,1068,1141,1147}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001155, '极运营/前台业务/订单管理/订单管理（无订单作废按钮）', '{1398,1405,1077,1072,1088,1102,1071,1068,1141,1147}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001156, '极运营/教务管理/排课/修改排课', '{1417,1062,1035,1398,1432}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001170, '极运营/教务管理/排课/查看排课', '{1417,1398,1034,1008,1103}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001167, '极运营/教务管理/教室设置/教室设置', '{1398,1435,1000,1057,1042,1014}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001157, '极运营/教务管理/班级/修改班级', '{1398,1421,1431,1048,1035,1062}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001160, '极运营/教务管理/班级/删除班级', '{1398,1421,1423,1053}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001165, '极运营/教务管理/班级/批量建班', '{1398,1421,1416,1058,1050}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001168, '极运营/教务管理/班级/新建班级', '{1398,1421,1422,1036,1062}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001171, '极运营/教务管理/班级/查看班级', '{1421,1398,1024,1030,1121,1174,1204,1040}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001162, '极运营/教务管理/网报设置/原班设置', '{1436,1398,1438}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001172, '极运营/教务管理/网报设置/查看班级网报设置', '{1436,1398,1055}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001175, '极运营/教务管理/网报设置/设置网报时间', '{1436,1398,1437,1029}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001166, '极运营/教务管理/课消/操作课消', '{1398,1433,1434,1096,1229}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001173, '极运营/教务管理/课消/查看课消', '{1433,1398,1193,1024,1219}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001158, '极运营/教务管理/课程/修改课程', '{1409,1415,1398,1020,1030,1040,1023}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001159, '极运营/教务管理/课程/停用课程', '{1398,1413,1409,1028}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001161, '极运营/教务管理/课程/删除课程', '{1398,1409,1414,1033}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001163, '极运营/教务管理/课程/启用课程', '{1028,1398,1409,1412}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001164, '极运营/教务管理/课程/复制课程', '{1398,1409,1411,1047}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001169, '极运营/教务管理/课程/新建课程', '{1047,1398,1409,1410}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001174, '极运营/教务管理/课程/查看课程', '{1409,1398,1013,1142,1030}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001194, '极运营/系统设置/优惠设置/优惠设置', '{1398,1426,1012,1025,1026}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001191, '极运营/系统设置/基础参数设置/基础参数设置', '{1398,1419,1001,1021,1032,1011,1009,1059,1007}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001195, '极运营/系统设置/学生身份/学生身份设置', '{1398,1429,1021}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001196, '极运营/系统设置/招生目标设置/招生目标设置', '{1398,1475,1392,1394,1393,1391,1395,1653}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001193, '极运营/系统设置/教学参数设置/教学参数设置', '{1398,1425,1001,1021}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001192, '极运营/系统设置/课程类型设置/课程类型设置', '{1398,1424,1001,1002}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001190, '极运营/统计报表/业务统计/业务统计', '{1456,1398,1156,1114,1145,1118,1125,1126,1128,1130,1132,1152}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001186, '极运营/统计报表/收费报表/收费报表', '{1448,1398,1116,1157,1122,1135,1106,1111,1146,1104}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001187, '极运营/统计报表/班级报表/班级报表', '{1450,1398,1119,1155,1117,1136,1115,1140}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001188, '极运营/统计报表/课消报表/课消报表', '{1452,1398,1105,1151,1112,1120,1144,1109}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001189, '极运营/统计报表/违规统计/违规统计', '{1454,1398,1110,1150,1113,1133}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001183, '极运营/财务管理/充值/学生充值', '{1445,1398,1222,1227,1236}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001185, '极运营/财务管理/对账日报/查看对账日报', '{1398,1617,1614,1615,1610}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001184, '极运营/财务管理/库存现金/查看库存现金', '{1398,1446,1338,1123}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001177, '极运营/财务管理/收支流水/查看收支流水', '{1398,1440,1335,1154}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001179, '极运营/财务管理/缴款确认/缴款确认', '{1398,1404,1344,1342,1137}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001178, '极运营/财务管理/账户统计/查看账户统计', '{1398,1441,1332,1340}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001182, '极运营/财务管理/账户设置/账户设置', '{1398,1444,1330,1331,1334,1343}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001181, '极运营/财务管理/退费/退费操作', '{1398,1443,1477,1196,1163,1224,1333}', 1);\n" +
                "INSERT INTO \"\"(\"id\", \"name\", \"permission_id\", \"type\") VALUES (10001180, '极运营/财务管理/退费/退费记录查看', '{1398,1443,1333,1246,1158}', 1);";

        String replace1 = sb.replaceAll("\"\"","service_user.permission_group");
        String replaceAll = replace1.replaceAll("1000\\d\\d\\d\\d", "nextval('permission_group_id_seq')");
        System.out.println(replaceAll);

    }

    @Test
    public void test04() {

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
