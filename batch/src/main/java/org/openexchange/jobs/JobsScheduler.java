package org.openexchange.jobs;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class JobsScheduler implements EmbeddedValueResolverAware {
    private static final Logger logger = LoggerFactory.getLogger(JobsScheduler.class);
    private final ScheduledExecutorService scheduledExecutorService;
    private final ApplicationContext applicationContext;
    private StringValueResolver stringValueResolver;

    @Value("${spring.jobs.jobs.restart.interval:1}")
    private long restartInterval;
    @Value("${spring.jobs.jobs.restart.timeunit:MINUTES}")
    private TimeUnit restartTimeUnit;

    @Autowired
    public JobsScheduler(ScheduledExecutorService scheduledExecutorService,
                         ApplicationContext applicationContext) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
    }

    @PostConstruct
    private void postConstruct() {
        Map<String, Object> producerBeans = applicationContext.getBeansWithAnnotation(Configuration.class);
        producerBeans.forEach((s, o) -> {
            for (Method m : MethodUtils.getMethodsWithAnnotation(o.getClass(), Job.class)) {
                String concurrencyString = m.getAnnotation(Job.class).concurrencyString();
                if (concurrencyString.contains("${")) {
                    concurrencyString = stringValueResolver.resolveStringValue(concurrencyString);
                }
                int parallelism = StringUtils.isEmpty(concurrencyString) ? Runtime.getRuntime().availableProcessors() : Integer.valueOf(concurrencyString);
                for (int i = 0; i < parallelism; i++) {
                    scheduledExecutorService.schedule(new Restartable(o, m, scheduledExecutorService, restartInterval, restartTimeUnit), restartInterval, restartTimeUnit);
                }
            }
        });
    }

    private static class Restartable implements Runnable {
        private final Object o;
        private final Method m;
        private final ScheduledExecutorService scheduledExecutorService;
        private final long restartInterval;
        private final TimeUnit restartTimeUnit;

        Restartable(Object o, Method m, ScheduledExecutorService scheduledExecutorService, long restartInterval, TimeUnit restartTimeUnit) {
            this.o = o;
            this.m = m;
            this.scheduledExecutorService = scheduledExecutorService;
            this.restartInterval = restartInterval;
            this.restartTimeUnit = restartTimeUnit;
        }

        @Override
        public void run() {
            try {
                m.invoke(o);
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            } finally {
                scheduledExecutorService.schedule(
                        new Restartable(o, m, scheduledExecutorService, restartInterval, restartTimeUnit),
                        restartInterval, restartTimeUnit);
            }
        }
    }
}
