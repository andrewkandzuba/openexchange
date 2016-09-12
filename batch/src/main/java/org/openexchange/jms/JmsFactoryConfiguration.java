package org.openexchange.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

@Configuration
@RefreshScope
@EnableJms
public class JmsFactoryConfiguration {
    @Value("${spring.jms.activemq.broker.url:tcp://127.0.0.1:61616}")
    private String brokerUrl;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        factory.setTrustedPackages(Arrays.asList("org.openexchange.protocol", "java.lang", "java.util"));
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(ConnectionFactory connectionFactory) {
        return new CachingConnectionFactory(connectionFactory);
    }
}
