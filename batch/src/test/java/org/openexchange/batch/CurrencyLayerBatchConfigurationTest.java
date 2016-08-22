package org.openexchange.batch;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyLayerBatchConfigurationTest {

    /*@Bean
    public Job job1(JobBuilderFactory jobs, Step step1) {
        return jobs.get("job1")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      ItemReader reader, ItemWriter writer,
                      ItemProcessor processor) {

        return stepBuilderFactory
                .get("step1")
                .chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }*/
}
