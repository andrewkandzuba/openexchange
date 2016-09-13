package org.openexchange.batch;

import org.openexchange.protocol.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private final static String JOB1 = "job1";
    final static String STEP1 = "step1";
    private final static Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Value("${spring.batch.job.restart.interval:1}")
    private long interval;
    @Value("${spring.batch.job.restart.timeUnit:MINUTES}")
    private String timeUnit;
    @Value("${spring.batch.job.chunk.size:5}")
    private int chunkSize;
    private volatile Job job;

    @Bean
    public ItemReader reader() {
        return new QuoteReader();
    }

    @Bean
    public Job job1(JobBuilderFactory jobs, @Qualifier(STEP1) Step step1, ScheduledExecutorService executorService) {
        job = jobs.get(JOB1)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .listener(new JobExecutionListenerSupport() {
                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        if (jobExecution.getExitStatus().compareTo(ExitStatus.COMPLETED) == 0) {
                            executorService.schedule(() -> {
                                try {
                                    job.execute(jobExecution);
                                } catch (Exception e) {
                                    logger.error(e.getMessage(), e);
                                }
                            }, interval, TimeUnit.valueOf(timeUnit));
                        } else {
                            logger.error(String.format("Job's execution has existed with status = %s. Unable to schedule next run", jobExecution.getExitStatus()));
                        }
                    }
                }).build();
        return job;
    }

    @Bean
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