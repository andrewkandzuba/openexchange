package org.openexchange.service;

import org.openexchange.jpa.Sms;
import org.springframework.jms.JmsException;

import java.util.List;

public interface SmsSendService {
    void write(String destination, Sms message) throws JmsException;
    void write(String destination, List<? extends Sms> messages) throws JmsException;
}
