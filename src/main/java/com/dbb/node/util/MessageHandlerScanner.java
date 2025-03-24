package com.dbb.node.util;

import com.dbb.node.annotations.Injectable;
import com.dbb.node.annotations.MessageHandler;
import com.dbb.node.base.SagaBase;
import com.dbb.node.logging.LoggerConfig;
import com.dbb.node.annotations.IHandleMessages;
import com.dbb.node.annotations.IHandle;
import com.dbb.node.base.MessageBase;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;


public class MessageHandlerScanner {

    private static final Logger logger = LoggerConfig.getLogger(SagaBase.class);
    private static final Map<String, MessageHandler> handlerRegistry = new HashMap<>();

    /**
     * Calls the appropriate handler for a given message type
     *
     * @param QueueName The name of the message type (e.g., "TEXT_MESSAGE")
     * @param message   The message object
     */
    public static void callHandler(String QueueName, MessageBase message) throws Exception {
        MessageHandler handler = handlerRegistry.get(QueueName);
        handler.handle(message);
    }

    /**
     * Scans all classes in the specified package for message handlers and builds the handler registry.
     * @param packageName The base package to scan (e.g., "com.example")
     */
    public static void scanForHandlers(String packageName) throws Exception {
        logger.info(String.format("Looking for handlers in package: %s", packageName));
        handlerRegistry.clear();

        List<Class<?>> classes = findAllClassesInPackage(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(IHandleMessages.class)) {
                SagaBase handlerInstance;
                try {
                    handlerInstance = (SagaBase) clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    logger.severe("Failed to instantiate handler class " + clazz.getName() + ": " + e.getMessage());
                    e.printStackTrace();
                    continue;
                }

                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Injectable.class)) {
                        field.setAccessible(true);
                        Object fieldInstance = field.getType().getDeclaredConstructor().newInstance();
                        field.set(handlerInstance, fieldInstance);
                        logger.fine(String.format("Please inject this dynamically: %s", field.getName()));
                    }
                }
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(IHandle.class)) {
                        IHandle handleAnnotation = method.getAnnotation(IHandle.class);

                        MessageHandler handler = (msg) -> method.invoke(handlerInstance, msg);
                        Class<?> messageType = handleAnnotation.MessageType();
                        String QueueName = messageType.getSimpleName()
                                .replaceAll("([A-Z])", "_$1")
                                .toUpperCase().replaceFirst("^_", "");
                        logger.info(String.format("Registering MessageHandler for: %s", QueueName));
                        handlerRegistry.put(QueueName
                                , handler);
                    }
                }
            }
        }
    }

    /**
     * Finds all classes in a package and its subpackages.
     * @param packageName The base package name
     * @return List of classes found
     */
    private static List<Class<?>> findAllClassesInPackage(String packageName) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    /**
     * Recursively finds all classes in a directory.
     * @param directory The directory to search
     * @param packageName The package name for classes found
     * @return List of classes found
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    try {
                        classes.add(Class.forName(className));
                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
                        // Skip classes that can't be loaded
                    }
                }
            }
        }

        return classes;
    }
}