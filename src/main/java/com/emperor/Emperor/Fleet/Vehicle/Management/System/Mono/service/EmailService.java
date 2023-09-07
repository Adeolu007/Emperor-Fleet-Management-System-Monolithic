package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

}