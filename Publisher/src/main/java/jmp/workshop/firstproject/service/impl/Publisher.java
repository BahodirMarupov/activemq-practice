package jmp.workshop.firstproject.service.impl;

import static jmp.workshop.firstproject.common.Properties.TOPIC_NAME;
import static jmp.workshop.firstproject.common.Properties.VIRTUAL_TOPIC_NAME;

import jmp.workshop.firstproject.service.IPublisher;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class Publisher implements IPublisher {

  private final Logger logger = LoggerFactory.getLogger(Publisher.class);
  private final JmsTemplate jmsTopicTemplate;
  private final JmsTemplate jmsQueueTemplate;

  public Publisher(@Qualifier("JmsTopicTemplate") JmsTemplate jmsTopicTemplate,
      @Qualifier("JmsQueueTemplate") JmsTemplate jmsQueueTemplate) {
    this.jmsTopicTemplate = jmsTopicTemplate;
    this.jmsQueueTemplate = jmsQueueTemplate;
  }

  @Override
  public void sendMessageToTopic(String message) {
    try {
      jmsTopicTemplate.convertAndSend(TOPIC_NAME, message);
      logger.info("Message '{}' sent to topic '{}'", message, TOPIC_NAME);
      Thread.sleep(1000);
    } catch (Exception e) {
      logger.error("Exception while sending message to topic '{}' : {}", TOPIC_NAME, e.getMessage());
    }
  }

  @Override
  public void sendMessageToVirtualTopic(String message) {
    try {
      jmsTopicTemplate.convertAndSend(new ActiveMQTopic(VIRTUAL_TOPIC_NAME), message);
      logger.info("Message '{}' sent to virtual topic '{}'", message, VIRTUAL_TOPIC_NAME);
      Thread.sleep(1000);
    } catch (Exception e) {
      logger.error("Exception while sending message to topic '{}' : {}", VIRTUAL_TOPIC_NAME, e.getMessage());
    }
  }
}
