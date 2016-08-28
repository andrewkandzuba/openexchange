package org.openexchange.jms;

import org.openexchange.domain.Quote;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;

public class QueueProducerImpl implements QueueProducer {
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final Queue queue;

    public QueueProducerImpl(JmsMessagingTemplate jmsMessagingTemplate, Queue queue) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.queue = queue;
    }

    public void send(Quote quote) throws JmsException {
        jmsMessagingTemplate.convertAndSend(queue, quote);
    }
}
