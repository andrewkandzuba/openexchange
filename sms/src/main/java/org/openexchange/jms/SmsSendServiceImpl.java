package org.openexchange.jms;

import org.openexchange.jpa.Sms;
import org.openexchange.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmsSendServiceImpl implements SmsSendService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public SmsSendServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @Transactional(transactionManager = "smsTransactionManager")
    public void write(String destination, Sms message) throws JmsException {
        jmsTemplate.convertAndSend(destination, message);
    }

    @Override
    @Transactional(transactionManager = "smsTransactionManager")
    public void write(String destination, List<? extends Sms> messages) throws JmsException {
        messages.forEach(o -> jmsTemplate.convertAndSend(destination, o));
    }
}
