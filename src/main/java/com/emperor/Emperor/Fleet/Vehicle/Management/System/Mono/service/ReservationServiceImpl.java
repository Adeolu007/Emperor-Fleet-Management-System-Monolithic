package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RegisteredVehicleDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ReservationDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ReservationResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Reservation;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.CustomReservationException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.ReservationNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.ReservationRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, VehicleRepository vehicleRepository) {
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
//    public ResponseEntity<ReservationResponseDto> createReservation(ReservationDto reservationDto) {
//        if (!vehicleRepository.existsByLicensePlate(reservationDto.getVehicle()))
//            throw new VehicleNotFoundException("This vehicle does not exist");
//
//        Reservation existingReservation = reservationRepository.findByVehicleAndDateAndStartTime(
//                reservationDto.getVehicle(), reservationDto.getDate(), reservationDto.getStartTime());
//
//        if (existingReservation != null) {
//            // A reservation for the same vehicle, date, and startTime already exists
//            throw new CustomReservationException("A reservation for the same vehicle, date, and startTime already exists.");
//        } else {
//            // Create a new reservation
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate date = reservationDto.getDate(); // Assign the date directly
//
//            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//            String startTimeString = String.valueOf(reservationDto.getStartTime());
//            String endTimeString = String.valueOf(reservationDto.getEndTime());
//            LocalTime startTime = LocalTime.parse(startTimeString, timeFormatter);
//            LocalTime endTime = LocalTime.parse(endTimeString, timeFormatter);
//
//            Reservation reservation = new Reservation();
//            reservation.setVehicle(vehicleRepository.findByLicensePlate(reservationDto.getVehicle()));
//            reservation.setDate(date);
//            reservation.setStartTime(startTime);
//            reservation.setEndTime(endTime);
//            reservation.setPurpose(reservationDto.getPurpose());
//
//            // Save the new reservation to the repository
//            reservationRepository.save(reservation);
//
//            return ResponseEntity.ok(ReservationResponseDto.builder().vehicle(reservation.getVehicle().getLicensePlate())
//                    .date(reservation.getDate()).startTime(reservation.getStartTime()).endTime(reservation.getEndTime())
//                    .purpose(reservation.getPurpose()).build());
//        }
//    }
    public ResponseEntity<ReservationResponseDto> createReservation(ReservationDto reservationDto) {
        if (!vehicleRepository.existsByLicensePlate(reservationDto.getVehicle()))
            throw new VehicleNotFoundException("This vehicle does not exist");
        Reservation existingReservation = reservationRepository.findByVehicleAndDateAndStartTime(
                reservationDto.getVehicle(), reservationDto.getDate(), reservationDto.getStartTime());

        if (existingReservation != null) {
            // A reservation for the same vehicle, date, and startTime already exists
            throw new CustomReservationException("A reservation for the same vehicle, date, and startTime already exists.");
        } else {
            // Create a new reservation
            //Date format is not working, it is returning null
            Reservation reservation = new Reservation();
            reservation.setVehicle(vehicleRepository.findByLicensePlate(reservationDto.getVehicle()));
            reservation.setDate(reservationDto.getDate());
            reservation.setStartTime(reservationDto.getStartTime());
            reservation.setEndTime(reservationDto.getEndTime());
            reservation.setPurpose(reservationDto.getPurpose());

            // Save the new reservation to the repository
            reservationRepository.save(reservation);

            return ResponseEntity.ok(ReservationResponseDto.builder().vehicle(reservation.getVehicle().getLicensePlate())
                    .date(reservation.getDate()).startTime(reservation.getStartTime()).endTime(reservation.getEndTime())
                    .purpose(reservation.getPurpose()).build());

        }
    }

    @Override
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        if(reservationRepository.findAll()==null) return null;
        List<Reservation> reservations = reservationRepository.findAll();

        List<ReservationResponseDto> reservationResponseDtos = reservations.stream()
                .map(this::convertToReservationResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservationResponseDtos);
    }

    @Override
    public ResponseEntity<ReservationResponseDto> getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        ReservationResponseDto reservationResponseDto = convertToReservationResponseDto(reservation);

        return ResponseEntity.ok(reservationResponseDto);
    }

    private ReservationResponseDto convertToReservationResponseDto(Reservation reservation) {
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setVehicle(reservation.getVehicle().getLicensePlate());
        reservationResponseDto.setDate(reservation.getDate());
        reservationResponseDto.setStartTime(reservation.getStartTime());
        reservationResponseDto.setEndTime(reservation.getEndTime());
        reservationResponseDto.setPurpose(reservation.getPurpose());
        return reservationResponseDto;
    }
}