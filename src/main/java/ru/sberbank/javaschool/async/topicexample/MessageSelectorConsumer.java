package ru.sberbank.javaschool.async.topicexample;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.javaschool.async.AppConfig;

import javax.jms.*;


public class MessageSelectorConsumer {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            connection.start();

//            registerConsumer(topic, session, "kind = 'fruit' OR kind = 'vegetable'");
//            registerConsumer(topic, session, "kind = 'fruit'");
            registerConsumer(topic, session, "kind = 'berry'");
//            registerConsumer(topic, session, "");

            // Waiting for "poison pill" message to stop receiving messages
            MessageConsumer consumer = session.createConsumer(topic, "stop = true");
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println("Exiting...");
                    synchronized (LOCK) {
                        LOCK.notifyAll();
                    }
                }
            });

            synchronized (LOCK) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void registerConsumer(Topic topic, Session session, final String selector) throws JMSException {
        MessageConsumer consumer = session.createConsumer(topic, selector);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("Received (" + selector + "): " + message);
            }
        });
    }
}