package com.jike.demo.service;

import java.util.Set;

/**
 * @author qukun
 * @Description
 * @date 2020/3/24
 */
public interface IDemoService {
    void addWhiteList(Long id);

    Set<Long> getWhiteList();
}
