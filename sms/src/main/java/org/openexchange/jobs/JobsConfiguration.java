package org.openexchange.jobs;

import org.openexchange.jpa.Sms;
import org.openexchange.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@RefreshScope
public class JobsConfiguration {
    @Value("${sms.outbound.queue.write.chunk.size:100}")
    private int smsWriteChunkSize;
    @Value("${sms.outbound.queue.read.chunk.size:100}")
    private int smsReadChunkSize;
    private final SmsService smsService;

    @Autowired
    public JobsConfiguration(SmsService smsService) {
        this.smsService = smsService;
    }

    @Job(concurrencyString = "${spring.jms.consumer.concurrency:1}")
    public void jobSmsProducer() {
        for (int i = 0; i < smsWriteChunkSize; i++) {
            smsService.write(new Sms(UUID.randomUUID().toString(), "+37258211717", "+37258000000", "Sample text"));
        }
    }

    @Job
    public void jobSmsConsumer() {
        smsService.receive(smsReadChunkSize);
    }
}
