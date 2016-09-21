package org.openexchange.service;

import org.springframework.jms.JmsException;

public interface SmsReceiveService {
    void receive(String destination, int chunkSize) throws JmsException;
}
