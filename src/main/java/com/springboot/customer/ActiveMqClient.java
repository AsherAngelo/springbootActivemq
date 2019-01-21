package com.springboot.customer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqClient {

//    @JmsListener(destination = "zhaomengjie")
//    public void receiveTopic(String message) {
//        System.out.println("监听topic=============监听topic");
//        System.out.println(message);
//
//    }
//
//    @JmsListener(destination = "queue")
//    public void receiveQueue(String message) {
//        System.out.println("监听queue=============监听queue");
//        System.out.println(message);
//
//    }

    @JmsListener(destination = "publish.queue", containerFactory = "jmsListenerContainerQueue")
    @SendTo("out.queue")
    public String receiveQueue(String text){
        System.out.println("QueueListener: consumer-a 收到一条信息: " + text);
        return "consumer-a received : " + text;
    }

    @JmsListener(destination = "publish.topic", containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(String text){
        System.out.println("TopicListener: consumer-a 收到一条信息: " + text);
    }

}
