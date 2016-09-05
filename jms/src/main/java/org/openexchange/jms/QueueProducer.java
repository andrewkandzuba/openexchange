package org.openexchange.jms;

import org.openexchange.protocol.Quote;
import org.springframework.jms.JmsException;

public interface QueueProducer {
    void send(Quote quote) throws JmsException;
}
