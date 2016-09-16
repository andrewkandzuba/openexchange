package org.openexchange.jms;

import org.openexchange.protocol.Quote;
import org.springframework.jms.JmsException;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

public abstract class JmsUtilsTest {
    static final String TEST_QUOTE_QUEUE = "test.quote.queue";

    private void failure() throws JmsException {
        throw new UncategorizedJmsException("Something went wrong!!!");
    }

    @Transactional(rollbackFor = JmsException.class, propagation = Propagation.REQUIRES_NEW)
    void failedToSend(JmsTemplate jmsTemplate, String destination) {
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

        jmsTemplate.convertAndSend(destination, q1);
        failure();
        jmsTemplate.convertAndSend(destination, q2);
    }

    @Transactional(rollbackFor = JmsException.class, propagation = Propagation.REQUIRES_NEW)
    void successToSend(JmsTemplate jmsTemplate, String destination) {
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

        jmsTemplate.convertAndSend(destination, q1);
        jmsTemplate.convertAndSend(destination, q2);
    }

    @Transactional(rollbackFor = JmsException.class, propagation = Propagation.REQUIRES_NEW)
    void failedRead(JmsTemplate jmsTemplate, String destination) {
        jmsTemplate.receiveAndConvert(destination);
        failure();
        jmsTemplate.receiveAndConvert(destination);
    }
}
