package org.openexchange.config;

import org.openexchange.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RefreshScope
public class RefreshRateConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RefreshRateConfiguration.class);

    @Value("${spring.cloud.config.refresh.enabled:true}")
    private boolean enabled;

    private final RefreshEndpoint refreshEndpoint;

    @Autowired
    public RefreshRateConfiguration(RefreshEndpoint refreshEndpoint) {
        this.refreshEndpoint = refreshEndpoint;
    }

    @Job(concurrencyString = "1")
    public void refreshContext() {
        if (!enabled) {
            logger.info("Configuration refresh is disabled");
            return;
        }
        logger.info("Force the refresh of the application context");
        refreshEndpoint.refresh();
    }
}