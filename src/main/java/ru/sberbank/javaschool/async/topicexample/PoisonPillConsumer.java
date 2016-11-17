package ru.sberbank.javaschool.async.topicexample;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.javaschool.async.AppConfig;

import javax.jms.*;


public class PoisonPillConsumer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(topic);

            connection.start();
            do {
                Message message = consumer.receive();
                System.out.println("Received message: " + message);
                if (message.getBooleanProperty("stop")) {
                    break;
                }
            } while (true);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}