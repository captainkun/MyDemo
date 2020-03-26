package com.jike.demo.controller;

import com.jike.demo.entity.ExcelIn;
import com.jike.demo.service.IExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author qukun
 * @Description excel导入导出功能测试
 * @date 2020/2/15
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private IExcelService excelService;

    @PostMapping("/excelOut")
    public void excelOut(HttpServletResponse response, @RequestBody @Valid ExcelIn excelIn) throws Exception {
        excelService.excelOut(response, excelIn);
    }
}
