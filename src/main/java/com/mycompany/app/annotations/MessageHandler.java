package com.mycompany.app.annotations;

@FunctionalInterface
public interface MessageHandler {
    void handle(Object message) throws Exception;
}