package org.openexchange.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@RefreshScope
@EnableJms
public class JmsConfiguration {
    /*
    private String jmsBrokerUrl;
    private String jmsUser;
    private String jmsPassword;

    @Bean
    public ConnectionFactory connectionFactory(){
        return new ActiveMQConnectionFactory()
    }*/

    @Bean(name = "jmsTemplateQuotes")
    public JmsTemplate jmsTemplateQuotes(ConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(new ActiveMQQueue("quotes.queue"));
        return jmsTemplate;
    }
}
