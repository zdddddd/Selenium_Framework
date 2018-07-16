package com.meyoung.testTools;

import com.meyoung.tools.RandomTool;
import org.junit.Test;

public class TestRandomTool {

    @Test
    public void testGetRandomNum(){
        RandomTool.getRandomNum(10);
    }

    @Test
    public void testGetRandomNum1(){
        RandomTool.getRandomNum(10,15);
    }
}
