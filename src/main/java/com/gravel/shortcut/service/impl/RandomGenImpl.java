package com.gravel.shortcut.service.impl;

import com.gravel.shortcut.service.GenNumService;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("RandomGen")
public class RandomGenImpl implements GenNumService {

    private static final Random random = new Random();

    private static final int length = 10;

    @Override
    public long genNum() {
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                val.append(random.nextInt(9) + 1);
            } else {
                val.append(random.nextInt(10));
            }
        }
        return Long.parseLong(val.toString());
    }
}
