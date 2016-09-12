package org.openexchange.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.openexchange.protocol.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

import java.time.Instant;
import java.util.Date;

import static org.openexchange.jms.JmsFactoryConfigurationTest.TEST_QUOTES_QUEUE;

@Service
public class JmsTransactionalService {
    @Autowired
    private ConnectionFactory cachingConnectionFactory;
    private JmsTemplate jmsTemplate;

    @PostConstruct
    private void init() {
        jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(TEST_QUOTES_QUEUE));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void transactionComplete() throws Throwable {
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

        jmsTemplate.convertAndSend(q1);
        jmsTemplate.convertAndSend(q2);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void transactionFailed() throws Throwable {
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

        jmsTemplate.convertAndSend(q1);
        wrong();
        jmsTemplate.convertAndSend(q2);
    }

    private void wrong() throws Exception {
        throw new Exception("Something went wrong!!!");
    }
}
