package com.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.entity.Users;
/**
 * @Description
 * @Author shichao.chen
 * @Date 2019/8/27 16:02
 * @Version 1.0
 **/
public class QueueMessageListener implements MessageListener {

    //添加了监听器,只要生产者发布了消息,监听器监听到有消息消费者就会自动消费(获取消息)
    public void onMessage(Message message) {
        //(第1种方式)没加转换器之前接收到的是文本消息
        //TextMessage tm = (TextMessage) message;

        //(第2种方式)加了转换器之后接收到的ObjectMessage对象消息
        ObjectMessage objMsg=(ObjectMessage) message;
        Users users;
        try {
            users = (Users) objMsg.getObject();
            //System.out.println("QueueMessageListener监听到了文本消息：\t" + tm.getText());
            System.out.println("QueueMessageListener监听到了文本消息：\t" + users);
            //do something ...
        } catch (JMSException e1) {
            e1.printStackTrace();
        }
    }

}

