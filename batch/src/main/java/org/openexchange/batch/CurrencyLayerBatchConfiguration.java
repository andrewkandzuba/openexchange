package org.openexchange.batch;

import org.openexchange.integration.CurrencyLayerService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyLayerBatchConfiguration {
    @Autowired
    private CurrencyLayerService currencyLayerService;

    @Bean
    public ItemReader reader() {
        return new QuoteReader(currencyLayerService);
    }

    @Bean
    public ItemProcessor processor() {
        return new QuoteProcessor();
    }

    @Bean
    public ItemWriter writer() {
        return new QuoteWriter();
    }
}
