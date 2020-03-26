package com.jike.demo;

import com.mongodb.client.MongoDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qukun
 * @Description
 * @date 2020/3/20
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = DemoApplication.class)
public class SpringBootTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void hehe() {

        MongoDatabase db = mongoTemplate.getDb();
        System.out.println(db.listCollectionNames().first());

    }
}
