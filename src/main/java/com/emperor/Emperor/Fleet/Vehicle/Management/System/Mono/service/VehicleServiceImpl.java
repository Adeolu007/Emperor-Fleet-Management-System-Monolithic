package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.DriverEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Vehicle;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.DriverRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.VehicleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService{
    private VehicleRepository vehicleRepository;
    private DriverRepository driverRepository;
    private EmailService emailService;

    private DriverService driverService;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, DriverRepository driverRepository, EmailService emailService, DriverService driverService) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.emailService = emailService;
        this.driverService = driverService;
    }

    @Override
    public ResponseEntity<ResponseDto> registerNewVehicle(VehicleRegistrationRequest vehicleRegistrationRequest) {
    if(vehicleRepository.existsByLicensePlate(vehicleRegistrationRequest.getLicensePlate())){
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.VEHICLE_ALREADY_EXIST_CODE)
                .responseMessage(ResponseUtils.VEHICLE_ALREADY_EXIST_MESSAGE)
                .responseBody("Vehicle with Licence Plate " + vehicleRegistrationRequest.getLicensePlate() + " already exists.")
                .build());
     //   throw new VehicleAlreadyExistException("Vehicle with license plate " + vehicleRegistration.getLicensePlate() + " already exists");
    }
        Vehicle newVehicle = Vehicle.builder()
                .licensePlate(vehicleRegistrationRequest.getLicensePlate())
                .acquisitionDate(vehicleRegistrationRequest.getAcquisitionDate())
                .model(vehicleRegistrationRequest.getModel())
                .make(vehicleRegistrationRequest.getMake())
                .year(vehicleRegistrationRequest.getYear())
                .fuelCapacity(vehicleRegistrationRequest.getFuelCapacity())
                .description(vehicleRegistrationRequest.getDescription())
                .registrationDate(vehicleRegistrationRequest.getRegistrationDate()).email(driverRepository.findByLicenseNumber(vehicleRegistrationRequest.getDriverLicenseNumber()).get().getEmail())
                .firstName(driverRepository.findByLicenseNumber(vehicleRegistrationRequest.getDriverLicenseNumber()).get().getFirstName())
                .driver(driverRepository.findByLicenseNumber(vehicleRegistrationRequest.getDriverLicenseNumber()).get())
                .status(Status.ACTIVE)
                .build();

        vehicleRepository.save(newVehicle);
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(newVehicle.getEmail());
        emailDetails.setSubject("NeoClan Tech Transaction Alert [Credit : ]");
        emailDetails.setMessageBody("Credit transaction of  has been performed on your account. Your new account balance is " );
        emailService.sendSimpleMail(emailDetails);

        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.VEHICLE_CREATION_CODE)
                .responseMessage(ResponseUtils.VEHICLE_CREATION_MESSAGE)
                .responseBody("A new vehicle with Licence Plate " + newVehicle.getLicensePlate() + " has successfully been created.")
//                        .responseBody(VehicleResponse.builder().make(newVehicle.getMake())
//                                .model(newVehicle.getModel()).licensePlate(newVehicle.getLicensePlate())
//                                .description(newVehicle.getDescription()).fuelCapacity(newVehicle.getFuelCapacity()))
                .build());

    }
    @Override
    public ResponseEntity<ResponseDto> findByLicensePlate(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if(!vehicleRepository.existsByLicensePlate(licensePlate) || vehicle.getStatus()==Status.NOT_ACTIVE){
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.VEHICLE_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.VEHICLE_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry, this vehicle does not exist")

                    .build());

          //  throw new VehicleNotFoundException("This vehicle does not exist");
        }
        RegisteredVehicleDto responseDto = RegisteredVehicleDto.builder()
                .licensePlate(vehicle.getLicensePlate())
                .acquisitionDate(vehicle.getAcquisitionDate())
                .model(vehicle.getModel())
                .make(vehicle.getMake())
                .year(vehicle.getYear())
                .fuelCapacity(vehicle.getFuelCapacity())
                .description(vehicle.getDescription())
                .firstName(vehicle.getFirstName())
                .driverLicenseNumber(driverRepository.findById(vehicle.getDriver().getId()).get().getLicenseNumber())
                .email(driverRepository.findById(vehicle.getDriver().getId()).get().getEmail())
                .build();

log.info(vehicle.getDriver().getLicenseNumber().toString());
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.VEHICLE_FOUND_CODE)
                .responseMessage(ResponseUtils.VEHICLE_FOUND_MESSAGE)
                //.responseBody(VehicleInfoResponse.builder().licensePlate(vehicle.getLicensePlate().toString()).build())
                  .responseBody("Vehicle with license number " + vehicle.getLicensePlate() + " has been assigned to our driver " )
                        .responseBody(VehicleInfoResponse.builder()
                                .licensePlate(vehicle.getLicensePlate()).make(vehicle.getMake())
                                .fuelCapacity(vehicle.getFuelCapacity()).description(vehicle.getDescription()).build())
                .build());
    }

    @Override
    public ResponseEntity<ResponseDto> deleteVehicle(String licensePlate) {
        Vehicle deletedVehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (!vehicleRepository.existsByLicensePlate(licensePlate) || deletedVehicle.getStatus() == Status.NOT_ACTIVE){
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.VEHICLE_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.VEHICLE_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry, this vehicle does not exist")

                    .build());
            //  throw new VehicleNotFoundException("This vehicle does not exist");
        }
        deletedVehicle.setStatus(Status.NOT_ACTIVE);
        vehicleRepository.save(deletedVehicle);
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.VEHICLE_FOUND_CODE)
                .responseMessage(ResponseUtils.VEHICLE_FOUND_MESSAGE)
                .responseBody("Vehicle with license Plate " + deletedVehicle.getLicensePlate() + " has been deleted")
                .build());
    }

    @Override
    public ResponseEntity<ResponseDto> updateVehicle(VehicleRegistrationRequest updateVehicle, String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle==null) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.VEHICLE_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.VEHICLE_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry, this vehicle does not exist")

                    .build());
          //  throw new VehicleNotFoundException("This vehicle does not exist");
        }
        vehicle.setMake(updateVehicle.getMake());
        vehicle.setModel(updateVehicle.getModel());
        vehicle.setDescription(updateVehicle.getDescription());
        vehicle.setYear(updateVehicle.getYear());
        vehicle.setFuelCapacity(updateVehicle.getFuelCapacity());
        vehicle.setRegistrationDate(updateVehicle.getRegistrationDate());
        vehicleRepository.save(vehicle);

//        RegisteredVehicleDto responseDto = RegisteredVehicleDto.builder()
//                .licensePlate(updateVehicle.getLicensePlate())
//                .make(updateVehicle.getMake())
//                .model(updateVehicle.getModel())
//                .fuelCapacity(updateVehicle.getFuelCapacity())
//                .year(updateVehicle.getYear())
//                .acquisitionDate(updateVehicle.getAcquisitionDate())
//                .description(updateVehicle.getDescription())
//                .build();
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.VEHICLE_UPDATED_CODE)
                .responseMessage(ResponseUtils.VEHICLE_UPDATED_MESSAGE)
                .responseBody(VehicleResponse.builder()
                        .make(vehicle.getMake())
                        //.acquisitionDate(vehicle.getAcquisitionDate())
                        //.year(vehicle.getYear()).fuelCapacity(vehicle.getFuelCapacity())
                        .model(vehicle.getModel()))
                .build());

    }

    @Override
//    public ResponseEntity<String> changeDriver(String licensePlate, String driverLicence) {
//        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
//        if(vehicle == null || vehicle.getStatus()==Status.NOT_ACTIVE){
//            throw new VehicleNotFoundException("This vehicle does not exist");
//        }
////      log.info( vehicle.getDriver().getFirstName()); ;
//       log.info( vehicle.getDriver().getLicenseNumber());
//
//
//        vehicle.setDriver(driverRepository.findByLicenseNumber(driverLicence));
//      //  vehicle.setFirstName(driverRepository.findByLicenseNumber(driverLicence).getFirstName());
//        vehicleRepository.save(vehicle);
//        return ResponseEntity.ok("Driver has been reassigned");
//    }

    public ResponseEntity<String> changeDriver(String licensePlate, String driverLicence) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle == null || vehicle.getStatus() == Status.NOT_ACTIVE) {
            throw new VehicleNotFoundException("This vehicle does not exist or is not active");
        }



//        log.info(String.valueOf(driverService.getDriverByLicenseNumber(driverLicence).getBody()));


        DriverEntity newDriver = driverRepository.findByLicenseNumber(driverLicence).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info(String.format("Driver license number is '%s'", newDriver.getLicenseNumber()));
//        if (newDriver == null) {
//            throw new DriverNotFoundException("Driver with license number " + driverLicence + " not found");
//        }

        // Check if the current driver is null
//        if (vehicle.getDriver() != null) {
//            log.info("Current Driver License Number: " + vehicle.getDriver().getLicenseNumber());
//        } else {
//            log.info("Current Driver is null");
//        }

        // Set the new driver
        vehicle.setDriver(newDriver);

        // Save the updated vehicle
        vehicleRepository.save(vehicle);

        return ResponseEntity.ok("Driver has been reassigned");
    }


//    public ResponseEntity<String> changeDriver(String licensePlate, String driverLicence) {
//        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
//        DriverEntity newDriver = driverRepository.findByLicenseNumber(driverLicence);
//        log.info("Vehicle:" + vehicle);
//        log.info("Driver:" + newDriver);
//        if (vehicle == null || vehicle.getStatus() == Status.NOT_ACTIVE) {
//            throw new VehicleNotFoundException("This vehicle does not exist or is not active");
//        }
//
//        if (newDriver == null) {
//            throw new DriverNotFoundException("Driver with license number " + driverLicence + " not found");
//        }
//
//        DriverEntity currentDriver = vehicle.getDriver();
//
//        if (currentDriver != null) {
//            log.info("Current Driver License Number: " + currentDriver.getLicenseNumber());
//        } else {
//            log.info("Current Driver is null");
//        }
//
//        log.info("New Driver License Number: " + newDriver.getLicenseNumber());
//
//        vehicle.setDriver(newDriver);
//        vehicleRepository.save(vehicle);
//
//        return ResponseEntity.ok("Driver has been reassigned");
//    }
}
