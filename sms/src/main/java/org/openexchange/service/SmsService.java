package org.openexchange.service;

import org.openexchange.jpa.Sms;

import java.util.Collection;

public interface SmsService {
    void register(Collection<Sms> notifications);
}
