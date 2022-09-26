package com.kongge.rabbitmq.receiver;

import com.kongge.rabbitmq.config.RabbitMqConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqReceiver {
    @RabbitListener(queues = RabbitMqConfiguration.DIRECT_QUEUE)
    public void directReceiver(String msg) {
        System.out.println(msg);
    }

    @RabbitListener(queues = RabbitMqConfiguration.TOPIC_QUEUE_1)
    public void topicReceiver(String msg) {
        System.out.println("topic1: " + msg);
    }

    @RabbitListener(queues = RabbitMqConfiguration.TOPIC_QUEUE_2)
    public void topicReceiver2(String msg) {
        System.out.println("topic2: " + msg);
    }

    @RabbitListener(queues = RabbitMqConfiguration.FANOUT_QUEUE_1)
    public void fanoutReceiver(String msg) {
        System.out.println("fanout1: " + msg);
    }

    @RabbitListener(queues = RabbitMqConfiguration.FANOUT_QUEUE_2)
    public void fanoutReceiver2(String msg) {
        System.out.println("fanout2: " + msg);
    }

    @RabbitListener(queues = RabbitMqConfiguration.HEADER_QUEUE)
    public void headerReceiver(String msg) {
        System.out.println("header: " + msg);
    }

//    @RabbitListener(queues = RabbitMqConfiguration.DEAD_QUEUE)
//    public void deadReceiver(String msg) {
//        System.out.println("dead: " + msg);
//    }
}
