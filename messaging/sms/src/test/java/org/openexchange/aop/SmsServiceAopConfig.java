package org.openexchange.aop;

import org.openexchange.pojos.Sms;
import org.openexchange.sms.SmsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class SmsServiceAopConfig {
    @Bean
    public SmsService smsService(){
        return new SmsService() {

            @Override
            public void send(Collection<Sms> messages) {
            }

            @Override
            public Collection<Sms> receive(int number) {
                return null;
            }
        };
    }
}
