package com.yassine.insta_clone_backend.utils;

import org.springframework.stereotype.Service;

@Service
public class NumberUtils {

    public int generateRandomBetweenValues(int min, int max){
        return (int) ((Math.random() * (max-min)) + min);
    }
}
