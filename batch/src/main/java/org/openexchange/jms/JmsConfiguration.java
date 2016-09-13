package org.openexchange.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.openexchange.protocol.Quote;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

@Configuration
@RefreshScope
@EnableJms
@EnableTransactionManagement
public class JmsConfiguration {
    static final String QUOTES_QUEUE = "quotes.queue";

    @Value("${spring.jms.broker.url:tcp://127.0.0.1:61616}")
    private String brokerUrl;
    @Value("${spring.jms.broker.receive.timeout.timeUnit:SECONDS}")
    private String receiveTimeoutTimeUnit;
    @Value("${spring.jms.broker.receive.timeout.interval:10}")
    private long receiveTimeoutInterval;


    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connectionFactory.setObjectMessageSerializationDefered(true);
        connectionFactory.setCopyMessageOnSend(false);
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager() {
        return new JmsTransactionManager(jmsConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(JmsConfiguration.QUOTES_QUEUE));
        jmsTemplate.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setReceiveTimeout(TimeUnit.valueOf(receiveTimeoutTimeUnit).toMillis(receiveTimeoutInterval));
        return jmsTemplate;
    }

    @Bean
    public ItemWriter writer(JmsTemplate jmsTemplate) {
        JmsItemWriter<Quote> writer = new JmsItemWriter<>();
        writer.setJmsTemplate(jmsTemplate);
        return writer;
    }
}
