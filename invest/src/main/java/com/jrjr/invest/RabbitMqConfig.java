// package com.jrjr.invest;
//
// import org.springframework.amqp.core.Binding;
// import org.springframework.amqp.core.BindingBuilder;
// import org.springframework.amqp.core.Queue;
// import org.springframework.amqp.core.TopicExchange;
// import org.springframework.amqp.rabbit.annotation.EnableRabbit;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
// import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
// import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// @EnableRabbit
// public class RabbitMqConfig {
// 	static final String CHAT_QUEUE_NAME = "chat.queue";
// 	static final String CHAT_EXCHANGE_NAME = "chat.exchange";
// 	static final String ROUTING_KEY = "room.*";
//
// 	@Value("${spring.rabbitmq.host}")
// 	private String host;
//
// 	@Value("${spring.rabbitmq.port}")
// 	private Integer port;
//
// 	@Value("${spring.rabbitmq.username}")
// 	private String username;
//
// 	@Value("${spring.rabbitmq.password}")
// 	private String password;
//
// 	@Bean
// 	Queue queue() {
// 		return new Queue(CHAT_QUEUE_NAME, false);
// 	} // durable : RabbitMQ 서버가 재시작될 때 지속성
//
// 	@Bean
// 	TopicExchange exchange() {
// 		return new TopicExchange(CHAT_EXCHANGE_NAME);
// 	}
//
// 	@Bean
// 	Binding binding(Queue queue, TopicExchange exchange) {
// 		return BindingBuilder
// 				.bind(queue)
// 				.to(exchange)
// 				.with(ROUTING_KEY);
// 	}
//
// 	@Bean
// 	public RabbitTemplate rabbitTemplate() {
// 		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
// 		rabbitTemplate.setMessageConverter(jsonMessageConverter());
// 		rabbitTemplate.setRoutingKey(ROUTING_KEY);
// 		return rabbitTemplate;
// 	}
//
// 	@Bean
// 	public Jackson2JsonMessageConverter jsonMessageConverter(){
// 		return new Jackson2JsonMessageConverter();
// 	}
//
// 	@Bean
// 	public ConnectionFactory connectionFactory() {
// 		CachingConnectionFactory factory = new CachingConnectionFactory();
// 		factory.setHost(host);
// 		factory.setPort(port);
// 		factory.setUsername(username);
// 		factory.setPassword(password);
// 		return factory;
// 	}
//
// 	@Bean
// 	public SimpleMessageListenerContainer container() {
// 		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
// 		container.setConnectionFactory(connectionFactory());
// 		container.setQueueNames(CHAT_QUEUE_NAME);
// 		container.setMessageListener(null);
// 		return container;
// 	}
//
// 	// @Bean
// 	// SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
// 	// 	MessageListenerAdapter listenerAdapter) {
// 	// 	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
// 	// 	container.setConnectionFactory(connectionFactory);
// 	// 	container.setQueueNames(CHAT_QUEUE_NAME);
// 	// 	container.setMessageListener(listenerAdapter);
// 	// 	return container;
// 	// }
//
//
// 	// @Bean
// 	// MessageListenerAdapter listenerAdapter(Receiver receiver) {
// 	// 	return new MessageListenerAdapter(receiver, "receiveMessage");
// 	// }
//
// }
