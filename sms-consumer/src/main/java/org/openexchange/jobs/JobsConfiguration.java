package org.openexchange.jobs;

import org.openexchange.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RefreshScope
public class JobsConfiguration {
    @Value("${sms.outbound.queue.read.chunk.size:100}")
    private int smsReadChunkSize;
    private final SmsService smsService;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public JobsConfiguration(SmsService smsService, PlatformTransactionManager transactionManager) {
        this.smsService = smsService;
        this.transactionManager = transactionManager;
    }

    @Job
    public void jobSmsConsumer() {
        smsService.receive(smsReadChunkSize);
    }
}
