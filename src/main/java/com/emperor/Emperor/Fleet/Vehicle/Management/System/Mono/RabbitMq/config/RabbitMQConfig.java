//package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.RabbitMq.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//
//    @Value("${rabbitmq.occupant.booking.queue}")
//    private String occupantBookingQueue;
//
//    @Bean
//    public Queue occupantBookingQueue() {
//        return new Queue(occupantBookingQueue);
//    }
//
//    @Bean
//    public MessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter());
//        return rabbitTemplate;
//    }
//
//    ////
//
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchange;
//
//
//    @Value("${rabbitmq.occupant.booking.key}")
//    private String occupantBookingKey;
//
//
//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(exchange);
//    }
//
//    @Bean
//    public Binding occupantBookingBinding() {
//        return BindingBuilder.bind(occupantBookingQueue())
//                .to(exchange())
//                .with(occupantBookingKey);
//    }
//
//
//}
