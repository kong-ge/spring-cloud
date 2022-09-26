package com.kongge.rabbitmq.controller;

import com.kongge.rabbitmq.config.RabbitMqConfiguration;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class RabbitMqController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMsg")
    public void sendDirectMsg(String msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.DIRECT_EXCHANGE, RabbitMqConfiguration.DIRECT_ROUTING_KEY, msg);
    }

    @GetMapping("/sendTopicMsg")
    public void sendTopicMsg(String msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.TOPIC_EXCHANGE, "topic.routing.key.hello", msg);
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.TOPIC_EXCHANGE, "topic.routing.key.hello.world", msg);
    }

    @GetMapping("/sendFanoutMsg")
    public void sendFanoutMsg(String msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.FANOUT_EXCHANGE, "", msg);
    }

    @GetMapping("/sendHeaderMsg")
    public void sendHeaderMsg(String msg) {
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).setHeader(RabbitMqConfiguration.HEADER_KEY, "tom").build();
        rabbitTemplate.send(RabbitMqConfiguration.HEADER_EXCHANGE, "", message);
        Message m = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).setHeader("cus", "tom").build();
        rabbitTemplate.send(RabbitMqConfiguration.HEADER_EXCHANGE, "", m);
    }

    @GetMapping("/sendToDeadMsg")
    public void sendToDeadMsg(String msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.TO_DEAD_EXCHANGE, "", msg);
    }
}
