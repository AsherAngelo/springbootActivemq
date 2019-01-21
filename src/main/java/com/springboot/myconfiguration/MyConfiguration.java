package com.springboot.myconfiguration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class MyConfiguration {

    @Autowired
    private EntryObject entryObject;

    @Value("${spring.activemq.user}")
    private String usrName;
    @Value("${spring.activemq.password}")
    private String password;
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(entryObject.getQueueName());
    }

    @Bean
    public Topic topic(){
        ActiveMQTopic topic =  new ActiveMQTopic(entryObject.getTopicName());
        return topic;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(usrName, password, brokerUrl);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        //重连间隔时间
        bean.setRecoveryInterval(1000L);
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(connectionFactory);
        // 给订阅者一个名字,并开启持久订阅
        bean.setClientId("client_id2");
        bean.setSubscriptionDurable(true);
        return bean;
    }

    @Bean
    @Primary
    public JmsTemplate MyJmsTemplate(ActiveMQConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubNoLocal(true);
        jmsTemplate.setDeliveryMode(2);
        jmsTemplate.setExplicitQosEnabled(true);
        return jmsTemplate;
    }

}
