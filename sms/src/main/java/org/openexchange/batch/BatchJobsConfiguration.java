package org.openexchange.batch;

import org.openexchange.jpa.Sms;
import org.openexchange.service.SmsReceiveService;
import org.openexchange.service.SmsSendService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class BatchJobsConfiguration {
    private static final String SMS_QUEUE = "sms.outbound.queue";
    private static final int MAX_WRITE_CHUNK_SIZE = 100;
    private static final int MAX_READ_CHUNK_SIZE = 10;

    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public BatchJobsConfiguration(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Bean
    public ItemReader<Sms> smsItemReader() {
        return new AbstractItemStreamItemReader<>() {
            private volatile int currentIndex = 0;
            @Override
            public Sms read() throws Exception {
                if (currentIndex-- > 0) {
                    return new Sms(UUID.randomUUID().toString(), "+37258211717", "+37258000000", "Sample text");
                }
                return null;
            }

            @Override
            public void open(ExecutionContext executionContext) {
                currentIndex = MAX_WRITE_CHUNK_SIZE;
            }
        };
    }

    @Bean
    public ItemWriter<Sms> smsItemWriter(SmsSendService smsSendService) {
        return new AbstractItemStreamItemWriter<>() {
            @Override
            public void write(List<? extends Sms> list) throws Exception {
                smsSendService.write(SMS_QUEUE, list);
            }
        };
    }

    @Bean(name = "sms_generate_job")
    public Job job1(JobBuilderFactory jobs, @Qualifier("sms_generate_step") Step step1) {
        return jobs.get("sms_generate_job")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean(name = "sms_generate_step")
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      ItemReader<Sms> reader,
                      ItemWriter<Sms> writer) {
        return stepBuilderFactory.get("sms_generate_step")
                .<Sms, Sms>chunk(MAX_READ_CHUNK_SIZE)
                .reader(reader)
                .processor(sms -> sms)
                .writer(writer)
                .faultTolerant()
                .allowStartIfComplete(true)
                .build();
    }

    @Bean(name = "incoming_sms_read_job")
    public Job job2(JobBuilderFactory jobs, @Qualifier("incoming_sms_read_step") Step step2) {
        return jobs.get("incoming_sms_read_job")
                .incrementer(new RunIdIncrementer())
                .start(step2)
                .build();
    }

    @Bean(name = "incoming_sms_read_step")
    public Step step2(StepBuilderFactory stepBuilderFactory, SmsReceiveService smsReceiveService) {
        return stepBuilderFactory.get("incoming_sms_read_step").tasklet((stepContribution, chunkContext) -> {
            Collection<Sms> messages = smsReceiveService.receive(SMS_QUEUE, MAX_READ_CHUNK_SIZE);
            return RepeatStatus.CONTINUABLE;
        }).taskExecutor(new ConcurrentTaskScheduler(scheduledExecutorService)).allowStartIfComplete(true).build();
    }
}
