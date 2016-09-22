package org.openexchange.jobs;

import org.openexchange.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.openexchange.service.SmsService.SMS_OUTBOUND_QUEUE;

@Job(concurrencyString = "${spring.jms.consumer.concurrency:-1}", runMethod = "receive")
public class JobSmsConsumer {
    private final SmsService smsService;
    private final JobsConfiguration jobsConfiguration;

    @Autowired
    public JobSmsConsumer(SmsService smsService, JobsConfiguration jobsConfiguration) {
        this.smsService = smsService;
        this.jobsConfiguration = jobsConfiguration;
    }

    public void receive() {
        smsService.receive(SMS_OUTBOUND_QUEUE, jobsConfiguration.getSmsReadChunkSize());
    }
}
