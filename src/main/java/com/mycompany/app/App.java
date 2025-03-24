package com.mycompany.app;

import com.mycompany.app.logging.*;
import com.mycompany.app.messages.TextMessage;
import com.mycompany.app.util.Bus;
import com.mycompany.app.util.MessageHandlerScanner;

import java.util.logging.*;


public class App {

    private static final Logger logger = LoggerConfig.getLogger(App.class);


    public static void main(String[] args) {
        try {
            Bus bus = new Bus();
            MessageHandlerScanner.scanForHandlers("com.mycompany.app.handlers");


             TextMessage msg = new TextMessage("Hello, World!");
             bus.Send(msg);
             logger.info("jeererwe");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
