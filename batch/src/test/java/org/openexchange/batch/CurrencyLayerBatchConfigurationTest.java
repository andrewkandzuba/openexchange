package org.openexchange.batch;

import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.protocol.Quotes;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class CurrencyLayerBatchConfigurationTest {
    @MockBean
    private ItemWriter<Quotes> itemWriter;
    @MockBean
    private CurrencyLayerService currencyLayerService;

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils(){
        return new JobLauncherTestUtils();
    }
}
