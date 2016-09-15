package org.openexchange.batch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@EnableRetry
class RetryTasklet implements Tasklet {
    private static final Logger log = Logger.getLogger(RetryTasklet.class);

    private final ThreadLocal<Integer> attempts;
    private final int maxInt;

    public RetryTasklet(@Value("${maxInt:5}") int maxInt) {
        this.maxInt = maxInt;
        this.attempts = new ThreadLocal<>();
        this.attempts.set(0);
    }

    @Override
    @Retryable(value = {RuntimeException.class}, maxAttempts = 10)
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        int currentAttempts = attempts.get();
        attempts.set(currentAttempts + 1);
        log.info("Attempt number " + currentAttempts);
        if (currentAttempts < maxInt) {
            throw new RuntimeException("I failed the test!");
        }
        log.debug("Finished!");
        return RepeatStatus.FINISHED;
    }
}
