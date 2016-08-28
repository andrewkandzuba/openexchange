package org.openexchange.jms;

import org.openexchange.domain.Quote;
import org.springframework.jms.JmsException;

public interface QueueProducer {
    void send(Quote quote) throws JmsException;
}
