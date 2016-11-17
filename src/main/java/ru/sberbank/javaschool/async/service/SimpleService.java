package ru.sberbank.javaschool.async.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class SimpleService {
    private final ConnectionFactory connectionFactory;
    private final Topic topic;

    @Autowired
    public SimpleService(ConnectionFactory connectionFactory, Topic topic) {
        this.connectionFactory = connectionFactory;
        this.topic = topic;
    }

    public void doSomething() {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);

            Message message = session.createTextMessage("My test message");
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}