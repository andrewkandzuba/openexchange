package org.openexchange.service;

import org.openexchange.protocol.Sms;
import org.springframework.jms.JmsException;

public interface SmsService {
    void write(Sms message) throws JmsException;
}
