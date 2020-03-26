package com.jike.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qukun
 * @Description 线索列表数据
 * @date 2019/11/4
 */
public class GeekClueVo implements Serializable {

    private static final long serialVersionUID = -1802225887464895560L;

    /**
     * 线索ID
     */
    private Integer id;

    private Integer clueTypeId;
    /**
     * 线索分类名称
     */
    private String clueTypeName;
    /**
     * 站点Id
     */
    private Integer siteId;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户电话
     */
    private Long phone;

    private Integer gradeId;
    /**
     * 年级
     */
    private String grade;

    private Integer subjectId;
    /**
     * 科目
     */
    private String subject;

    private Integer courseTypeId;
    /**
     * 课型
     */
    private String courseType;

    private Integer classTypeId;
    /**
     * 班型
     */
    private String classType;
    /**
     * 课程ID
     */
    private Integer courseId;
    /**
     * 课程名
     */
    private String courseName;

    private Integer schoolAreaId;
    /**
     * 意向校区
     */
    private String schoolArea;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 转换的时间格式
     */
    @Transient
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeExport;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClueTypeName() {
        return clueTypeName;
    }

    public void setClueTypeName(String clueTypeName) {
        this.clueTypeName = clueTypeName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getClueTypeId() {
        return clueTypeId;
    }

    public void setClueTypeId(Integer clueTypeId) {
        this.clueTypeId = clueTypeId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public Integer getClassTypeId() {
        return classTypeId;
    }

    public void setClassTypeId(Integer classTypeId) {
        this.classTypeId = classTypeId;
    }

    public Integer getSchoolAreaId() {
        return schoolAreaId;
    }

    public void setSchoolAreaId(Integer schoolAreaId) {
        this.schoolAreaId = schoolAreaId;
    }

    public Date getCreateTimeExport() {
        return createTimeExport;
    }

    public void setCreateTimeExport(Date createTimeExport) {
        this.createTimeExport = createTimeExport;
    }
}
