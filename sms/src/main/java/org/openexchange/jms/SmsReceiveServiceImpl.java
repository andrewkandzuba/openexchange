package org.openexchange.jms;

import org.openexchange.jpa.Sms;
import org.openexchange.service.SmsReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
public class SmsReceiveServiceImpl implements SmsReceiveService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public SmsReceiveServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @Transactional
    public Collection<Sms> receive(String destination, int chunkSize) throws JmsException {
        Collection<Sms> messages = new HashSet<>();
        while (chunkSize-- > 0){
            messages.add((Sms) jmsTemplate.receiveAndConvert(destination));
        }
        return messages;
    }
}
