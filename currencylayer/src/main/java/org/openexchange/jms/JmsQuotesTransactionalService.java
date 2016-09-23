package org.openexchange.jms;

import org.openexchange.protocol.Mapping;
import org.openexchange.protocol.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JmsQuotesTransactionalService {
    private static final String QUOTES_QUEUE = Mapping.queues.get(Quote.class);
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsQuotesTransactionalService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void write(List<? extends Quote> list) throws JmsException {
        list.forEach(o -> jmsTemplate.convertAndSend(QUOTES_QUEUE, o));
    }
}
