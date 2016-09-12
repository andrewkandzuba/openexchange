package org.openexchange.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsFactoryConfigurationTest {
    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://embedded-broker?create=false");
        factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(ConnectionFactory connectionFactory) {
        return new CachingConnectionFactory(connectionFactory);
    }
}
