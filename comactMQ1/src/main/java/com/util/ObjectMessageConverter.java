package com.util;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
/**
 * @Description 通用的消息对象转换类
 * @Author shichao.chen
 * @Date 2019/8/27 16:00
 * @Version 1.0
 **/
public class ObjectMessageConverter implements MessageConverter {

    //把一个Java对象转换成对应的JMS Message (生产消息的时候)
    public Message toMessage(Object object, Session session)
            throws JMSException, MessageConversionException {

        return session.createObjectMessage((Serializable) object);
    }

    //把一个JMS Message转换成对应的Java对象 (消费消息的时候)
    public Object fromMessage(Message message) throws JMSException,
            MessageConversionException {
        ObjectMessage objMessage = (ObjectMessage) message;
        return objMessage.getObject();
    }

}

