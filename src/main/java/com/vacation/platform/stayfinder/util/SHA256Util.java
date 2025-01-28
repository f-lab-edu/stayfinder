package com.vacation.platform.stayfinder.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

    public static String encrypt(String data) throws NoSuchAlgorithmException {
        if(data == null) return "";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());

        return byteToHex(md.digest());
    }

    private static String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
