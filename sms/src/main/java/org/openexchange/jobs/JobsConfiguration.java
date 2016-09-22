package org.openexchange.jobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class JobsConfiguration {
    @Value("${sms.outbound.queue.write.chunk.size:100}")
    private int smsWriteChunkSize;
    @Value("${spring.jms.subscribers.concurrency:1}")
    private int jmsSubscribersNumber;
    @Value("${sms.outbound.queue.read.chunk.size:100}")
    private int smsReadChunkSize;

    public int getSmsWriteChunkSize() {
        return smsWriteChunkSize;
    }

    public int getJmsSubscribersNumber() {
        return jmsSubscribersNumber;
    }

    public int getSmsReadChunkSize() {
        return smsReadChunkSize;
    }
}
