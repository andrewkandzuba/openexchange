package org.openexchange.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

@Configuration
@RefreshScope
@EnableJms
@EnableTransactionManagement
public class JmsActiveMQAutoConfiguration {
    @Value("${spring.jms.broker.url:tcp://127.0.0.1:61616}")
    private String brokerUrl;
    @Value("${spring.jms.broker.receive.timeout.timeUnit:SECONDS}")
    private String receiveTimeoutTimeUnit;
    @Value("${spring.jms.broker.receive.timeout.interval:1}")
    private long receiveTimeoutInterval;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connectionFactory.setObjectMessageSerializationDefered(true);
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setCopyMessageOnSend(false);
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setReceiveTimeout(TimeUnit.valueOf(receiveTimeoutTimeUnit).toMillis(receiveTimeoutInterval));
        return jmsTemplate;
    }
}
