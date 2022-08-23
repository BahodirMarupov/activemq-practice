package jmp.workshop.firstproject;

import javax.annotation.PostConstruct;
import jmp.workshop.firstproject.service.IProducer;
import jmp.workshop.firstproject.service.IPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublisherApp {

  private final IPublisher publisher;
  private final IProducer producer;

  public PublisherApp(IPublisher publisher, IProducer producer) {
    this.publisher = publisher;
    this.producer = producer;
  }

  public static void main(String[] args) {
    SpringApplication.run(PublisherApp.class, args);
  }

  @PostConstruct
  public void run(){
    //Task 1 (to test durability of subscribers, need run publisher service firstly )
    publisher.sendMessageToTopic("first message");
    publisher.sendMessageToTopic("second message");

    //Task 2
    producer.sendMessage("Hello!");
  }
}
