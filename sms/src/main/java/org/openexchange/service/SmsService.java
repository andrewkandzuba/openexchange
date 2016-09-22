package org.openexchange.service;

import org.openexchange.jpa.Sms;
import org.springframework.jms.JmsException;

import java.util.Collection;

public interface SmsService {
    String SMS_OUTBOUND_QUEUE = "sms.outbound.queue";

    void write(String destination, Sms message) throws JmsException;
    void write(String destination, Collection<? extends Sms> messages) throws JmsException;
    void receive(String destination, int chunkSize) throws JmsException;
}
