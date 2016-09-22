package org.openexchange.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringValueResolver;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
class JobsScheduler implements EmbeddedValueResolverAware {
    private static final Logger logger = LoggerFactory.getLogger(JobsScheduler.class);
    private final ScheduledExecutorService scheduledExecutorService;
    private final ApplicationContext applicationContext;
    private StringValueResolver stringValueResolver;

    @Value("${spring.batch.job.restart.interval:10000}")
    private long restartInterval;
    @Value("${spring.batch.job.restart.timeunit:MILLISECONDS}")
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
        Map<String, Object> producerBeans = applicationContext.getBeansWithAnnotation(Job.class);
        producerBeans.forEach((s, o) -> {
            try {
                Annotation a = o.getClass().getAnnotation(Job.class);
                Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(a);

                // Get run method
                String runMethod = (String) attributes.getOrDefault("runMethod", "");
                Method method = o.getClass().getMethod(runMethod);

                // Get parallelism level
                Integer concurrency = (Integer) attributes.getOrDefault("concurrency", -1);
                String concurrencyString = (String) attributes.getOrDefault("concurrencyString", concurrency);
                if (concurrencyString.contains("${")) {
                    concurrencyString = stringValueResolver.resolveStringValue(concurrencyString);
                }
                int parallelism = Integer.valueOf(concurrencyString);
                parallelism = (parallelism == -1) ? Runtime.getRuntime().availableProcessors() : parallelism;

                // Schedule
                for (int i = 0; i < parallelism; i++) {
                    scheduledExecutorService.schedule(() -> restart(o, method), restartInterval, restartTimeUnit);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    private void restart(Object o, Method m) {
        try {
            m.invoke(o);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        } finally {
            scheduledExecutorService.schedule(() -> restart(o, m), restartInterval, restartTimeUnit);
        }
    }
}
