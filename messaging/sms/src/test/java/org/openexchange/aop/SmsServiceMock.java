package org.openexchange.aop;

import org.openexchange.pojos.Sms;
import org.openexchange.sms.SmsService;

import java.util.Collection;

public class SmsServiceMock implements SmsService {
    private final Collection<Sms> inbox;

    public SmsServiceMock(Collection<Sms> inbox) {
        this.inbox = inbox;
    }

    @Override
    public void send(Collection<Sms> messages) {

    }

    @Override
    public Collection<Sms> receive(int number) {
        return inbox;
    }
}
