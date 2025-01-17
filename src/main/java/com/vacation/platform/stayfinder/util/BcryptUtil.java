package com.vacation.platform.stayfinder.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil  {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public static String hash(String data) {
        return encoder.encode(data);
    }


    public static boolean matches(String data, String hashData) {
        return encoder.matches(data, hashData);
    }


}
