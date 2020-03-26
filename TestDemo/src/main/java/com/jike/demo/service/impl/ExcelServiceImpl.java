package com.jike.demo.service.impl;
import java.util.Date;

import com.jike.demo.entity.ExcelIn;
import com.jike.demo.entity.GeekClueVo;
import com.jike.demo.service.IExcelService;
import com.kun.utils.ExcelUtils.BigExcelExport;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qukun
 * @Description
 * @date 2020/2/15
 */
@Service
public class ExcelServiceImpl implements IExcelService {
    @Override
    public void excelOut(HttpServletResponse response, ExcelIn excelIn) throws Exception {
        try (OutputStream os = response.getOutputStream()) {
            List<GeekClueVo> contentList = new ArrayList<>();
            GeekClueVo geekClueVo = new GeekClueVo();
            geekClueVo.setId(0);
            geekClueVo.setClueTypeName("啊啊啊");
            geekClueVo.setSiteId(0);
            geekClueVo.setName("啊啊啊啊啊啊");
            geekClueVo.setPhone(0L);
            geekClueVo.setGrade("啊");
            geekClueVo.setSubject("啊");
            geekClueVo.setCourseType("啊");
            geekClueVo.setClassType("啊");
            geekClueVo.setCourseName("啊");
            geekClueVo.setSchoolArea("啊");
            geekClueVo.setRemarks("啊");
            geekClueVo.setCreateTime(new Date());
            geekClueVo.setUpdateTime(new Date());
            geekClueVo.setCourseId(0);
            geekClueVo.setClueTypeId(0);
            geekClueVo.setGradeId(0);
            geekClueVo.setSubjectId(0);
            geekClueVo.setCourseTypeId(0);
            geekClueVo.setClassTypeId(0);
            geekClueVo.setSchoolAreaId(0);
            geekClueVo.setCreateTimeExport(new Date());
            contentList.add(geekClueVo);
            BigExcelExport.exportExcel(contentList, titles, fields, os);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("导出出错啦");
        }
    }

    private final List<String> titles = Arrays.asList("线索分类名称", "姓名", "手机号码", "年级", "学科", "课型", "班型", "课程ID", "课程名称", "意向校区", "备注", "创建时间");
    private final List<String> fields = Arrays.asList("clueTypeName", "name", "phone", "grade", "subject", "courseType",
            "classType", "courseId", "courseName", "schoolArea", "remarks", "createTimeExport");
}
