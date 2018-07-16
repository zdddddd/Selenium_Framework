package com.meyoung.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoggerControler {
    private static Logger logger = null;
    private static  LoggerControler logControler = null;
    
    public  static LoggerControler getLogger(Class<?> T){
        if(logger==null){
            Properties properties = new Properties();

            try {
                String path = System.getProperty("user.dir")+"/config/log4j.properties";
                InputStream is = new FileInputStream(path);
                properties.load(is);
            }catch (IOException e){
                e.printStackTrace();
            }
            PropertyConfigurator.configure(properties);
            logger = Logger.getLogger(T);
            logControler = new LoggerControler();

        }
        return logControler;
    }

    public  static Logger getLogger1(Class<?> T){
        if(logger==null){
            PropertyConfigurator.configure("config/log4j.properties");
            logger = Logger.getLogger(T);
        }
        return logger;
    }

    public void info(String msg){
        logger.info(msg);
    }

    public void debug(String msg){
        logger.debug(msg);
    }

    public void warn(String msg){
        logger.warn(msg);
    }

    public void error(String msg){
        logger.error(msg);
    }

//        private static Logger logger = Logger.getLogger(LoggerControler.class);
//
//        /**
//         * @param args
//         */
//        public static void main(String[] args) {
//            // System.out.println("This is println message.");
//
//            // 记录debug级别的信息
//            logger.debug("This is debug message.");
//            // 记录info级别的信息
//            logger.info("This is info message.");
//            // 记录error级别的信息
//            logger.error("This is error message.");
//        }

}
