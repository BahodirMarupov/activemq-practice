package jmp.workshop.firstproject.config;

import javax.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class ActiveMqConfiguration {

  private final ConnectionFactory connectionFactory;

  public ActiveMqConfiguration(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Bean
  @Qualifier("JmsTopicTemplate")
  public JmsTemplate jmsTopicTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(connectionFactory);
    jmsTemplate.setPubSubDomain(true);
    return jmsTemplate;
  }

  @Bean
  @Qualifier("JmsQueueTemplate")
  public JmsTemplate jmsQueueTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(connectionFactory);
    jmsTemplate.setPubSubDomain(false);
    return jmsTemplate;
  }
}
