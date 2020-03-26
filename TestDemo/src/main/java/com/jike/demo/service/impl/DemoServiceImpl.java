package com.jike.demo.service.impl;

import com.jike.demo.service.IDemoService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author qukun
 * @Description
 * @date 2020/3/24
 */
@Service
public class DemoServiceImpl implements IDemoService {
    private static Set<Long> whiteList = new CopyOnWriteArraySet<>();

    @Override
    public void addWhiteList(Long id) {
        whiteList.add(id);
    }

    @Override
    public Set<Long> getWhiteList() {
        return whiteList;
    }


}
