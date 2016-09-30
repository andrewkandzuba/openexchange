package org.openexchange.sms;

import org.openexchange.protocol.Sms;

import java.util.Collection;

public interface SmsService {
    void send(Collection<Sms> messages);
    Collection<Sms> receive(int number);
}
