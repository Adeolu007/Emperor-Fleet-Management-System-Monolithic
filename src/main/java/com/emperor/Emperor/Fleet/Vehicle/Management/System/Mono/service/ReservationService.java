package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ReservationDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ReservationResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    ResponseEntity<ResponseDto> createReservation(ReservationDto reservationDto);
    ResponseEntity<List<ReservationResponseDto>> getAllReservations();
    ResponseEntity<ResponseDto> getReservationById(Long reservationId);
}
