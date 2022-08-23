package jmp.workshop.secondproject.config;

import javax.jms.ConnectionFactory;
import jmp.workshop.firstproject.common.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class ActiveMqConfiguration {

  private final ConnectionFactory connectionFactory;

  public ActiveMqConfiguration(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Bean("JmsTopicFactory")
  public DefaultJmsListenerContainerFactory jmsTopicFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setPubSubDomain(true);
    factory.setSubscriptionDurable(false);
    return factory;
  }

  @Bean("JmsQueueFactory")
  public DefaultJmsListenerContainerFactory jmsQueueFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    return factory;
  }

}
