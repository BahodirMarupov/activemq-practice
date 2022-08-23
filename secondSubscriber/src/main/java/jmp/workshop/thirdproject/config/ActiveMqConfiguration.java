package jmp.workshop.thirdproject.config;

import jmp.workshop.firstproject.common.Properties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

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
    return activeMQConnectionFactory;
  }

  @Bean("DurableJmsTopicFactory")
  public DefaultJmsListenerContainerFactory durableJmsTopicFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setPubSubDomain(true);
    factory.setSubscriptionDurable(true);
    factory.setClientId(Properties.DURABLE_SUB_CLIENT_ID);
    return factory;
  }

  @Bean("JmsQueueFactory")
  public DefaultJmsListenerContainerFactory jmsQueueFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    return factory;
  }

}
