package com.dbb.node.base;

import com.dbb.node.logging.LoggerConfig;
import com.dbb.node.util.Bus;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SagaBase {
    private static final Logger logger = LoggerConfig.getLogger(SagaBase.class);

    protected Bus bus = new Bus();

    public void log(String logLine) {
        logger.fine(logLine);
    }

    public void log(Level level, String logLine) {
        logger.log(level, logLine);
    }
}
