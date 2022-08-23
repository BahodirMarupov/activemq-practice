package jmp.workshop.firstproject.config;

import jmp.workshop.firstproject.common.Properties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class ActiveMqConfiguration {

  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Value("${spring.activemq.user}")
  private String username;

  @Value("${spring.activemq.password}")
  private String password;

  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);
    activeMQConnectionFactory.setUserName(username);
    activeMQConnectionFactory.setPassword(password);
    activeMQConnectionFactory.setClientID(Properties.CLIENT_ID);
    return activeMQConnectionFactory;
  }

  @Bean
  @Qualifier("JmsTopicTemplate")
  public JmsTemplate jmsTopicTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(connectionFactory());
    jmsTemplate.setPubSubDomain(true);
    return jmsTemplate;
  }

  @Bean
  @Qualifier("JmsQueueTemplate")
  public JmsTemplate jmsQueueTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(connectionFactory());
    jmsTemplate.setPubSubDomain(false);
    return jmsTemplate;
  }
}
