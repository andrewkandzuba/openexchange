package org.openexchange.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CurrencyLayerBatchService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyLayerBatchService.class);
    private final ScheduledExecutorService scheduler;
    private final CurrencyLayerBatchConfiguration batchServiceConfiguration;
    private final JobLauncher launcher;
    private final Job job;

    @Autowired
    public CurrencyLayerBatchService(ScheduledExecutorService scheduler,
                                     CurrencyLayerBatchConfiguration batchServiceConfiguration,
                                     JobLauncher launcher,
                                     Job job) {
        this.scheduler = scheduler;
        this.batchServiceConfiguration = batchServiceConfiguration;
        this.launcher = launcher;
        this.job = job;
    }

    @PostConstruct
    public void start() {
        scheduler.schedule(this::repeatBatch, 0, TimeUnit.valueOf(batchServiceConfiguration.getTimeUnit()));
    }

    private void repeatBatch(){
        if(!batchServiceConfiguration.isEnabled()) {
            logger.info("Batch refresh is disabled");
            return;
        }
        logger.info("Run the batch job");
        try {
            launcher.run(job, new JobParameters());
            scheduler.schedule(this::repeatBatch, batchServiceConfiguration.getInterval(), TimeUnit.valueOf(batchServiceConfiguration.getTimeUnit()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
