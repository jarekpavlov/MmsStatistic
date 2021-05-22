package com.jschool;

import com.rabbitmq.client.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Startup
@Singleton
public class ReceiverBean {
    private final static String QUEUE_NAME = "queue1";
    private List<CountByProduct> productList = null;

    @EJB
    EjbBean ejbBean;

    @Inject
    CdiBean cdiBean;

    @PostConstruct
    void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received: '" + message + "'");
                productList = ejbBean.getStatistic("30");
                cdiBean.someAction();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public List<CountByProduct> getProductList() {
        return productList;
    }
}
