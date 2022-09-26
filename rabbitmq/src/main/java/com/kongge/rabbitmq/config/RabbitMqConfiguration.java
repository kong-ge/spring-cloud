package com.kongge.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfiguration {

    public static final String DIRECT_QUEUE = "direct.queue";
    public static final String DIRECT_EXCHANGE = "direct.exchange";
    public static final String DIRECT_ROUTING_KEY = "direct.routing.key";

    public static final String TOPIC_QUEUE_1 = "topic.queue.1";
    public static final String TOPIC_QUEUE_2 = "topic.queue.2";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
//    public static final String TOPIC_ROUTING_KEY = "topic.routing.key.#";
    public static final String TOPIC_ROUTING_KEY = "topic.routing.key.*";

    public static final String FANOUT_QUEUE_1 = "fanout.queue.1";
    public static final String FANOUT_QUEUE_2 = "fanout.queue.2";
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    public static final String HEADER_QUEUE = "header.queue";
    public static final String HEADER_EXCHANGE = "header.exchange";
    public static final String HEADER_KEY = "name";

    public static final String TO_DEAD_EXCHANGE = "to.dead.exchange";
    public static final String TO_DEAD_QUEUE = "to.dead.queue";
    public static final String DEAD_EXCHANGE = "dead.exchange";
    public static final String DEAD_QUEUE = "dead.queue";

    /*
    ============== direct ================
     */
    @Bean
    public Queue directQueue() {
        Map<String, Object> args = new HashMap<>();
        return new Queue(DIRECT_QUEUE, true, true, false, args);
    }

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).build();
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY).noargs();
    }

    /*
    ============== topic ================
     */
    @Bean
    public Queue topicQueue() {
        Map<String, Object> args = new HashMap<>();
        return new Queue(TOPIC_QUEUE_1, true, true, false, args);
    }

    @Bean
    public Queue topicQueue2() {
        Map<String, Object> args = new HashMap<>();
        return new Queue(TOPIC_QUEUE_2, true, true, false, args);
    }

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).build();
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(TOPIC_ROUTING_KEY);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_ROUTING_KEY);
    }

    /*
    ============== fanout ================
     */
    @Bean
    public Queue fanoutQueue() {
        Map<String, Object> args = new HashMap<>();
        return new Queue(FANOUT_QUEUE_1, true, true, false, args);
    }

    @Bean
    public Queue fanoutQueue2() {
        Map<String, Object> args = new HashMap<>();
        return new Queue(FANOUT_QUEUE_2, true, true, false, args);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).build();
    }

    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    /*
    ============== header ================
     */
    @Bean
    public Queue headerQueue() {
        Map<String, Object> args = new HashMap<>();
        return new Queue(HEADER_QUEUE, true, true, false, args);
    }

    @Bean
    public HeadersExchange headerExchange() {
        return ExchangeBuilder.headersExchange(HEADER_EXCHANGE).build();
    }

    @Bean
    public Binding headerBinding() {
        return BindingBuilder.bind(headerQueue()).to(headerExchange()).where(HEADER_KEY).exists();
    }

    /*
    ============== dead letter ================
     */
    @Bean
    public Queue toDeadQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        return new Queue(TO_DEAD_QUEUE, true, true, false, args);
    }

    @Bean
    public FanoutExchange toDeadExchange() {
        return ExchangeBuilder.fanoutExchange(TO_DEAD_EXCHANGE).build();
    }

    @Bean
    public Binding toDeadBinding() {
        return BindingBuilder.bind(toDeadQueue()).to(toDeadExchange());
    }

    @Bean
    public Queue deadQueue() {
        Map<String, Object> args = new HashMap<>();
        return new Queue(DEAD_QUEUE, true, false, false, args);
    }

    @Bean
    public FanoutExchange deadExchange() {
        return ExchangeBuilder.fanoutExchange(DEAD_EXCHANGE).build();
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange());
    }
}
