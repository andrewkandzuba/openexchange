package org.openexchange.batch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.protocol.Currencies;
import org.openexchange.protocol.Quotes;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.openexchange.batch.BatchConfiguration.STEP1;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyLayerBatchFlowsTest.class)
@SpringBootApplication
@ComponentScan(basePackages = {"org.openexchange.batch", "org.openexchange.integration", "org.openexchange.config"})
@TestPropertySource(locations = "classpath:test.properties")
public class CurrencyLayerBatchFlowsTest {
    @Autowired
    private ItemWriter<Quotes> itemWriter;
    @Autowired
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
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobOperator jobOperator;
    private CountDownLatch counter = new CountDownLatch(10);

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        doNothing().when(itemWriter).write(anyListOf(Quotes.class));
    }

    @Test
    public void testChunkSize() throws Exception {
        Currencies currencies = new Currencies();
        currencies.setCurrencies(Map.of(
                "USD", "United States Dollar",
                "EUR", "European Euro",
                "UAH", "Ukrainian Hryvnya",
                "RUR", "Russian Rubles"));
        when(currencyLayerService.all()).thenReturn(currencies);

        Quotes quotes = new Quotes();
        quotes.setSuccess(true);
        quotes.setTimestamp(System.nanoTime());
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
        Assert.assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep(STEP1).getStatus());
    }

    @Test
    public void testFailure() throws Exception {
        when(currencyLayerService.all()).thenThrow(new IllegalStateException("Service unavailable"));
        Assert.assertEquals(BatchStatus.FAILED, jobLauncherTestUtils.launchStep(STEP1).getStatus());
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
        quotes.setTimestamp(System.nanoTime());
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

    private void repeatBatch() {
        jobExplorer.getJobNames().forEach(s -> {
            try {
                List<JobInstance> jobInstances = jobExplorer.getJobInstances(s, 0, 1);// this will get one latest job from the database
                if (!CollectionUtils.isEmpty(jobInstances)) {
                    JobInstance jobInstance = jobInstances.get(0);
                    List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
                    if (!CollectionUtils.isEmpty(jobExecutions)) {
                        for (JobExecution execution : jobExecutions) {
                            // If the job status is STARTED then update the status to FAILED and restart the job using JobOperator.java
                            if (execution.getStatus().equals(BatchStatus.STARTED)) {
                                execution.setEndTime(Date.from(Instant.now()));
                                execution.setStatus(BatchStatus.FAILED);
                                execution.setExitStatus(ExitStatus.FAILED);
                                jobRepository.update(execution);
                            }
                        }
                    }
                }
            } catch (Throwable ignored) {
            }
        });
        try {
            jobLauncherTestUtils.launchJob();
            counter.countDown();
            scheduledExecutorService.schedule(this::repeatBatch, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
