package org.openexchange.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@RefreshScope
@EnableJms
public class JmsConfiguration {
    @Value("${spring.jms.activemq.broker.url:tcp://127.0.0.1:61616}")
    private String brokerUrl;

    @Bean(name = "amqConnectionFactory")
    public ConnectionFactory amqConnectionFactory(){
        //return new ActiveMQConnectionFactory(brokerUrl, brokerUser, brokerUrl);
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    @Bean
    public CachingConnectionFactory connectionFactory(@Qualifier("amqConnectionFactory") ConnectionFactory connectionFactory){
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean(name = "jmsTemplateQuotes")
    public JmsTemplate jmsTemplateQuotes(ConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue("quotes.queue"));
        return jmsTemplate;
    }
}
