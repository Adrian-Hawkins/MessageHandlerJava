package com.mycompany.app;

import com.mycompany.app.base.MessageBase;
import com.mycompany.app.logging.*;
import com.mycompany.app.messages.ImageMessage;
import com.mycompany.app.messages.TextMessage;
import com.mycompany.app.util.Bus;
import com.mycompany.app.util.MessageHandlerScanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.logging.*;
import java.util.logging.Level;


public class App {

    private static final Logger logger = LoggerConfig.getLogger(App.class);

//    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
////        // Set correlation GUID for the current thread
////        CorrelationContext.setCorrelationGuid(UUID.randomUUID().toString());
////        Bus bus = new Bus();
//        Bus bus = Bus.class.getConstructor().newInstance();
//        bus.Send(new TextMessage("lasd"));
//
//        ImageMessage msg = new ImageMessage();
//        String srlzd = bus.serialize(msg);
//
//        MessageBase tmp = (MessageBase) bus.deserialize(srlzd);
//        CorrelationContext.setCorrelationGuid(tmp.getGuid());
//
//
//        logger.info("Application started");
//        logger.warning("This is a warning");
//
//        try {
//            performOperation();
//        } catch (Exception e) {
//            logger.severe("Operation failed");
//        }
//        CorrelationContext.clear();
//
//        logger.info("test");
//    }
//
//    private static void performOperation() throws Exception {
//        // Set a new correlation GUID for this operation
////        CorrelationContext.setCorrelationGuid(UUID.randomUUID().toString());
//        logger.info("Performing operation");
//
//        // Simulate error
//        throw new Exception("Something went wrong");
//    }


    public static void main(String[] args) {
        try {
            MessageHandlerScanner.scanForHandlers("com.mycompany.app.handlers");


             TextMessage msg = new TextMessage("Hello, World!");
             MessageHandlerScanner.callHandler("TEXT_MESSAGE", msg);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
