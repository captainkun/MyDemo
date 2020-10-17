package com.kun.demo.api.inner;

import com.kun.demo.pojo.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author qu.kun
 * @date 2020/10/10
 * @description
 */
@RestController
public class FeignService {
    @GetMapping("/users/list")
    Student getOwner(@RequestParam(value = "name") String name){
        return new Student(){{setAge(18);setStudentName(name);}};
    }

}
