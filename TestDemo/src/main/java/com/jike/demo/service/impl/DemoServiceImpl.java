package com.jike.demo.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.jike.demo.entity.User;
import com.jike.demo.service.IDemoService;
import com.jike.demo.service.IProducer;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.ext.DebugInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author qukun
 * @Description
 * @date 2020/3/24
 */
@Service
public class DemoServiceImpl implements IDemoService {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Autowired
    private IProducer producer;

    private static Set<Long> whiteList = new CopyOnWriteArraySet<>();

    @Override
    public void addWhiteList(Long id) {
        whiteList.add(id);
    }

    @Override
    public Set<Long> getWhiteList() {
        return whiteList;
    }

    @Override
    public Object beetl() {
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, userName, password);
        DBStyle mysql = new MySqlStyle();
        // sql语句放在classpagth的/sql 目录下
        SQLLoader loader = new ClasspathLoader("/sql");
        // 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的，
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        // 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, new Interceptor[]{new DebugInterceptor()});

        try {
            sqlManager.genPojoCodeToConsole("user");
            sqlManager.genSQLTemplateToConsole("user");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //使用内置的生成的sql 新增用户，如果需要获取主键，可以传入KeyHolder
        User user = new User();
        user.setAge(19);
        user.setName("xiandafu");
        sqlManager.insert(user);

        //使用内置sql查询用户
        int id = 1;
        user = sqlManager.unique(User.class, id);


        //模板更新,仅仅根据id更新值不为null的列
        User newUser = new User();
        newUser.setId(1);
        newUser.setAge(20);
        sqlManager.updateTemplateById(newUser);

        //模板查询
        User query = new User();
        query.setName("xiandafu");
        List<User> list = sqlManager.template(query);


        //Query查询
        LambdaQuery<User> userLambdaQuery = sqlManager.lambdaQuery(User.class);
        List<User> users = userLambdaQuery.andEq(User::getName, "xiandafy").select();


        //使用user.md 文件里的select语句，参考下一节。
        User query2 = new User();
        query.setName("xiandafu");
        List<User> list2 = sqlManager.select("user.select", User.class, query2);

        // 这一部分需要参考mapper一章
//        UserDao dao = sqlManager.getMapper(UserDao.class);
//        List<User> list3 = dao.select(query2);
        return null;
    }

    @Override
//    @Scheduled(cron = "0/1 * * * * ? ")
    public void scheduleService() {
        System.out.println("定时任务执行");
    }

    @Override
    @Async
    public void threadTest() {
        saleProduct(BigDecimal.ZERO);
        System.out.println("主线程执行完毕");
    }

    @Override
    @SentinelResource(value = "sayHello")
    public String sayHello(String name) {
        return "Hello," + name;
    }


    public void saleProduct(BigDecimal money) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步方法执行了");
        System.out.println("销售成功，销售额：" + money);
    }


}
