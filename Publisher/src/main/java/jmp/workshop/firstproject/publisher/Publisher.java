package jmp.workshop.firstproject.publisher;

import static jmp.workshop.firstproject.common.Properties.TOPIC_NAME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class Publisher implements IPublisher{

  private final Logger logger = LoggerFactory.getLogger(Publisher.class);
  private final JmsTemplate jmsTopicTemplate;

  public Publisher(@Qualifier("JmsTopicTemplate") JmsTemplate jmsTopicTemplate) {
    this.jmsTopicTemplate = jmsTopicTemplate;
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
}
