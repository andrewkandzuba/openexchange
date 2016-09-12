package org.openexchange.jms;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openexchange.protocol.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static org.openexchange.jms.JmsTemplateConfiguration.QUOTES_QUEUE;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuotesQueueTest.class)
@SpringBootApplication(exclude = {JmsFactoryConfiguration.class})
@ComponentScan(
        basePackages="org.openexchange.jms",
        excludeFilters = {
                @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                        value = {
                                JmsFactoryConfiguration.class
                        })
        })
@TestPropertySource(locations = "classpath:test.properties")
public class QuotesQueueTest {
    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();
    @Autowired
    private JmsTemplate jmsTemplate;
    private CountDownLatch received = new CountDownLatch(1);
    private volatile boolean isReceived = false;

    @Test
    public void testJmsSendWithDefaultSettings() throws Exception {
        Quote quote = new Quote();
        quote.setSource("USD");
        quote.setTarget("UAH");
        quote.setQuote(30.2);
        quote.setTimestamp(Date.from(Instant.now()));
        jmsTemplate.convertAndSend(quote);
        received.await();
        Assert.assertTrue(isReceived);
    }

    @JmsListener(destination = QUOTES_QUEUE)
    public void receiveMessage(Quote quote) {
        System.out.println("Received <" + quote + ">");
        isReceived = true;
        received.countDown();
    }
}
