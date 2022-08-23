package jmp.workshop.secondproject.service;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import jmp.workshop.firstproject.common.Properties;
import org.apache.activemq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

  private final JmsTemplate jmsTemplate;

  private final Logger logger = LoggerFactory.getLogger(Consumer.class);

  public Consumer(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @JmsListener(destination = Properties.QUEUE_NAME, containerFactory = "JmsQueueFactory")
  public void receiveMessage(Message message, Session session) {
    try {
      String messageBody = ((TextMessage) message).getText();
      logger.info("Message '{}' received from queue '{}'", messageBody, Properties.QUEUE_NAME);
      TextMessage response = session.createTextMessage();
      response.setText("reply message");
      response.setJMSCorrelationID(message.getJMSCorrelationID());
      jmsTemplate.convertAndSend(message.getJMSReplyTo(), response);
      logger.info("Message '{}' replied to producer with temp queue '{}'", response.getText(), message.getJMSReplyTo());
      Thread.sleep(1000);
    } catch (JMSException | InterruptedException e) {
      logger.error("Exception while receiving message from queue '{}' : {}", Properties.QUEUE_NAME, e.getMessage());
    }
  }

  @JmsListener(destination = Properties.FIRST_QUEUE_IN_VT, containerFactory = "JmsQueueFactory")
  public void receiveMessageFromVirtualTopic(Message message) {
    try {
      String text = ((TextMessage) message).getText();
      logger.info("Message '{}' received from virtual topic '{}' with consumer '{}'",
          text, Properties.VIRTUAL_TOPIC_NAME, Properties.FIRST_QUEUE_IN_VT);
    } catch (JMSException e) {
      logger.error("Exception while receiving message from virtual topic '{}' : {}", Properties.VIRTUAL_TOPIC_NAME, e.getMessage());
    }
  }
}
