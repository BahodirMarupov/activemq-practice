package jmp.workshop.thirdproject.service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import jmp.workshop.firstproject.common.Properties;
import org.apache.activemq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

  private final Logger logger = LoggerFactory.getLogger(Consumer.class);

  @JmsListener(destination = Properties.SECOND_QUEUE_IN_VT, containerFactory = "JmsQueueFactory")
  public void receiveMessageFromVirtualTopic(Message message) {
    try {
      String text = ((TextMessage) message).getText();
      logger.info("Message '{}' received from virtual topic '{}' with consumer '{}'",
          text, Properties.VIRTUAL_TOPIC_NAME, Properties.SECOND_QUEUE_IN_VT);
    } catch (JMSException e) {
      logger.error("Exception while receiving message from virtual topic '{}' : {}", Properties.VIRTUAL_TOPIC_NAME, e.getMessage());
    }
  }
}
