package com.jschool.beans;

import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Singleton
public class ListenerBean implements Serializable {
    Logger logger = Logger.getLogger(this.getClass());

    private final static String QUEUE_NAME = "queue1";
    private Connection connection;
    private Channel channel;

    @Inject
    private BeanManager beanManager;

    public void init()  {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        } catch (Exception e) {
            logger.warn("can not connect to the MQ");
        }


        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: '" + message + "'");
                beanManager.fireEvent(message);
            }
        };
        try {
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (Exception e) {
            logger.warn("can not connect to the MQ");
        }
    }

    @PreDestroy
    public void destroy() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
