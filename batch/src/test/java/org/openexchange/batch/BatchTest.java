package org.openexchange.batch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchTest.class)
@SpringBootApplication
@TestPropertySource(locations = "classpath:test.properties")
public class BatchTest {
    private static final Logger logger = LoggerFactory.getLogger(BatchTest.class);
    //@Autowired
    //private ItemReader<String> reader;
    @Autowired
    private RetryTasklet retryTasklet;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private BatchJobScheduler batchJobScheduler;

    @Test
    public void testReader() throws Exception {
        ItemReader<String> reader = new StringItemReader();
        ((ItemStream)reader).open(new ExecutionContext());

        Assert.assertNotNull(reader.read());
        Assert.assertNotNull(reader.read());
        Assert.assertNotNull(reader.read());
        Assert.assertNull(reader.read());
    }

    @Test
    public void testFailure() throws Exception {
        Step step = stepBuilderFactory.get("step1").tasklet((stepContribution, chunkContext) -> {
            throw new IllegalStateException("Service unavailable");
        }).build();
        Job job = jobBuilderFactory.get("job1").flow(step).end().build();
        Assert.assertEquals(BatchStatus.FAILED, jobLauncher.run(job, new JobParameters()).getStatus());
    }

    @Test
    public void testRetryOnError() throws Exception {
        Step step = stepBuilderFactory.get("step2").tasklet(retryTasklet).build();
        Job job = jobBuilderFactory.get("job2").flow(step).end().build();
        Assert.assertEquals(BatchStatus.COMPLETED, jobLauncher.run(job, new JobParameters()).getStatus());
    }

    @Test
    public void testScheduleTask() throws Exception {
        CountDownLatch counter = new CountDownLatch(2);
        Step step = stepBuilderFactory.get("step3").tasklet((stepContribution, chunkContext) -> {
            counter.countDown();
            logger.info("Retry: " + counter.getCount());
            return null;
        }).allowStartIfComplete(true).build();
        Job job = jobBuilderFactory.get("job3").flow(step).end().build();
        jobLauncher.run(job, new JobParameters());
        batchJobScheduler.retry(job);
        Assert.assertTrue(counter.await(5000, TimeUnit.MILLISECONDS));
        Assert.assertEquals(BatchStatus.COMPLETED, jobLauncher.run(job, new JobParameters()).getStatus());
    }
}
