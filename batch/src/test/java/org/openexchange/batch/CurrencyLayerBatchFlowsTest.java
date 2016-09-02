package org.openexchange.batch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.protocol.Currencies;
import org.openexchange.protocol.Quotes;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CurrencyLayerBatchFlowsTest {
    @MockBean
    private CurrencyLayerService currencyLayerService;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private QuoteTasklet quoteTasklet;
    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    private CountDownLatch counter = new CountDownLatch(10);

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testChuckSize() throws Exception {
        Currencies currencies = new Currencies();
        currencies.setCurrencies(Map.of(
                "USD", "United States Dollar",
                "EUR", "European Euro",
                "UAH", "Ukrainian Hryvnya",
                "RUR", "Russian Rubles"));
        when(currencyLayerService.all()).thenReturn(currencies);

        Quotes quotes = new Quotes();
        quotes.setSuccess(true);
        quotes.setSource("USD");
        quotes.setQuotes(Map.of(
                "USDUSD", 1.00,
                "USDEUR", 0.90,
                "USDUAH", 0.3,
                "USDRUR", 0.15,
                "USDUAD", 0.004,
                "USDCAD", 1.04,
                "USDGBP", 1.75));
        when(currencyLayerService.live(Mockito.anyListOf(String.class))).thenReturn(quotes);
        Assert.assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep("step1").getStatus());
    }

    @Test
    public void testFailure() throws Exception {
        when(currencyLayerService.all()).thenThrow(new IllegalStateException("Service unavailable"));
        Assert.assertEquals(BatchStatus.FAILED, jobLauncherTestUtils.launchStep("step1").getStatus());
    }

    @Test
    public void testRetryOnError() throws Exception {
        Step step = stepBuilderFactory.get("step2").tasklet(quoteTasklet).build();
        Job job = jobBuilderFactory.get("job2").flow(step).end().build();
        Assert.assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.getJobLauncher().run(job, new JobParameters()).getStatus());
    }

    @Test
    public void testScheduleTask() throws Exception {
        Currencies currencies = new Currencies();
        currencies.setCurrencies(Map.of(
                "USD", "United States Dollar",
                "EUR", "European Euro",
                "UAH", "Ukrainian Hryvnya",
                "RUR", "Russian Rubles"));
        when(currencyLayerService.all()).thenReturn(currencies);

        Quotes quotes = new Quotes();
        quotes.setSuccess(true);
        quotes.setSource("USD");
        quotes.setQuotes(Map.of(
                "USDUSD", 1.00,
                "USDEUR", 0.90,
                "USDUAH", 0.3,
                "USDRUR", 0.15,
                "USDUAD", 0.004,
                "USDCAD", 1.04,
                "USDGBP", 1.75));
        when(currencyLayerService.live(Mockito.anyListOf(String.class))).thenReturn(quotes);
        scheduledExecutorService.schedule(this::repeatBatch, 1, TimeUnit.SECONDS);
        counter.await();
    }

    private void repeatBatch(){
        try {
            jobLauncherTestUtils.launchJob();
            counter.countDown();
            scheduledExecutorService.schedule(this::repeatBatch, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
