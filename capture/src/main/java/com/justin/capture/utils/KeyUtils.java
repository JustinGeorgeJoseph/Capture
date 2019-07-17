package com.justin.capture.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeyUtils {

    public static String getMD5EncryptedKey(final String value) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(value.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
