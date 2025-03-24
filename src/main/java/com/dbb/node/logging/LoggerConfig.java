package com.dbb.node.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerConfig {
    public static void configure(Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CorrelationFormatter());
        consoleHandler.setFilter(new CorrelationFilter());
        consoleHandler.setLevel(Level.ALL);

        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        configure(logger);
        return logger;
    }
}