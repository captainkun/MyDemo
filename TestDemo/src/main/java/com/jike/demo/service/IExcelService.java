package com.jike.demo.service;

import com.jike.demo.entity.ExcelIn;

import javax.servlet.http.HttpServletResponse;

/**
 * @author qukun
 * @Description
 * @date 2020/2/15
 */
public interface IExcelService {

    void excelOut(HttpServletResponse response, ExcelIn excelIn) throws Exception;
}
