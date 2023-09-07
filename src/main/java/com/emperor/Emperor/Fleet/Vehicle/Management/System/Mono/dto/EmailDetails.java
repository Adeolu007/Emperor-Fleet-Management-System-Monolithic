package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmailDetails {

    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;

}