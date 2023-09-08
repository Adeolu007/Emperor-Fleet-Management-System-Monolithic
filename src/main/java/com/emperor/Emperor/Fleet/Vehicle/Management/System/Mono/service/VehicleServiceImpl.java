package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.RabbitMq.RabbitMQJsonProducer;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.EmailDetails;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RegisteredVehicleDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.UpdateVehicle;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleRegistration;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Vehicle;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriverNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleAlreadyExistException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.DriverRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService{
    //@Autowired
    private VehicleRepository vehicleRepository;
    //@Autowired
    private DriverRepository driverRepository;
    private EmailService emailService;
  //  @Autowired
//    private RabbitMQJsonProducer rabbitMQProducer;


    public VehicleServiceImpl(VehicleRepository vehicleRepository, DriverRepository driverRepository, EmailService emailService) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<String> registerNewVehicle(VehicleRegistration vehicleRegistration) {
    if(vehicleRepository.existsByLicensePlate(vehicleRegistration.getLicensePlate())){
        throw new VehicleAlreadyExistException("Vehicle with license plate " + vehicleRegistration.getLicensePlate() + " already exists");
    }
        Vehicle newVehicle = Vehicle.builder()
                .licensePlate(vehicleRegistration.getLicensePlate())
                .acquisitionDate(vehicleRegistration.getAcquisitionDate())
                .model(vehicleRegistration.getModel())
                .make(vehicleRegistration.getMake())
                .year(vehicleRegistration.getYear())
                .fuelCapacity(vehicleRegistration.getFuelCapacity())
                .description(vehicleRegistration.getDescription())
                .registrationDate(vehicleRegistration.getRegistrationDate())
                .email(driverRepository.findByLicenseNumber(vehicleRegistration.getDriverLicenseNumber()).getEmail())
                .firstName(driverRepository.findByLicenseNumber(vehicleRegistration.getDriverLicenseNumber()).getFirstName())
                .driver(driverRepository.findByLicenseNumber(vehicleRegistration.getDriverLicenseNumber()))
                .build();

        vehicleRepository.save(newVehicle);
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(newVehicle.getEmail());
        emailDetails.setSubject("NeoClan Tech Transaction Alert [Credit : ]");
        emailDetails.setMessageBody("Credit transaction of  has been performed on your account. Your new account balance is " );
//    kafkaTemplate.send("notificationTopic", emailDetails);
        emailService.sendSimpleMail(emailDetails);
      //  rabbitMQProducer.sendJsonMessage(emailDetails);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Vehicle with License Plate " + vehicleRegistration.getLicensePlate() + " has been registered");

    }

    @Override
    public ResponseEntity<RegisteredVehicleDto> findByLicensePlate(String licensePlate) {
        if(!vehicleRepository.existsByLicensePlate(licensePlate)){
            throw new VehicleNotFoundException("This vehicle does not exist");
        }
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
//        RegisteredVehicleDto responseDto= RegisteredVehicleDto.builder().licensePlate(vehicle.getLicensePlate())
//                .acquisitionDate(vehicle.getAcquisitionDate())
//                .model(vehicle.getModel())
//                .make(vehicle.getMake())
//                .year(vehicle.getYear())
//                .fuelCapacity(vehicle.getFuelCapacity())
//                .description(vehicle.getDescription())
//                .firstName(vehicle.getFirstName())
//                .driverLicenseNumber(vehicle.getDriverLicenseNumber())
//                .build();
//        return ResponseEntity.ok(registeredVehicleDto);
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

        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<String> deleteVehicle(String licensePlate) {
        if (!vehicleRepository.existsByLicensePlate(licensePlate)){
      //  if (!vehicleRepository.existsById(licensePlate)) {
            throw new VehicleNotFoundException("This vehicle does not exist");
        }
        vehicleRepository.deleteByLicensePlate(licensePlate);
        //vehicleRepository.deleteById(licensePlate);
        return ResponseEntity.ok("Vehicle with License Plate " + licensePlate + " has been deleted");

    }

    @Override
    public ResponseEntity<RegisteredVehicleDto> updateVehicle(UpdateVehicle updateVehicle) {
        if (!vehicleRepository.existsByLicensePlate(updateVehicle.getLicensePlate())) {
            throw new VehicleNotFoundException("This vehicle does not exist");
        }
        Vehicle vehicle = vehicleRepository.findByLicensePlate(updateVehicle.getLicensePlate());

        RegisteredVehicleDto responseDto = RegisteredVehicleDto.builder()
                .licensePlate(updateVehicle.getLicensePlate())
                .make(updateVehicle.getMake())
                .model(updateVehicle.getModel())
                .fuelCapacity(updateVehicle.getFuelCapacity())
                .year(updateVehicle.getYear())
                .acquisitionDate(updateVehicle.getAcquisitionDate())
                .description(updateVehicle.getDescription())
                .build();

        return ResponseEntity.ok(responseDto);

    }

}
