package org.openexchange.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BatchJobScheduler {
    private final static Logger logger = LoggerFactory.getLogger(BatchJobScheduler.class);
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final ApplicationContext appContext;

    @Autowired
    public BatchJobScheduler(JobLauncher jobLauncher,
                                       JobExplorer jobExplorer,
                                       ApplicationContext appContext) {
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
        this.appContext = appContext;
    }

    @Scheduled(fixedRateString = "${spring.batch.job.restart.interval.milliseconds:60000}")
    public void findAndRunJobs() throws JobRestartException, JobParametersInvalidException, JobInstanceAlreadyCompleteException {
        Map<String, Job> jobs = appContext.getBeansOfType(Job.class);
        jobs.forEach((s, job) -> {
            try {
               retry(job);
            } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    void retry(Job job) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        logger.info("Relaunch the job: [" + job.getName() + "]");
        List<JobInstance> jobInstances = jobExplorer.findJobInstancesByJobName(job.getName(), 0, 1);
        if(!jobInstances.isEmpty()){
            List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstances.get(0));
            if(!jobExecutions.isEmpty()){
                JobExecution jobExecution = jobExecutions.get(0);
                if(jobExecution.isRunning()){
                    throw new JobExecutionAlreadyRunningException("A job execution for this job is already running: " + jobInstances);
                }
                JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
                if(incrementer != null){
                    jobLauncher.run(job, job.getJobParametersIncrementer().getNext(jobExecution.getJobParameters()));
                } else {
                    jobLauncher.run(job, jobExecution.getJobParameters());
                }
            }
        }
    }
}
