package org.openexchange.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.openexchange.protocol.Quote;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsTemplateConfiguration {
    public static final String QUOTES_QUEUE = "quotes.queue";
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(QUOTES_QUEUE));
        return jmsTemplate;
    }

    @Bean
    public ItemWriter writer(JmsTemplate jmsTemplate) {
        JmsItemWriter<Quote> writer = new JmsItemWriter<>();
        writer.setJmsTemplate(jmsTemplate);
        return writer;
    }
}
