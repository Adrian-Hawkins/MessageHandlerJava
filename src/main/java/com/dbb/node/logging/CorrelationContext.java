package com.dbb.node.logging;

public class CorrelationContext {
    private static final ThreadLocal<String> correlationGuid = new ThreadLocal<>();

    public static void setCorrelationGuid(String guid) {
        correlationGuid.set(guid);
    }

    public static String getCorrelationGuid() {
        return correlationGuid.get();
    }

    public static void clear() {
        correlationGuid.remove();
    }
}