package fr.app.amqp.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Slf4j
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory;

    public RabbitMQConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    @Value("${rabbitmq.exchanges.internal}")
    private String exchangeForNotification;
    @Value("${rabbitmq.routing-keys.notification}")
    private String routingKeyForNotification;
    @Value("${rabbitmq.queues.notification}")
    private String queueForNotification;

//    private static Properties props;
//    @Bean
//    public static void properties() throws IOException {
//        props = new Properties();
//        Reader reader = new BufferedReader(new FileReader("message_queue/src/main/resources/application.yml"));
//        props.load(reader);
//        props.forEach((k,v) -> System.out.println(k + ":" + v));
//        props.setProperty("rabbitmq.exchanges.internal", "exchange");
//        props.setProperty("rabbitmq.routing-keys.notification", "notification.routing-key");
//        props.setProperty("rabbitmq.queues.notification", "notification.queue");
//    }

    @Bean
    public AmqpTemplate mainProducer(MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory factory(MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitListenerErrorHandler validationErrorHandler() {
        return (m, m2, e) -> {
            if (e != null) {
                log.info("The message with id {} threw an exception", m.getMessageProperties().getMessageId());
                throw new RuntimeException(e.getCause());
            }
            return m;
        };

    }

//    @Bean
//    public TopicExchange topicExchangeForNotification() {
//        System.out.println("Voila exchangeForNotification " + this.exchangeForNotification);
//        return new TopicExchange(this.exchangeForNotification);
//    }
//
//    @Bean
//    public Queue queueForNotification() {
//        System.out.println("Voila queueForNotification " + this.queueForNotification);
//        return new Queue(this.queueForNotification);
//    }
//
//    @Bean
//    public Binding bindingNotification() {
//        System.out.println("Voila routingKey " + this.routingKeyForNotification);
//        return BindingBuilder
//                .bind(queueForNotification())
//                .to(topicExchangeForNotification())
//                .with(this.routingKeyForNotification);
//    }
}
