package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ReservationDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ReservationResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    ResponseEntity<ReservationResponseDto> createReservation(ReservationDto reservationDto);
    ResponseEntity<List<ReservationResponseDto>> getAllReservations();
    ResponseEntity<ReservationResponseDto> getReservationById(Long reservationId);
}
