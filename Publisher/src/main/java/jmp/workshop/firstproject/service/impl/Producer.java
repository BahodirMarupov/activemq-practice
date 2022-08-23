package jmp.workshop.firstproject.service.impl;

import java.util.UUID;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import jmp.workshop.firstproject.common.Properties;
import jmp.workshop.firstproject.service.IProducer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Producer implements IProducer, MessageListener {

  private final ActiveMQConnectionFactory connectionFactory;
  private Queue temporaryQueue;
  private Session session;

  private MessageProducer producer;

  private MessageConsumer consumer;

  private final Logger logger = LoggerFactory.getLogger(Producer.class);

  public Producer(ActiveMQConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void onMessage(Message message) {
    try {
      logger.info("Message '{}' is replied from consumer", ((TextMessage) message).getText());
    } catch (JMSException e) {
      logger.error("Exception while reading replied message : {}", e.getMessage());
    }
  }

  @Override
  public void sendMessage(String message) {
    try {
      this.establishConnection();
      TextMessage textMessage = session.createTextMessage();
      textMessage.setText(message);
      textMessage.setJMSReplyTo(temporaryQueue);
      String correlationId = UUID.randomUUID().toString();
      textMessage.setJMSCorrelationID(correlationId);
      this.producer.send(textMessage);
      logger.info("Message '{}' is sent to queue '{}' with correlationId '{}'", message, Properties.QUEUE_NAME, correlationId);
      Thread.sleep(1000);
      this.closeConnection();
    } catch (JMSException | InterruptedException e) {
      logger.error("Exception while sending message'{}' to queue '{}' : {}", message, Properties.QUEUE_NAME, e.getMessage());
    }
  }

  private void establishConnection() throws JMSException {
    Connection connection = connectionFactory.createConnection();
    connection.start();
    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Destination requestQueue = session.createQueue(Properties.QUEUE_NAME);
    producer = session.createProducer(requestQueue);
    temporaryQueue = session.createTemporaryQueue();
    consumer = session.createConsumer(temporaryQueue);
    consumer.setMessageListener(this);
    logger.info("Connection is established");
  }

  private void closeConnection() throws JMSException {
    producer.close();
    consumer.close();
    session.close();
    logger.info("Connection is closed");
  }
}
