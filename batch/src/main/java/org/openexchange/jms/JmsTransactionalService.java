package org.openexchange.jms;

import org.openexchange.protocol.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class JmsTransactionalService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsTransactionalService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional(rollbackFor = Throwable.class)
    void write(List<? extends Quote> list) throws JmsException {
        list.forEach(jmsTemplate::convertAndSend);
    }
}
