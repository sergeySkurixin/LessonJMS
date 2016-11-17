package ru.sberbank.javaschool.async;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ru.sberbank.javaschool.async.service.SimpleService;


@ContextConfiguration(classes = TestConfig.class)
public class SimpleServiceEmbeddedBrokerTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private SimpleService service;

    @Test
    public void testDoSomething() {
        service.doSomething();
    }

}