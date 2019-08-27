package com.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.entity.Users;

/**
 * @Description
 * @Author shichao.chen
 * @Date 2019/8/27 15:50
 * @Version 1.0
 **/
@Service
public class ProducerService {

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;


    /**
     * 向指定队列发送消息 (发送文本消息)
     */
    public void sendMessage(Destination destination, final String msg) {

        jmsTemplate.setDeliveryPersistent(true);

        System.out.println(Thread.currentThread().getName() + " 向队列" + destination.toString() + "发送消息---------------------->" + msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    /**
     * 向指定队列发送消息以对象的方式 (发送对象消息)
     */
    public void sendMessageNew(Destination destination, Users user) {
        System.out.println(Thread.currentThread().getName() + " 向队列" + destination.toString() + "发送消息---------------------->" + user);
        jmsTemplate.convertAndSend(user);
    }

    /**
     * 向默认队列发送消息
     */
    public void sendMessage(final String msg) {
        String destination = jmsTemplate.getDefaultDestinationName();
        System.out.println(Thread.currentThread().getName() + " 向队列" + destination + "发送消息---------------------->" + msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}
