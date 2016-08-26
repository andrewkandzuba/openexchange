package org.openexchage.jms;

import org.openexchage.domain.Quote;
import org.springframework.jms.JmsException;

public interface QueueProducer {
    void send(Quote quote) throws JmsException;
}
