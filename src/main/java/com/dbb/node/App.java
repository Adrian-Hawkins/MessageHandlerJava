package com.dbb.node;

import com.dbb.node.logging.LoggerConfig;
import com.dbb.node.util.Bus;
import com.dbb.node.util.MessageHandlerScanner;
import com.dbb.node.messages.TextMessage;

import java.util.logging.*;


public class App {

    private static final Logger logger = LoggerConfig.getLogger(App.class);


    public static void main(String[] args) {
        try {
            Bus bus = new Bus();
            MessageHandlerScanner.scanForHandlers("com.dbb.node.handlers");


             TextMessage msg = new TextMessage("Hello, World!");
             bus.Send(msg);
             logger.info("jeererwe");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
