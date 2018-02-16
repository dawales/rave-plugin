/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author emmanueladeyemi
 */
public class CryptoUtil {

    public static boolean validateHash(String sentHash, String objectString, String key) {

        String actualHash = sha512(objectString, key);

        return sentHash.equalsIgnoreCase(actualHash);
    }

    public static String sha512(String msg, String salt) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");

            if (salt != null) {
                md.update(salt.getBytes());
            }

            String response = toHexStr(md.digest(msg.getBytes()));

            return response;

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public static final String hmacSHA512(byte[] data, byte[] key){
        
        try{
            
            Mac mac = Mac.getInstance("HmacSHA512");
            
            if(key != null){
                SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA512");
                mac.init(signingKey);
            }
            return toHexStr(mac.doFinal(data));
        }catch(Exception ex){
            if(ex != null)
                ex.printStackTrace();
        }  
        
        return null;
    }

    public static String toHexStr(byte[] bytes) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            builder.append(String.format("%02x", bytes[i]));
        }

        return builder.toString();
    }


    public static String generateHash(String value, String privateKey) {

        String temp = "" + privateKey;
        temp += "" + value;

        return sha512(temp, null);
    }

    public static String DecryptData(String data, String _encrytptionKey) {

        try {
            byte[] message = Base64.getDecoder().decode(data);

            final MessageDigest md = MessageDigest.getInstance("md5");
            final byte[] digestOfPassword = md.digest(_encrytptionKey
                    .getBytes("utf-8"));
            System.out.println("di=" + toHexStr(digestOfPassword));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }

            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, key);

            final byte[] plainText = decipher.doFinal(message);

            return new String(plainText, "UTF-8");
        } catch (Exception e) {
            return "";
        }

    }

    public static String EncryptData(String message, String _encrytptionKey) {
        try {

            final MessageDigest md = MessageDigest.getInstance("md5");
            final byte[] digestOfPassword = md.digest(_encrytptionKey
                    .getBytes("utf-8"));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }

            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            final byte[] plainTextBytes = message.getBytes("utf-8");
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            return "";
        }

    }

    public static String encryptAES(String key, String iv, String data) {

        try {
            byte[] keyBytes = Base64.getDecoder().decode("aGTjQa/Ntz+cB5E010yPLw==");
            byte[] ivByte = Base64.getDecoder().decode(iv);

            byte[] datByte = data.getBytes();
//            byte[] temp = new byte[24];
//            System.arraycopy(keyBytes, 0, temp, 0, 16);
//            System.arraycopy(keyBytes, 0, temp, 16, 8);

            AlgorithmParameterSpec parameterSpec = new IvParameterSpec(ivByte);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, parameterSpec);
//            cipher.update(datByte);
            return toHexStr(cipher.doFinal(datByte));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String decryptAES(String key, String iv, String data) {

        try {
            byte[] keyBytes = Base64.getDecoder().decode("aGTjQa/Ntz+cB5E010yPLw==");
            byte[] ivByte = Base64.getDecoder().decode(iv);

            byte[] datByte = data.getBytes();
//            byte[] temp = new byte[24];
//            System.arraycopy(keyBytes, 0, temp, 0, 16);
//            System.arraycopy(keyBytes, 0, temp, 16, 8);

            AlgorithmParameterSpec parameterSpec = new IvParameterSpec(ivByte);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, newKey, parameterSpec);
//            cipher.update(datByte);
            return new String(cipher.doFinal(hexStringToByteArray(data)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static final byte[] hexStringToByteArray(char[] src, int off, int len) {
        if ((len & 1) != 0) {
            throw new IllegalArgumentException("The argument 'len' can not be odd value");
        }

        final byte[] buffer = new byte[len / 2];

        for (int i = 0; i < len; i++) {
            int nib = src[off + i];

            if ('0' <= nib && nib <= '9') {
                nib = nib - '0';
            } else if ('A' <= nib && nib <= 'F') {
                nib = nib - 'A' + 10;
            } else if ('a' <= nib && nib <= 'f') {
                nib = nib - 'a' + 10;
            } else {
                throw new IllegalArgumentException("The argument 'src' can contains only HEX characters");
            }

            if ((i & 1) != 0) {
                buffer[i / 2] += nib;
            } else {
                buffer[i / 2] = (byte) (nib << 4);
            }
        }

        return buffer;
    }

    public static final byte[] hexStringToByteArray(char[] src) {
        return hexStringToByteArray(src, 0, src.length);
    }

    public static final byte[] hexStringToByteArray(String s) {
        char[] src = s.toCharArray();
        return hexStringToByteArray(src);
    }

    public static final byte[] hexStringToByteArray(String s, char delimiter) {
        char src[] = s.toCharArray();
        int srcLen = 0;

        for (char c : src) {
            if (c != delimiter) {
                src[srcLen++] = c;
            }
        }

        return hexStringToByteArray(src, 0, srcLen);
    }

    public static String harden(String key, String unencryptedString) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] plainTextBytes = unencryptedString.getBytes("utf-8");
        byte[] buf = cipher.doFinal(plainTextBytes);
        byte[] base64Bytes = Base64.getEncoder().encode(buf);
        String base64EncryptedString = new String(base64Bytes);

        return base64EncryptedString;
    }

    public static String soften(String key, String encryptedString) throws UnsupportedEncodingException, NoSuchAlgorithmException, 
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (encryptedString == null) {
            return "";
        }
        byte[] message = Base64.getDecoder().decode(encryptedString);

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");

        Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] plainText = decipher.doFinal(message);

        return new String(plainText, "UTF-8");

    }

}
