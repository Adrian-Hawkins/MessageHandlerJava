package com.mycompany.app.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CorrelationFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        if (CorrelationContext.getCorrelationGuid() != null)
            return String.format("%s %s [%s] %s \n",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(record.getMillis())),
                    record.getLevel().getName(),
                    CorrelationContext.getCorrelationGuid(),
                    record.getMessage());
        else
            return String.format("%s %s %s \n",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(record.getMillis())),
                    record.getLevel().getName(),
                    record.getMessage());

    }
}