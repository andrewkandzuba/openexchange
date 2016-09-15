package org.openexchange.batch;

import org.openexchange.protocol.Quote;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private final static String JOB1 = "job1";
    final static String STEP1 = "step1";

    @Value("${spring.batch.job.restart.interval:1}")
    private long interval;
    @Value("${spring.batch.job.restart.timeUnit:MINUTES}")
    private String timeUnit;
    @Value("${spring.batch.job.chunk.size:8}")
    private int chunkSize;

    @Bean
    public ItemReader reader() {
        return new QuoteReader();
    }

    @Bean(name = JOB1)
    public Job job1(JobBuilderFactory jobs, @Qualifier(STEP1) Step step1, ScheduledExecutorService executorService) {
        return jobs.get(JOB1)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean(name = STEP1)
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      ItemReader<Quote> reader,
                      ItemWriter<Quote> writer) {
        return stepBuilderFactory.get(STEP1)
                .<Quote, Quote>chunk(chunkSize)
                .reader(reader)
                .processor(quote -> quote)
                .writer(writer)
                .faultTolerant()
                .allowStartIfComplete(true)
                .build();
    }
}