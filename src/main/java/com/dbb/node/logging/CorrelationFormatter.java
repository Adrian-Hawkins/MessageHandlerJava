package com.dbb.node.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CorrelationFormatter extends Formatter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    @Override
    public String format(LogRecord record) {
        String timestamp = DATE_FORMAT.format(new Date(record.getMillis()));
        String logLevel = record.getLevel().getName();
        String correlationGuid = CorrelationContext.getCorrelationGuid();
        String message = record.getMessage();

        return correlationGuid != null
                ? String.format("%s %s [%s] %s%n", timestamp, logLevel, correlationGuid, message)
                : String.format("%s %s %s%n", timestamp, logLevel, message);

    }
}