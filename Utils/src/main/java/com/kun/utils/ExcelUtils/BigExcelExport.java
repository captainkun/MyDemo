package com.kun.utils.ExcelUtils;

import com.alibaba.fastjson.JSONObject;
import com.kun.utils.exception.BusinessException;
import com.kun.utils.exception.ResultCode;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;

/**
 * 下载excel，仅处理不带下拉框的
 */
public class BigExcelExport {
    /**
     *
     * @param datas 数据源
     * @param titles 标题头
     * @param fields 数据源中对应标题头的字段名，顺序和数量需要和标题头一致
     * @param os 流
     */
    public static void exportExcel(List<?> datas, List<String> titles, List<String> fields, OutputStream os) {
        if (titles == null || titles.size() == 0 || fields == null || fields.size() == 0 || fields.size() != titles.size())
            throw BusinessException.of("标题头或字段设置不正确，数量不一致或者为空，请检查", ResultCode.DATA_IS_WRONG);
        // 声明一个工作薄
        SXSSFWorkbook wb = new SXSSFWorkbook();
        // 声明一个单子并命名
        Sheet sheet = wb.createSheet("1");
        // 给单子名称一个长度
        sheet.setDefaultColumnWidth(17);
        // 生成一个样式
        CellStyle headStyle = headStyle(wb);
        // 创建第一行（也可以称为表头）
        Row row = sheet.createRow(0);

        // 给表头第一行一次创建单元格
        for (int index = 0; index < titles.size(); index++) {
            Cell cell = row.createCell( index);
            // 名称..
            cell.setCellValue(titles.get(index));
            cell.setCellStyle(headStyle);
        }
        // 向单元格里填充数据
        CellStyle bodyStyle = bodyStyle(wb);
        for (int i = 0; i < datas.size(); i++) {
            row = sheet.createRow(i + 1);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(datas.get(i)).toString());
            for (int index = 0; index < titles.size(); index++) {
                String field = fields.get(index);
                Cell cell = row.createCell(index);
                cell.setCellStyle(bodyStyle);
                Object value=jsonObject.get(field);
                if (StringUtils.isEmpty(value))
                    value = "";
                if (value.equals(Boolean.FALSE)) value = "否";
                if (value.equals(Boolean.TRUE)) value = "是";
                cell.setCellValue(value + "");
            }
        }
        try {
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CellStyle headStyle(SXSSFWorkbook wb){
        CellStyle style = wb.createCellStyle();
        style.setAlignment(ExtendedFormatRecord.CENTER);
        Font font2 = wb.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 12);
        style.setFont(font2);
        return style;
    }

    private static CellStyle bodyStyle(SXSSFWorkbook wb){
        CellStyle style = wb.createCellStyle();
        style.setAlignment(ExtendedFormatRecord.CENTER);
        return style;
    }


//    public static String uploadExcelStream(COSClientUtil cosClientUtil, List<?> datas, List<String> titles, List<String> fields, String fileName) throws IOException {
//        if (titles == null || titles.size() == 0 || fields == null || fields.size() == 0 || fields.size() != titles.size())
//            throw BusinessException.of("标题头或字段设置不正确，数量不一致或者为空，请检查", ResultCode.DATA_IS_WRONG);
//        // 声明一个工作薄
//        try(SXSSFWorkbook wb = new SXSSFWorkbook()){
//            // 声明一个单子并命名
//            Sheet sheet = wb.createSheet("1");
//            // 给单子名称一个长度
//            sheet.setDefaultColumnWidth(17);
//            // 生成一个样式
//            CellStyle headStyle = headStyle(wb);
//            // 创建第一行（也可以称为表头）
//            Row row = sheet.createRow(0);
//
//            // 给表头第一行一次创建单元格
//            for (int index = 0; index < titles.size(); index++) {
//                Cell cell = row.createCell( index);
//                // 名称..
//                cell.setCellValue(titles.get(index));
//                cell.setCellStyle(headStyle);
//            }
//            // 向单元格里填充数据
//            CellStyle bodyStyle = bodyStyle(wb);
//            for (int i = 0; i < datas.size(); i++) {
//                row = sheet.createRow(i + 1);
//                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(datas.get(i)).toString());
//                for (int index = 0; index < titles.size(); index++) {
//                    String field = fields.get(index);
//                    Cell cell = row.createCell(index);
//                    cell.setCellStyle(bodyStyle);
//                    Object value=jsonObject.get(field);
//                    // 数字格式导出判断
//                    if (StringUtils.isEmpty(value)) {
//                        value = "-";
//                        cell.setCellType(1);
//                        cell.setCellValue(value + "");
//                    } else  if (value.equals(Boolean.FALSE)) {
//                        value = "否";
//                        cell.setCellType(1);
//                        cell.setCellValue(value + "");
//                    } else   if (value.equals(Boolean.TRUE)) {
//                        value = "是";
//                        cell.setCellType(1);
//                        cell.setCellValue(value + "");
//                    } else if (value instanceof Number)  {
//                        // 防止异常报错
//                        try {
//                            cell.setCellType(0);
//                            cell.setCellValue(Double.valueOf(value.toString()));
//                        } catch (Exception e) {
//                            cell.setCellType(1);
//                            cell.setCellValue(value + "");
//                        }
//                    } else {
//                        cell.setCellType(1);
//                        cell.setCellValue(value + "");
//                    }
//                }
//            }
//
//            String fileUrl = uploadExcel(cosClientUtil, fileName, wb);
//            return fileUrl;
//        }
//    }

    private static String path = "/ExportExcelTemp/";

//    private static String uploadExcel(COSClientUtil cosClientUtil, String fileName, SXSSFWorkbook wb)  {
//        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
//            wb.write(out);
//            try(InputStream excelStream = new ByteArrayInputStream(out.toByteArray())){
//                String bucketName = "";
//                if (cosClientUtil.isProd) {
//                    bucketName = COSClientUtil.BUCKET_PROFILE;
//                } else {
//                    bucketName = COSClientUtil.BUCKET_TEST;
//                }
//                String url = cosClientUtil.uploadExcelWithStream(excelStream, path + fileName, bucketName);
//                out.close();
//                return url;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw BusinessException.of("导出异常", ResultCode.DATA_IS_WRONG);
//        }
//    }

}
