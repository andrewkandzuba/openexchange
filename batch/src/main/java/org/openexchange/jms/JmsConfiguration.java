package org.openexchange.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.openexchange.protocol.Quote;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.jms.JmsItemWriter;
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
    @Value("${spring.jms.activemq.broker.url:vm://embedded-broker?create=false}")
    private String brokerUrl;

    @Bean(name = "amqConnectionFactory")
    public ConnectionFactory connectionFactory(){
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

    @Bean
    public ItemWriter writer(@Qualifier("jmsTemplateQuotes") JmsTemplate jmsTemplate) {
        JmsItemWriter<Quote> writer = new JmsItemWriter<>();
        writer.setJmsTemplate(jmsTemplate);
        return writer;
    }
}
