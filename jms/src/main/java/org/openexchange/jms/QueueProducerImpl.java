package org.openexchange.jms;

import org.openexchange.domain.Quote;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

public class QueueProducerImpl implements QueueProducer {
    private final JmsTemplate jmsTemplate;
    private final Queue queue;

    public QueueProducerImpl(JmsTemplate jmsTemplate, Queue queue) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }

    public void send(Quote quote) throws JmsException {
        jmsTemplate.send(queue, session -> session.createTextMessage("hello queue world"));
    }
}
