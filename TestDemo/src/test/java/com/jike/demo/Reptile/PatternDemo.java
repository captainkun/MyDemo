package com.jike.demo.Reptile;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qukun
 * @date 2019/6/25
 */
public class PatternDemo {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("1\\d{10}");

        String content = "<div><div class='jg666'>[转让]<a href='/17610866588' title='手机号码17610866588估价评估_值多少钱_归属地查询_测吉凶_数字含义_求购转让信息' class='lj44'>17610866588</a>由 张云龙 300元转让,联系电话：17610866588</div><div class='jg666'>[转让]<a href='/17777351513' title='手机号码17777351513估价评估_值多少钱_归属地查询_测吉凶_数字含义_求购转让信息' class='lj44'>17777351513</a>由 胡俊宏 888元转让,QQ：762670775,联系电话：17777351513,可以小砍价..</div><div class='jg666'>[求购]<a href='/15019890606' title='手机号码15019890606估价评估_值多少钱_归属地查询_测吉凶_数字含义_求购转让信息' class='lj44'>15019890606</a>由 张宝红 600元求购,联系电话：15026815169</div><div class='jg666'>";

        Matcher m = p.matcher(content);
        // System.out.println(p.matcher("sf@sina").matches());
        Set<String> set = new HashSet<>();
        // 通过Matcher类的group方法和find方法来进行查找和匹配
        while (m.find()) {
            String value = m.group();
            set.add(value);
        }
        System.out.println(set);
    }
}
