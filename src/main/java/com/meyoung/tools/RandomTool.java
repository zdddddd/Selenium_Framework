package com.meyoung.tools;

import com.meyoung.log4j.LoggerControler;
import org.apache.log4j.Logger;

import java.util.Random;

public class RandomTool {

    final static Logger logger = LoggerControler.getLogger1(RandomTool.class);
    //生成N位数的随机数
    public static long getRandomNum(int length){
        long random = (long)(Math.random()*Math.pow(10,length));
        logger.debug(random);
        return random;
    }

    //生成某个范围内的随机数
    public static int getRandomNum(int min,int max){
        Random random = new Random();
        int i = random.nextInt(max - min + 1) + min;
        logger.debug("min:"+min+"  max:"+max+  "  random:"+i);
        return i;
    }

    //生成一个字母
    public static char getRandomString(){
        //根据acs码来先生成数字然后再转成char类型
        //65-90 是大写字母  97-122是小写字母
        Random random = new Random();
        //生成n位就用for循环，和sStringBuffer
        int i = random.nextInt(2) % 2 == 0 ? 65 : 97;
        char c = (char) (random.nextInt(26) + i);
        return c;
    }

    //生成数位字母
    public static String getRandomString(int length){
        //根据acs码来先生成数字然后再转成char类型
        //65-90 是大写字母  97-122是小写字母
        Random random = new Random();
        //生成n位就用for循环，和sStringBuffer
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <length ; i++) {

            int a = random.nextInt(2) % 2 == 0 ? 65 : 97;
            char c = (char) (random.nextInt(26) + a);
            sb.append(c);
        }
            return sb.toString();
    }
}
