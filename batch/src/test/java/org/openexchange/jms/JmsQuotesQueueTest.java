package org.openexchange.jms;

import org.apache.activemq.command.ActiveMQQueue;
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

import javax.jms.ConnectionFactory;
import java.time.Instant;
import java.util.Date;

import static org.openexchange.jms.JmsFactoryConfigurationTest.TEST_QUOTES_QUEUE;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmsQuotesQueueTest.class)
@SpringBootApplication(exclude = {JmsFactoryConfiguration.class})
@ComponentScan(
        basePackages="org.openexchange.jms",
        excludeFilters = {
                @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                        value = {
                                JmsFactoryConfiguration.class,
                                JmsTemplateConfiguration.class
                        })
        })
@TestPropertySource(locations = "classpath:test.properties")
public class JmsQuotesQueueTest {
    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();
    @Autowired
    private ConnectionFactory cachingConnectionFactory;

    @Test
    public void testJmsSendWithDefaultSettings() throws Exception {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(TEST_QUOTES_QUEUE));

        Quote quote = new Quote();
        quote.setSource("USD");
        quote.setTarget("UAH");
        quote.setQuote(30.2);
        quote.setTimestamp(Date.from(Instant.now()));

        jmsTemplate.convertAndSend(quote);
        Quote o = (Quote) jmsTemplate.receiveAndConvert(TEST_QUOTES_QUEUE);
        Assert.assertNotNull(o);
        Assert.assertEquals("USD", o.getSource());
        Assert.assertEquals("UAH", o.getTarget());
        Assert.assertEquals(Double.valueOf(30.2), o.getQuote());
        Assert.assertNotNull(o.getTimestamp());
    }

    @Test
    public void testLostConsistency() throws Exception {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(TEST_QUOTES_QUEUE));
    }

    @JmsListener(destination = TEST_QUOTES_QUEUE)
    public void receiveMessage(Quote quote) {
        System.out.println("Received <" + quote + ">");
    }
}
