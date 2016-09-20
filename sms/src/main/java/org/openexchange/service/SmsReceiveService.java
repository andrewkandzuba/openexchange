package org.openexchange.service;

import org.openexchange.jpa.Sms;
import org.springframework.jms.JmsException;

import java.util.Collection;

public interface SmsReceiveService {
    Collection<Sms> receive(String destination, int chunkSize) throws JmsException;
}
