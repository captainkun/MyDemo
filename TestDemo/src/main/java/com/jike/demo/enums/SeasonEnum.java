package com.jike.demo.enums;

/**
 * @author qukun
 * @Description 测试枚举
 * @date 2020/1/11
 */
public enum  SeasonEnum {
    SPRING("春"),
    SUMMER("夏"),
    QIU("秋"),
    WINTER("冬");

    private String ChineseName;

    SeasonEnum(String ChineseName) {
        this.ChineseName = ChineseName;
    }

    public String getChineseName() {
        return ChineseName;
    }
}
