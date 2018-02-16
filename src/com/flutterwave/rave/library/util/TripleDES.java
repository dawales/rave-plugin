/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author emmanueladeyemi
 */
public class TripleDES {

    private String key;

// Method to turn bytes in hex
    public static String toHexStr(byte[] bytes) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            builder.append(String.format("%02x", bytes[i]));
        }

        return builder.toString();
    }

    // this is the getKey function that generates an encryption Key for you by passing your Secret Key as a parameter.
    public static String getKey(String seedKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] hashedString = md.digest(seedKey.getBytes("utf-8"));
            byte[] subHashString = toHexStr(Arrays.copyOfRange(hashedString, hashedString.length - 12, hashedString.length)).getBytes("utf-8");
            String subSeedKey = seedKey.replace("FLWSECK-", "");
            subSeedKey = subSeedKey.substring(0, 12);
            byte[] combineArray = new byte[24];
            System.arraycopy(subSeedKey.getBytes(), 0, combineArray, 0, 12);
            System.arraycopy(subHashString, subHashString.length - 12, combineArray, 12, 12);
            return new String(combineArray);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getGlobal().log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getGlobal().log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // This is the encryption function that encrypts your payload by passing the stringified format and your encryption Key.
    public static String encryptData(String message, String _encryptionKey) {
        try {
            final byte[] digestOfPassword = _encryptionKey.getBytes("utf-8");
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            final byte[] plainTextBytes = message.getBytes("utf-8");
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
            return Base64.getEncoder().encodeToString(cipherText);

        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }

}
