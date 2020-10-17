package com.jike.demo.api;

import com.jike.demo.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qu.kun
 * @date 2020/10/10
 * @description
 */
@FeignClient(value = "test2")
public interface FeignClientApi {

    @GetMapping("/users/list")
    Student getStudentInfo(@RequestParam(value = "name") String name);
}
