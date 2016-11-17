package ru.sberbank.javaschool.async.transaction;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.javaschool.async.AppConfig;

import javax.jms.*;


public class TransactionConsumer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Queue queue = context.getBean(Queue.class);

        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();

            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

            MessageConsumer consumer = session.createConsumer(queue);

            TextMessage message1 = (TextMessage) consumer.receive();
            System.out.println(message1.getText());

//            if (true) throw new RuntimeException();

            TextMessage message2 = (TextMessage) consumer.receive();
            System.out.println(message2.getText());

            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}