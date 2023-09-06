package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils;

import java.util.Random;

public class ResponseUtils {

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
