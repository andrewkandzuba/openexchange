package org.openexchange.service;

import org.openexchange.jpa.SmsEntity;
import org.openexchange.jpa.SmsRepository;
import org.openexchange.protocol.Mapping;
import org.openexchange.protocol.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

@Service
class SmsServiceImpl implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    private static final String queueName = Mapping.queues.get(Sms.class);
    private final JmsTemplate jmsTemplate;
    private final SmsRepository smsRepository;

    @Autowired
    public SmsServiceImpl(JmsTemplate jmsTemplate, SmsRepository smsRepository) {
        this.jmsTemplate = jmsTemplate;
        this.smsRepository = smsRepository;
    }

    @Override
    @Transactional(noRollbackFor = Throwable.class, rollbackFor = SQLException.class)
    public void receive(int chunkSize) throws JmsException {
        Collection<SmsEntity> messages = new HashSet<>();
        while (chunkSize-- > 0) {
            Sms message = (Sms) jmsTemplate.receiveAndConvert(queueName);
            if (message == null) {
                logger.debug("No message is available");
                break;
            }
            messages.add(transform(message));
            logger.debug("Message has been received: [" + message + "]");
        }
        smsRepository.save(messages);
    }

    private SmsEntity transform(Sms sms) {
        return new SmsEntity(
                sms.getMessageId().toString(),
                sms.getMobileOriginate(),
                sms.getMobileTerminate(),
                sms.getText(),
                sms.getReceiveTime());
    }
}
