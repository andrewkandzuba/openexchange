package org.openexchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class RefreshRateConfiguration {
    @Value("${spring.cloud.config.refresh.rate:1}")
    private long rate;
    @Value("${spring.cloud.config.refresh.timeUnit:MINUTES}")
    private String timeUnit;

    public long getRate() {
        return rate;
    }

    public String getTimeUnit() {
        return timeUnit;
    }
}