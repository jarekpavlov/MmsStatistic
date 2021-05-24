package com.jschool;

import com.rabbitmq.client.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

@Startup
@Singleton
public class ListenerBean implements Serializable {
    private final static String QUEUE_NAME = "queue1";
    private Connection connection;
    private Channel channel;

    @Inject
    private BeanManager beanManager;

    @PostConstruct
    void init() throws TimeoutException, IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received: '" + message + "'");
                beanManager.fireEvent(message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    @PreDestroy
    public void destroy() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
