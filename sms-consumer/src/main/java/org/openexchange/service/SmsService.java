package org.openexchange.service;

import org.springframework.jms.JmsException;

public interface SmsService {
    void receive(int chunkSize) throws JmsException;
}
