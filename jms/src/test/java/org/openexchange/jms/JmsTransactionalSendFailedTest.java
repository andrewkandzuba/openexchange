package org.openexchange.jms;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openexchange.protocol.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Date;

import static org.openexchange.jms.JmsQuotesTransactionalService.QUOTES_QUEUE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmsTransactionalSendFailedTest.class)
@SpringBootApplication
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class JmsTransactionalSendFailedTest {
    private static final Logger logger = LoggerFactory.getLogger(JmsTransactionalSendFailedTest.class);
    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();
    @Autowired
    private JmsTemplate jmsTemplate;

    @Test(expected = JmsTransactionalException.class)
    @Transactional(rollbackFor = JmsTransactionalException.class)
    public void transactionRollback() throws InterruptedException, JmsTransactionalException {
        Quote q1 = new Quote();
        q1.setSource("USD");
        q1.setTarget("UAH");
        q1.setQuote(30.2);
        q1.setTimestamp(Date.from(Instant.now()));

        Quote q2 = new Quote();
        q2.setSource("USD");
        q2.setTarget("EUR");
        q2.setQuote(0.93);
        q2.setTimestamp(Date.from(Instant.now()));

        jmsTemplate.convertAndSend(QUOTES_QUEUE, q1);
        failure();
        jmsTemplate.convertAndSend(QUOTES_QUEUE, q2);
    }

    @BeforeTransaction
    void beforeTransaction() {
        logger.info("BeforeTransaction");
    }

    @AfterTransaction
    void afterTransaction() {
        logger.info("After transaction");
        Assert.isNull(jmsTemplate.receive(QUOTES_QUEUE));
    }

    private void failure() throws JmsException {
        throw new JmsTransactionalException("Something went wrong!!!");
    }

    private static class JmsTransactionalException extends JmsException {
        JmsTransactionalException(String msg) {
            super(msg);
        }
    }
}
