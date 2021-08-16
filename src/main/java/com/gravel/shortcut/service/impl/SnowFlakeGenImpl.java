package com.gravel.shortcut.service.impl;

import com.gravel.shortcut.service.GenNumService;
import com.gravel.shortcut.utils.SnowFlake;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("SnowFlakeGen")
public class SnowFlakeGenImpl implements GenNumService {

    @Resource
    private SnowFlake idGenerator;

    /**
     * 雪花算法 生成 18位数字 支持高并发
     * @return
     */
    @Override
    public long genNum() {
        return idGenerator.nextId();
    }

}
