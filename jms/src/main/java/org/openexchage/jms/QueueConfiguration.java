package org.openexchage.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
public class QueueConfiguration {
    @Bean
    public JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("out.quotes");
    }

    @Bean
    public QueueProducer queueProducer(JmsMessagingTemplate jmsMessagingTemplate, Queue queue){
        return new QueueProducerImpl(jmsMessagingTemplate, queue);
    }
}
