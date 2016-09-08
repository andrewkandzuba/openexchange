package org.openexchange.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyLayerBatchService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyLayerBatchService.class);
    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;
    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final Job job;

    @Autowired
    public CurrencyLayerBatchService(Job job, JobExplorer jobExplorer, JobOperator jobOperator, JobRepository jobRepository, JobLauncher jobLauncher) {
        this.job = job;
        this.jobExplorer = jobExplorer;
        this.jobOperator = jobOperator;
        this.jobRepository = jobRepository;
        this.jobLauncher = jobLauncher;
    }

    @PostConstruct
    private void init() {
        try {
            List<JobInstance> jobInstances = jobExplorer.getJobInstances(job.getName(), 0, 1);// this will get one latest job from the database
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
                            jobOperator.restart(execution.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Scheduled(fixedRate = 10000)
    private void repeatBatch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        logger.info("Run the batch job");
        jobLauncher.run(job, new JobParameters());
    }
}
