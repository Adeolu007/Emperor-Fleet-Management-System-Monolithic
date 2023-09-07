//package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.RabbitMq;
//
//
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.EmailDetails;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class RabbitMQJsonProducer {
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchange;
//
//    @Value("${rabbitmq.occupant.booking.key}")
//    private String occupantBookingKey;
//
//    private final RabbitTemplate rabbitTemplate;
//
//    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void sendJsonMessage(EmailDetails emailDetails) {
//        log.info(String.format("message sent -> %s", emailDetails));
//        rabbitTemplate.convertAndSend(exchange, occupantBookingKey, emailDetails);
//    }
//
//}
