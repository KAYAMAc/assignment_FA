package com.FA.assignment.util;

import org.apache.commons.logging.LogFactory;

public class FALogger {
    private static void outputLog(String message){
        org.apache.commons.logging.Log log = LogFactory.getLog("1");
        log.info(message);
    }
}
