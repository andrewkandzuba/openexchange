package org.openexchange.jobs;

import org.openexchange.protocol.Sms;
import org.openexchange.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Configuration
@RefreshScope
public class JobsConfiguration {
    @Value("${sms.outbound.queue.write.chunk.size:100}")
    private int smsWriteChunkSize;
    private final SmsService smsService;

    @Autowired
    public JobsConfiguration(SmsService smsService) {
        this.smsService = smsService;
    }

    @Job(concurrencyString = "${spring.jms.producer.concurrency:1}")
    public void jobSmsProducer() {
        for (int i = 0; i < smsWriteChunkSize; i++) {
            smsService.write(new Sms()
                    .withMessageId(UUID.randomUUID())
                    .withMobileOriginate("+37258211717")
                    .withMobileTerminate("+37258000000")
                    .withText("Sample text")
                    .withReceiveTime(Date.from(Instant.now())));
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory){
        return new JmsTransactionManager(connectionFactory);
    }
}
