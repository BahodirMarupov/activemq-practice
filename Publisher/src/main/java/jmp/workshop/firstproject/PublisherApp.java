package jmp.workshop.firstproject;

import javax.annotation.PostConstruct;
import jmp.workshop.firstproject.publisher.IPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublisherApp {

  private final IPublisher publisher;

  public PublisherApp(IPublisher publisher) {
    this.publisher = publisher;
  }

  public static void main(String[] args) {
    SpringApplication.run(PublisherApp.class, args);
  }

  @PostConstruct
  public void run(){
    //Task 1 (to test durability of subscribers, need run publisher service firstly )
    publisher.sendMessageToTopic("first message");
    publisher.sendMessageToTopic("second message");
  }
}
