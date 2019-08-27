package com.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.entity.Users;

/**
 * @Description
 * @Author shichao.chen
 * @Date 2019/8/27 15:53
 * @Version 1.0
 **/
@Service
public class ConsumerService {

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    //接收文本消息
    public TextMessage receive(Destination destination) {
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try {
            JSONObject json = JSONObject.fromObject(textMessage.getText());
            System.out.println("name:" + json.getString("userName"));
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage;
    }

    //接收对象消息
    public ObjectMessage receiveNew(Destination destination) {
        ObjectMessage objMsg = (ObjectMessage) jmsTemplate.receive(destination);
        try {
            Users users = (Users) objMsg.getObject();
            System.out.println("name:" + users.getUserName());
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + users);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return objMsg;
    }
}
