package jmp.workshop.firstproject.common;

public interface Properties {

  String TOPIC_NAME = "test_topic";
  String QUEUE_NAME = "test_queue";
  String REPLY_QUEUE_NAME = "test_reply_queue";
  String VIRTUAL_TOPIC_NAME = "VirtualTopic.virtual_topic";
  String FIRST_QUEUE_IN_VT = "Consumer.first_consumer.VirtualTopic.virtual_topic";
  String SECOND_QUEUE_IN_VT = "Consumer.second_consumer.VirtualTopic.virtual_topic";
  String DURABLE_SUB_CLIENT_ID = "durable_sub_client_id";
}
