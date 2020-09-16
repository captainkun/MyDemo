package com.jike.demo.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @author qukun
 * @date 2019/5/30
 */

public class Student implements Serializable {

    private static final long serialVersionUID = -5364059559850603751L;
    private long id;

    @NotNull(message = "学生姓名不能为空")
    private String studentName;

    private String nickname;

    private Integer age;

    private Map<Integer, String> map;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(studentName, student.studentName) &&
                Objects.equals(nickname, student.nickname) &&
                Objects.equals(age, student.age) &&
                Objects.equals(map, student.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentName, nickname, age, map);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", map=" + map +
                '}';
    }
}
