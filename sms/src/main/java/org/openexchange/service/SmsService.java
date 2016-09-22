package org.openexchange.service;

import org.openexchange.jpa.Sms;
import org.springframework.jms.JmsException;

import java.util.Collection;

public interface SmsService {
    String SMS_OUTBOUND_QUEUE = "sms.outbound.queue";

    void write(Sms message) throws JmsException;
    void write(Collection<? extends Sms> messages) throws JmsException;
    void receive(int chunkSize) throws JmsException;
}
