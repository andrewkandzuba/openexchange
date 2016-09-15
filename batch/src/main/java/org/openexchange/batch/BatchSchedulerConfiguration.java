package org.openexchange.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class BatchSchedulerConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final ApplicationContext appContext;

    @Autowired
    public BatchSchedulerConfiguration(JobLauncher jobLauncher, JobExplorer jobExplorer, ApplicationContext appContext) {
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
        this.appContext = appContext;
    }

    @Scheduled(fixedRateString = "${spring.batch.job.restart.interval.milliseconds:60000}")
    public void findAndRunJobs() throws JobRestartException, JobParametersInvalidException, JobInstanceAlreadyCompleteException {
        Map<String, Job> jobs = appContext.getBeansOfType(Job.class);
        jobs.forEach((s, job) -> {
            try {
                logger.info("Relaunch the job: [" + s + "]");
                List<JobInstance> jobInstances = jobExplorer.findJobInstancesByJobName(s, 0, 1);
                if(!jobInstances.isEmpty()){
                    List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstances.get(0));
                    if(!jobExecutions.isEmpty()){
                        JobExecution jobExecution = jobExecutions.get(0);
                        if(jobExecution.isRunning()){
                            throw new JobExecutionAlreadyRunningException("A job execution for this job is already running: " + jobInstances);
                        }
                        jobLauncher.run(job, job.getJobParametersIncrementer().getNext(jobExecution.getJobParameters()));
                    }
                }
            } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }
}