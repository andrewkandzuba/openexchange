package org.openexchange.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyLayerBatchConfiguration {
    @Bean
    public ItemReader reader() {
        return new QuoteReader();
    }

    @Bean
    public ItemProcessor processor() {
        return new QuoteProcessor();
    }

    @Bean
    public ItemWriter writer() {
        return new QuoteWriter();
    }

    @Bean
    public Job job1(JobBuilderFactory jobs, @Qualifier("step1") Step step1) {
        return jobs.get("job1")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      ItemReader<Quote> reader, ItemWriter<Quote> writer,
                      ItemProcessor<Quote, Quote> processor) {
        return stepBuilderFactory.get("step1")
                .<Quote, Quote>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }
}
