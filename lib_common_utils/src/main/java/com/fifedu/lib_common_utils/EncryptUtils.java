package com.fifedu.lib_common_utils;


import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {
    public static final String PASSWORD = "fifedu)!@(*#$&^%";
    private static final String IV_STRING = "A-16-BYTE:FIFEDU";
    private static final String CHARSET = "UTF-8";
    private static final String PATTERN_ECB = "ECB";
    public static final String PATTERN_CBC = "CBC";

    public static String encrypt(String content, String password, String pattern) {

        switch (pattern) {

            case PATTERN_ECB:

                return encrypt_ECB(content, password);

            case PATTERN_CBC:

                return encrypt_CBC(content, password);

        }
        return "";
    }

    public static String decrypt(String content, String password, String pattern) {

        switch (pattern) {

            case PATTERN_ECB:

                return decrypt_ECB(content, password);

            case PATTERN_CBC:

                return decrypt_CBC(content, password);

        }
        return "";
    }


    /**
     * 加密解密
     */
    public static String cryptoAES(String content, Integer type) {

        switch (type) {
            case 1:
                return encrypt_CBC(content, PASSWORD);

            case 2:
                return decrypt_CBC(content, PASSWORD);

        }
        return "";
    }


    // ECB 模式
    private static String encrypt_ECB(String content, String password) {

        try {

            byte[] contentBytes = content.getBytes(CHARSET);
            byte[] passwordBytes = password.getBytes(CHARSET);

            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            kgen.init(128, new SecureRandom(passwordBytes));

            // 生成密钥
            SecretKey key = kgen.generateKey();

            // 创建密码器
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // 加密
            byte[] result = cipher.doFinal(contentBytes);

            return Base64.encodeToString(result, Base64.DEFAULT);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "";
    }

    private static String decrypt_ECB(String content, String password) {

        try {

            byte[] passwordBytes = password.getBytes(CHARSET);
            byte[] contentBytes = Base64.decode(content, Base64.DEFAULT);

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(passwordBytes));

            SecretKey key = kgen.generateKey();

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] result = cipher.doFinal(contentBytes);

            return new String(result);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "";
    }

    // CBC模式
    private static String encrypt_CBC(String content, String password) {
        try {
            byte[] contentBytes = content.getBytes(CHARSET);
            byte[] passwordBytes = password.getBytes(CHARSET);
            // 生成密钥
            SecretKeySpec key = new SecretKeySpec(passwordBytes, "AES");
            // 设置偏移量
            byte[] initParam = IV_STRING.getBytes(CHARSET);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 初始化密码器
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            // 加密
            byte[] result = cipher.doFinal(contentBytes);
            return Base64.encodeToString(result, Base64.DEFAULT);
        } catch (Exception e) {
        }

        return "";
    }

    private static String decrypt_CBC(String content, String password) {

        try {

            //password = secureRandom(password);

            byte[] passwordBytes = password.getBytes(CHARSET);
            byte[] contentBytes = Base64.decode(content.getBytes(), Base64.DEFAULT);

            SecretKeySpec key = new SecretKeySpec(passwordBytes, "AES");

            byte[] initParam = IV_STRING.getBytes(CHARSET);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

            byte[] result = cipher.doFinal(contentBytes);

            return new String(result);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "";
    }

}
