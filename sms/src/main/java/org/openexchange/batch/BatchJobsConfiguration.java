package org.openexchange.batch;

import org.openexchange.jpa.Sms;
import org.openexchange.service.SmsSendService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
public class BatchJobsConfiguration {
    private static final String SMS_QUEUE = "jpa.queue";

    @Bean
    public ItemReader<Sms> smsItemReader() {
        return new AbstractItemStreamItemReader<>() {
            @Override
            public Sms read() throws Exception {
                if (Thread.currentThread().isInterrupted()) return null;
                return new Sms(UUID.randomUUID().toString(), "+37258211717", "+37258000000", "Sample text");
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
                .<Sms, Sms>chunk(10)
                .reader(reader)
                .processor(sms -> sms)
                .writer(writer)
                .faultTolerant()
                .allowStartIfComplete(true)
                .build();
    }
}
