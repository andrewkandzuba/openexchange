package org.openexchange.jms;

import org.openexchange.jpa.Sms;
import org.openexchange.jpa.SmsRepository;
import org.openexchange.service.SmsReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmsReceiveServiceImpl implements SmsReceiveService {
    private final JmsTemplate jmsTemplate;
    private final SmsRepository smsRepository;

    @Autowired
    public SmsReceiveServiceImpl(JmsTemplate jmsTemplate, SmsRepository smsRepository) {
        this.jmsTemplate = jmsTemplate;
        this.smsRepository = smsRepository;
    }

    @Override
    @Transactional(transactionManager = "smsTransactionManager")
    public void receive(String destination, int chunkSize) throws JmsException {
        while (chunkSize-- > 0) {
            Sms sms = (Sms) jmsTemplate.receiveAndConvert(destination);
            smsRepository.save(sms);
        }
    }
}
