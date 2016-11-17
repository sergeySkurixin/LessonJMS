package ru.sberbank.javaschool.async.topicexample;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.javaschool.async.AppConfig;
import ru.sberbank.javaschool.async.exception.ProjectException;

import javax.jms.*;


public class TopicProducer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);

            String text = "My test message " + System.currentTimeMillis();
            Message message = session.createTextMessage(text);
            producer.send(message);

            System.out.println("Sended message: " + text);
        } catch (JMSException e) {
            throw new ProjectException(e);
        }
    }
}