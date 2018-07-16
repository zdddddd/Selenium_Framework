package com.meyoung.testLog4j;

import com.meyoung.log4j.LoggerControler;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestLoggerController {

    final static  LoggerControler log = LoggerControler.getLogger(TestLoggerController.class);


    @Test
    public void testCase1(){
        log.debug("这是debug");
        log.error("这是error");
        log.info("这是info");
        log.warn("这是warn");
    }

    final static  Logger logger = LoggerControler.getLogger1(TestLoggerController.class);
    @Test
    public void testCase2(){
        logger.debug("这是debug");
        logger.error("这是error");
        logger.info("这是info");
        logger.warn("这是warn");
    }
}
