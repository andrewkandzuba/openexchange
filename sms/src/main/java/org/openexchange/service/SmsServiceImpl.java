package org.openexchange.service;

import org.openexchange.jpa.Sms;
import org.openexchange.jpa.SmsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
class SmsServiceImpl implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    private final JmsTemplate jmsTemplate;
    private final SmsRepository smsRepository;

    @Autowired
    public SmsServiceImpl(JmsTemplate jmsTemplate, SmsRepository smsRepository) {
        this.jmsTemplate = jmsTemplate;
        this.smsRepository = smsRepository;
    }

    @Override
    @Transactional(noRollbackFor = Throwable.class)
    public void write(String destination, Sms message) throws JmsException {
        jmsTemplate.convertAndSend(destination, message);
    }

    @Override
    @Transactional(noRollbackFor = Throwable.class)
    public void write(String destination, Collection<? extends Sms> messages) throws JmsException {
        messages.forEach(message -> {
            jmsTemplate.convertAndSend(destination, message);
            logger.debug("Message has been sent: [" + message + "]");
        });
    }

    @Override
    @Transactional(noRollbackFor = Throwable.class)
    public void receive(String destination, int chunkSize) throws JmsException {
        Collection<Sms> messages = new HashSet<>();
        while (chunkSize-- > 0) {
            Sms message = (Sms) jmsTemplate.receiveAndConvert(destination);
            if(message == null){
                logger.debug("No message is available");
                break;
            }
            messages.add(message);
            logger.debug("Message has been received: [" + message + "]");
        }
        smsRepository.save(messages);
    }
}
