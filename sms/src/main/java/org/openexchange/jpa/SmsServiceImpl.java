package org.openexchange.jpa;

import org.openexchange.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class SmsServiceImpl implements SmsService {
    private final SmsRepository smsRepository;

    @Autowired
    public SmsServiceImpl(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    @Override
    @Transactional
    public void register(Collection<Sms> notifications) {
        notifications.forEach(smsRepository::save);
    }
}
