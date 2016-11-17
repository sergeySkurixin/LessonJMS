package ru.sberbank.javaschool.async;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
@ComponentScan("ru.sberbank")
public class AppConfig {

    @Bean
    public ConnectionFactory jmsFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("TESTQUEUE");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("TESTTOPIC");
    }

}