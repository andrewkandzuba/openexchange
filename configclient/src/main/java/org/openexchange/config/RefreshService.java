package org.openexchange.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshService {
    private final static Logger logger = LoggerFactory.getLogger(RefreshService.class);
    private final RefreshEndpoint refreshEndpoint;
    private final ScheduledExecutorService scheduler;
    private final RefreshRateConfiguration refreshRateConfiguration;

    @Autowired
    public RefreshService(RefreshEndpoint refreshEndpoint, ScheduledExecutorService scheduler, RefreshRateConfiguration refreshRateConfiguration) {
        this.refreshEndpoint = refreshEndpoint;
        this.scheduler = scheduler;
        this.refreshRateConfiguration = refreshRateConfiguration;
    }

    @PostConstruct
    public void start() {
        scheduler.schedule(this::refreshContext, refreshRateConfiguration.getInterval(), TimeUnit.valueOf(refreshRateConfiguration.getTimeUnit()));
    }

    private void refreshContext() {
        if(!refreshRateConfiguration.isEnabled()) {
            logger.info("Configuration refresh is disabled");
            return;
        }
        logger.info("Force the refresh of the application context");
        try {
            refreshEndpoint.refresh();
            scheduler.schedule(this::refreshContext, refreshRateConfiguration.getInterval(), TimeUnit.valueOf(refreshRateConfiguration.getTimeUnit()));
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }
}
