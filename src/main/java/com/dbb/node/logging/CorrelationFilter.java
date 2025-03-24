package com.dbb.node.logging;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class CorrelationFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return true;
    }
}