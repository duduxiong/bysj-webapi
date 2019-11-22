package com.lzy.mtnj.infrastructure.util;

import java.util.Random;

public class RandomUtil {
    public static String CreateRandom(int length){
        Random random = new Random();
        String result = "";
        for(int i=0;i<length;i++){
            result+=random.nextInt(10);
        }
        return result;
    }
}
