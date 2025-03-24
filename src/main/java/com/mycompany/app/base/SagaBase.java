package com.mycompany.app.base;

import com.mycompany.app.App;
import com.mycompany.app.logging.LoggerConfig;

import java.util.logging.Logger;

public class SagaBase {
    private static final Logger logger = LoggerConfig.getLogger(SagaBase.class);

    public String inner = "oop test";
    public void testlog() {
        System.out.println("this is just a oop test: " + this.getClass().getSimpleName() + this.inner);
    }
    public void log(String logline) {
        System.out.println(logline);
    }
}
