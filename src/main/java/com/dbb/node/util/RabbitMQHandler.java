package com.dbb.node.util;
import com.dbb.node.base.MessageBase;
import com.dbb.node.logging.CorrelationContext;
import com.dbb.node.logging.LoggerConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class RabbitMQHandler {
    private static final Logger logger = LoggerConfig.getLogger(RabbitMQHandler.class);

    private Connection connection;
    private Channel channel;
    private final Bus bus = new Bus();
    private static final String HOST = "localhost";
    private static final int PORT = 5672;
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    private static final int MAX_RECONNECT_ATTEMPTS = 5;
    private static final long RECONNECT_DELAY_MS = 5000; // 5 seconds

    public RabbitMQHandler() {
        connectWithRetry();
    }

    private void connectWithRetry() {
        for (int attempt = 1; attempt <= MAX_RECONNECT_ATTEMPTS; attempt++) {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(HOST);
                factory.setPort(PORT);
                factory.setUsername(USERNAME);
                factory.setPassword(PASSWORD);

                factory.setAutomaticRecoveryEnabled(true);
                factory.setNetworkRecoveryInterval(10000);

                connection = factory.newConnection();
                channel = connection.createChannel();

                logger.info(String.format("Successfully connected to RabbitMQ (Attempt %s)", attempt));
                return;
            } catch (Exception e) {
                logger.severe("Failed to establish RabbitMQ connection (Attempt {})");

                if (attempt < MAX_RECONNECT_ATTEMPTS) {
                    try {
                        Thread.sleep(RECONNECT_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
        throw new RuntimeException("Could not establish RabbitMQ connection after " + MAX_RECONNECT_ATTEMPTS + " attempts");
    }

    public void sendMessage(String queueName, String message) {
        try {
            // Declare the queue to ensure it exists
            channel.queueDeclare(queueName, false, false, false, null);

            // Publish the message
            channel.basicPublish("", queueName, null, message.getBytes());

            logger.info(String.format("Sent message to queue '%s': %s", queueName, message));
        } catch (IOException e) {
            logger.severe("Failed to send message to queue " + queueName);
        }
    }

    public void listenToQueuesIndefinitely(String[] queueNames) {
        for (String queueName : queueNames) {
            try {
                channel.queueDeclare(queueName, false, false, false, null);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    logger.info(String.format("Received message from queue '%s': %s", queueName, message));
                    try {
                        MessageBase msg = (MessageBase) bus.deserialize(message);
                        CorrelationContext.setCorrelationGuid(msg.getGuid());
                        String Queue = msg.getClass().getSimpleName()
                                .replaceAll("([A-Z])", "_$1")
                                .toUpperCase().replaceFirst("^_", "");
                        ObjectToMapConverter.printObjectAsMap(msg);
                        logger.info(String.format("Processing message for queue: %s", Queue));
                        MessageHandlerScanner.callHandler(Queue, msg);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        CorrelationContext.clear();
                    }
                };
                channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

                logger.info(String.format("Started continuous listening to queue: %s", queueName));
            } catch (IOException e) {
                logger.severe("Failed to listen to queue " + queueName);
            }
        }
    }

    public void keepAlive() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Keepalive thread interrupted");
        }
    }

    public void close() {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
            logger.info("RabbitMQ connection closed");
        } catch (IOException | TimeoutException e) {
            logger.severe("Error closing RabbitMQ connection");
        }
    }

}