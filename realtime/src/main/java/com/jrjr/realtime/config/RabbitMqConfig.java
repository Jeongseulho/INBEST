package com.jrjr.realtime.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    // Exchange 이름
    @Value("${custom.rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    // Queue 이름
    @Value("${custom.rabbitmq.queue.invest}")
    private String INVEST_QUEUE_NAME;
    @Value("${custom.rabbitmq.queue.trading}")
    private String TRADING_QUEUE_NAME;
    @Value("${custom.rabbitmq.queue.chat}")
    private String CHAT_QUEUE_NAME;
    @Value("${custom.rabbitmq.queue.realtime}")
    private String REALTIME_QUEUE_NAME;

    // 라우팅 키
    @Value("${custom.rabbitmq.routing-key.invest}")
    private String INVEST_ROUTING_KEY;
    @Value("${custom.rabbitmq.routing-key.trading}")
    private String TRADING_ROUTING_KEY;
    @Value("${custom.rabbitmq.routing-key.chat}")
    private String CHAT_ROUTING_KEY;
    @Value("${custom.rabbitmq.routing-key.realtime}")
    private String REALTIME_ROUTING_KEY;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue investQueue() {
        return new Queue(INVEST_QUEUE_NAME);
    }

    @Bean
    public Queue tradingQueue() {
        return new Queue(TRADING_QUEUE_NAME);
    }

    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE_NAME);
    }

    @Bean
    public Queue realtimeQueue() {
        return new Queue(REALTIME_QUEUE_NAME);
    }

    @Bean
    public Binding investBinding(DirectExchange directExchange, Queue investQueue) {
        return BindingBuilder.bind(investQueue).to(directExchange).with(INVEST_ROUTING_KEY);
    }

    @Bean
    public Binding tradingBinding(DirectExchange directExchange, Queue tradingQueue) {
        return BindingBuilder.bind(tradingQueue).to(directExchange).with(TRADING_ROUTING_KEY);
    }

    @Bean
    public Binding chatBinding(DirectExchange directExchange, Queue chatQueue) {
        return BindingBuilder.bind(chatQueue).to(directExchange).with(CHAT_ROUTING_KEY);
    }

    @Bean
    public Binding realtimeBinding(DirectExchange directExchange, Queue realtimeQueue) {
        return BindingBuilder.bind(realtimeQueue).to(directExchange).with(REALTIME_ROUTING_KEY);
    }

    /**
     * RabbitMQ 연결을 위한 ConnectionFactory 빈을 생성하여 반환
     *
     * @return ConnectionFactory 객체
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // JSON 형식의 메시지를 직렬화하고 역직렬할 수 있도록 설정
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }


    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        //LocalDateTime serializable 을 위해
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.registerModule(dateTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public  com.fasterxml.jackson.databind.Module dateTimeModule() {
        return new JavaTimeModule();
    }
}