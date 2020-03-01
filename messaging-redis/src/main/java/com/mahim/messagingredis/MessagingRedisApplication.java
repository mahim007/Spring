package com.mahim.messagingredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class MessagingRedisApplication {
	private static final Logger log = LoggerFactory.getLogger(MessagingRedisApplication.class);

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(MessagingRedisApplication.class, args);
		StringRedisTemplate template = context.getBean(StringRedisTemplate.class);
		Receiver receiver = context.getBean(Receiver.class);

		while (receiver.getCount() < 5) {
			log.info("sending message...");
			template.convertAndSend("chat", "hello from redis # " + receiver.getCount());
			Thread.sleep(500L);
		}
		System.exit(0);
	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	Receiver receiver() {
		return new Receiver();
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

}
