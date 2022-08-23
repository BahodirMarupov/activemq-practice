package jmp.workshop.thirdproject.subscriber;

import static jmp.workshop.firstproject.common.Properties.TOPIC_NAME;

import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class Subscriber {

  private final Logger logger = LoggerFactory.getLogger(Subscriber.class);

  @JmsListener(destination = TOPIC_NAME, containerFactory = "DurableJmsTopicFactory")
  public void receiveMessage(ActiveMQTextMessage message) {
    try {
      logger.info("Message '{}' received from topic '{}'", message.getText(), TOPIC_NAME);
    } catch (JMSException e) {
      logger.error("Exception while receiving message to topic '{}' : {}", TOPIC_NAME, e.getMessage());
    }
  }
}
