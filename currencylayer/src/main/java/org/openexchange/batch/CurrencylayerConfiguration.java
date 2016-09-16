package org.openexchange.batch;

import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.jms.JmsQuotesTransactionalService;
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
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@EnableBatchProcessing
public class CurrencylayerConfiguration {
    @Value("${spring.batch.job.chunk.size:8}")
    private int chunkSize;

    @Bean
    public ItemReader reader(CurrencyLayerService currencyLayerService) {
        return new QuotesReader(currencyLayerService);
    }

    @Bean(name = "job1")
    public Job job1(JobBuilderFactory jobs, @Qualifier("step1") Step step1) {
        return jobs.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean(name = "step1")
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      ItemReader<Quote> reader,
                      ItemWriter<Quote> writer) {
        return stepBuilderFactory.get("step1")
                .<Quote, Quote>chunk(chunkSize)
                .reader(reader)
                .processor(quote -> quote)
                .writer(writer)
                .faultTolerant()
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemWriter<Quote> itemWriter(JmsQuotesTransactionalService service){
        return service::write;
    }
}