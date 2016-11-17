package ru.sberbank.javaschool.async.transaction;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.javaschool.async.AppConfig;

import javax.jms.*;


public class TransactionProducer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Queue queue = context.getBean(Queue.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer producer = session.createProducer(queue);

            Message message1 = session.createTextMessage("My first message " + System.currentTimeMillis());
            producer.send(message1);

            Message message2 = session.createTextMessage("My second message " + System.currentTimeMillis());
            producer.send(message2);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}