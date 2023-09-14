package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils;

import java.util.Random;

public class ResponseUtils {
    public static final String DRIVER_UPDATED_CODE = "002";
    public static final String DRIVER_UPDATED_MESSAGE = "Driver's profile has been successfully updated";
    public static final String DRIVER_DOES_NOT_EXIST_CODE = "003";
    public static final String DRIVER_DOES_NOT_EXIST_MESSAGE = "This driver does not exist";
    public static final String DRIVER_DELETED_CODE = "004";
    public static final String DRIVER_DELETED_MESSAGE = "Driver has been deleted Successfully";
    public static final String DRIVER_FOUND_CODE = "005";
    public static final String DRIVER_FOUND_MESSAGE = "Driver profile has been found successfully";
    public static final String VEHICLE_ALREADY_EXIST_CODE = "006";
    public static final String VEHICLE_ALREADY_EXIST_MESSAGE = "Vehicle already exist";
    public static final String VEHICLE_CREATION_CODE = "007";
    public static final String VEHICLE_CREATION_MESSAGE = "Vehicle has successfully been created.";
    public static final String VEHICLE_DOES_NOT_EXIST_CODE = "008";
    public static final String VEHICLE_DOES_NOT_EXIST_MESSAGE = "This vehicle does not exist";
    public static final String VEHICLE_FOUND_CODE = "009";
    public static final String VEHICLE_FOUND_MESSAGE = "Vehicle has been found";
    public static final String VEHICLE_UPDATED_CODE = "010";
    public static final String VEHICLE_UPDATED_MESSAGE = "Vehicle has been successfully updated";
    public static final String FUEL_RECORD_CREATION_CODE = "011";
    public static final String FUEL_RECORD_CREATION_MESSAGE = "Fuel record has successfully been created.";
    public static final String MAINTENANCE_REPAIR_RECORD_CREATION_CODE = "012";
    public static final String MAINTENANCE_REPAIR_RECORD_CREATION_MESSAGE = "MaintenanceRepair record has successfully been created.";
    public static final String MAINTENANCE_REPAIR_RECORD_DOES_NOT_EXIST_CODE = "013";
    public static final String MAINTENANCE_REPAIR_RECORD_DOES_NOT_EXIST_MESSAGE = "This record does not exist";
    public static final String MAINTENANCE_REPAIR_RECORD_UPDATED_CODE = "014";
    public static final String MAINTENANCE_REPAIR_RECORD_UPDATED_MESSAGE = "Vehicle has been successfully updated";
    public static final String EMAIL_IS_NOT_VALID_CODE = "015";
    public static final String EMAIL_IS_NOT_VALID_MESSAGE = "Enter a valid email";
    public static final String RESERVATION_RECORD_ALREADY_EXIST_CODE = "016";
    public static final String RESERVATION_RECORD_ALREADY_EXIST_MESSAGE = "Reservation already exist already exist";
    public static final String RESERVATION_RECORD_CREATION_CODE = "017";
    public static final String RESERVATION_RECORD_CREATION_MESSAGE = "Reservation record has successfully been created.";
    public static final String RESERVATION_RECORD_NOT_FOUND_CODE = "018";
    public static final String RESERVATION_RECORD_NOT_FOUND_MESSAGE = "Reservation  been found";
    public static final  String ROLE_NOT_FOUND_MESSAGE = "Role not found";
    public static final  String ROLE_EXISTS_MESSAGE = "Role already exists";
    public static final String SUCCESS_MESSAGE = "Successful";
    public static final String USER_DELETED_CODE = "019";
    public static final String USER_DELETED_MESSAGE = "User successfully deleted";
    public static final String RESERVATION_RECORD_FOUND_CODE = "020";
    public static final String RESERVATION_RECORD_FOUND_MESSAGE = "Reservation record retrieved";
    public static final String EMAIL_EXISTS_CODE = "021";
    public static final String EMAIL_EXISTS_MESSAGE = "This email address already exists";

    public static final int lengthOfLicenceNumber = 10;

    public static String generateLicenceNumber(int len){
        String LicenceNumber = "";
        int x;
        char[] stringChars = new char[len];

        for (int i = 0; i < len; i++){
            Random random = new Random();
            x = random.nextInt(9);
            stringChars[i] = Integer.toString(x).toCharArray()[0];
        }

        LicenceNumber = new String(stringChars);
        return LicenceNumber.trim();
    }
}
