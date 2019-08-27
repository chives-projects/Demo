package com.controller.mq;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.entity.Users;
import com.service.ConsumerService;
import com.service.ProducerService;
/**
 * @Description
 * @Author shichao.chen
 * @Date 2019/8/27 15:56
 * @Version 1.0
 **/
@Controller
public class DemoController {

    //队列名Jaycekon (ActiveMQ中设置的队列的名称)
    @Resource(name="demoQueueDestination")
    private Destination demoQueueDestination;

    //队列消息生产者
    @Resource(name="producerService")
    private ProducerService producer;

    //队列消息消费者
    @Resource(name="consumerService")
    private ConsumerService consumer;

    /*
     * 准备发消息
     */
    @RequestMapping(value="/producer",method=RequestMethod.GET)
    public ModelAndView producer(){
        System.out.println("------------go producer");

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format( now );
        System.out.println(time);

        ModelAndView mv = new ModelAndView();
        mv.addObject("time", time);
        mv.setViewName("producer");
        return mv;
    }

    /*
     * 发消息
     */
    @RequestMapping(value="/onsend",method=RequestMethod.POST)
    public ModelAndView producer(@RequestParam("message") String message) {
        System.out.println("------------send to jms");
        ModelAndView mv = new ModelAndView();
        for(int i=0;i<5;i++){
            try {
                Users users=new Users("10"+(i+1),"赵媛媛"+(i+1),"女","27","影视演员");
                Gson gson=new Gson();
                String sendMessage=gson.toJson(users);
                System.out.println("发送的消息sendMessage:"+sendMessage.toString());
                // producer.sendMessage(demoQueueDestination,sendMessage.toString());//以文本的形式
                producer.sendMessageNew(demoQueueDestination, users);//以对象的方式

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mv.setViewName("index");
        return mv;
    }
    /*
     * 手动接收消息
     */
    @RequestMapping(value="/receive",method=RequestMethod.GET)
    public ModelAndView queue_receive() throws JMSException {
        System.out.println("------------receive message");
        ModelAndView mv = new ModelAndView();

        //TextMessage tm = consumer.receive(demoQueueDestination);//接收文本消息

        ObjectMessage objMsg=consumer.receiveNew(demoQueueDestination);//接收对象消息
        Users users= (Users) objMsg.getObject();
        //mv.addObject("textMessage", tm.getText());
        mv.addObject("textMessage", users.getUserId()+" || "+users.getUserName());
        mv.setViewName("receive");
        return mv;
    }

    /*
     * ActiveMQ Manager Test
     */
    @RequestMapping(value="/jms",method=RequestMethod.GET)
    public ModelAndView jmsManager() throws IOException {
        System.out.println("------------jms manager");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        JMXServiceURL url = new JMXServiceURL("");
        JMXConnector connector = JMXConnectorFactory.connect(url);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();

        return mv;
    }

}
