package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception;

public class ReservationNotFoundException extends RuntimeException{

    //custom exception
    public ReservationNotFoundException(String message){
        super(message);
    }
}