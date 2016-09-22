package org.openexchange.jobs;

import org.openexchange.jpa.Sms;
import org.openexchange.service.SmsService;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.UUID;

import static org.openexchange.service.SmsService.SMS_OUTBOUND_QUEUE;

@RefreshScope
@Job(concurrencyString = "${spring.jms.producer.concurrency:1}", runMethod = "produce")
class JobSmsProducer {
    private final SmsService smsService;
    private final JobsConfiguration jobsConfiguration;

    public JobSmsProducer(SmsService smsService, JobsConfiguration jobsConfiguration) {
        this.smsService = smsService;
        this.jobsConfiguration = jobsConfiguration;
    }

    public void produce() {
        for (int i = 0; i < jobsConfiguration.getSmsWriteChunkSize(); i++) {
            smsService.write(SMS_OUTBOUND_QUEUE, new Sms(UUID.randomUUID().toString(), "+37258211717", "+37258000000", "Sample text"));
        }
    }
}
