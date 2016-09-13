package org.openexchange.jms;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openexchange.protocol.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static org.openexchange.jms.JmsConfiguration.QUOTES_QUEUE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmsTransactionSuccessTest.class)
@SpringBootApplication
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class JmsTransactionSuccessTest {
    private static final Logger logger = LoggerFactory.getLogger(JmsTransactionSuccessTest.class);
    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();
    @Autowired
    private JmsTemplate jmsTemplate;
    private CountDownLatch latch = new CountDownLatch(1);
    private volatile Quote receivedQuote;

    @Test
    @Rollback(false)
    public void testJmsSendWithDefaultSettings() throws InterruptedException {
        transaction();
    }

    @BeforeTransaction
    void beforeTransaction() {
        logger.info("BeforeTransaction");
    }

    @JmsListener(destination = QUOTES_QUEUE)
    void receiveMessage(Quote quote) {
        logger.info("Received: [ " + quote + " ]");
        receivedQuote = quote;
        latch.countDown();
    }

    @AfterTransaction
    void afterTransaction() throws InterruptedException {
        logger.info("AfterTransaction");
        latch.await();

        Assert.assertNotNull(receivedQuote);
        Assert.assertEquals("USD", receivedQuote.getSource());
        Assert.assertEquals("UAH", receivedQuote.getTarget());
        Assert.assertEquals(Double.valueOf(30.2), receivedQuote.getQuote());
        Assert.assertNotNull(receivedQuote.getTimestamp());
    }

    @Transactional
    private void transaction() {
        Quote quote = new Quote();
        quote.setSource("USD");
        quote.setTarget("UAH");
        quote.setQuote(30.2);
        quote.setTimestamp(Date.from(Instant.now()));
        jmsTemplate.convertAndSend(quote);
    }
}
