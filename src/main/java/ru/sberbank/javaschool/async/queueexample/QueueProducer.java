package ru.sberbank.javaschool.async.queueexample;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.javaschool.async.AppConfig;
import ru.sberbank.javaschool.async.exception.ProjectException;

import javax.jms.*;


public class QueueProducer {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Queue queue = new ActiveMQQueue("TESTQUEUE");

//        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
//        Queue queue = context.getBean(Queue.class);

        try (Connection connection = connectionFactory.createConnection()) {
            // Смотрим аргументы метода createSession
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);

            Message message = session.createTextMessage("My test message " + System.currentTimeMillis());
            producer.send(message);
        } catch (JMSException e) {
            throw new ProjectException(e);
        }
    }

}