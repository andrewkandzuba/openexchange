package org.openexchange.service;

import org.openexchange.protocol.Mapping;
import org.openexchange.protocol.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    private static final String queueName = Mapping.queues.get(Sms.class);
    private final JmsTemplate jmsTemplate;

    @Autowired
    public SmsServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @Transactional(noRollbackFor = Throwable.class)
    public void write(Sms message) throws JmsException {
        logger.debug("Message has been sent: [" + message + "]");
        jmsTemplate.convertAndSend(queueName, message);
    }
}
