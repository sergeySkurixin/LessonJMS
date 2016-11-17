package ru.sberbank.javaschool.async.topicexample;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.javaschool.async.AppConfig;

import javax.jms.*;


public class PoisonPillProducer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);

            Message testMessage = session.createTextMessage("Test");
            producer.send(testMessage);

            Message poisonPill = session.createMessage();
            // Обратить внимание на то, что poisonPill - сообщение без тела, только свойства и заголовки.
            poisonPill.setBooleanProperty("stop", true);
            producer.send(poisonPill);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}