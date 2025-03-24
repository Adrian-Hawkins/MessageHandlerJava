package com.dbb.node.annotations;

@FunctionalInterface
public interface MessageHandler {
    void handle(Object message) throws Exception;
}