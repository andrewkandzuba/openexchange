package org.openexchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class HelloConfiguration {
    @Value("${configuration.properties.language:EN}")
    private String language;

    public String getLanguage() {
        return language;
    }
}
