package com.dbb.node;

import com.dbb.node.logging.LoggerConfig;
import com.dbb.node.util.Bus;
import com.dbb.node.util.MessageHandlerScanner;
import com.dbb.node.messages.TextMessage;
import com.dbb.node.util.RabbitMQHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;


public class App {

//    private static final Logger logger = LoggerConfig.getLogger(App.class);


    public static void main(String[] args) {
        try {
            MessageHandlerScanner.scanForHandlers("com.dbb.node.handlers");
            Bus bus = new Bus();
            TextMessage msg = new TextMessage("Some test");
            RabbitMQHandler rabbitMQHandler = new RabbitMQHandler();
            String[] queuesToListen = {"IMAGE_MESSAGE", "TEXT_MESSAGE"};
            rabbitMQHandler.listenToQueuesIndefinitely(queuesToListen);
            rabbitMQHandler.sendMessage("TEXT_MESSAGE", bus.serialize(msg));
            rabbitMQHandler.keepAlive();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
