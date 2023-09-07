//package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.RabbitMq.consumer;
//
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.EmailDetails;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.EmailService;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitMQEmailConsumer {
//
//    @Autowired
//    private EmailService emailService;
//
//    @RabbitListener(queues = "${rabbitmq.occupant.booking.queue}")
//    public void sendUserRegistrationDetails(EmailDetails emailDetails) {
//        emailService.sendSimpleMail(emailDetails);
//    }
//
//}
