package jmp.workshop.firstproject.service;

public interface IPublisher {

  void sendMessageToTopic(String message);

  void sendMessageToVirtualTopic(String message);
}
