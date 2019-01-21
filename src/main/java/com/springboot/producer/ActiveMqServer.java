package com.springboot.producer;

import com.springboot.myconfiguration.EntryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;

@RestController
@RequestMapping("/api")
public class ActiveMqServer {
//    @Autowired
//    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsMessagingTemplate jms;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @Autowired
    private EntryObject entryObject;


//    @RequestMapping("/sendtopic")
//    public void sendtopic(){
//        jmsTemplate.send("zhaomengjie", new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage textMessage = session.createTextMessage();
//                textMessage.setText("send data");
//                return textMessage;
//            }
//        });
//    }

    @RequestMapping("/queue")
    public String queue() {
        for (int i = 0; i < 10; i++) {
            jms.convertAndSend(queue, "queue" + i);
        }
        return "queue 发送成功";
    }

    @RequestMapping("/topic")
    public String topic(){
        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(topic, "topic"+i);
        }
        return "topic 发送成功";
    }

    @JmsListener(destination = "out.queue", containerFactory = "jmsListenerContainerQueue")
    public void consumerMsg(String msg){
        System.out.println(msg);
    }

}
