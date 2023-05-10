package com.example.knowledge.security.aes;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import com.example.knowledge.security.IEncrypt;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * 通过AES加密方式，用KeyGenerator生成秘钥，保存在Android Keystore中
 * 对数据进行加解密
 * <p>
 * 1、创建秘钥，保存在AndroidKeystore里面，秘钥别名为alias
 * 2、创建并初始化cipher对象，获取秘钥，对数据进行加解密
 */

public class AESEncryptUtil implements IEncrypt {


    public AESEncryptUtil() {
    }

    //    private static final String ALIAS = "123";
    //  算法/模式/补码方式
    private static String TRANSFORMATION = "AES/GCM/NoPadding";
    private static byte[] encryptIv;

    /**
     * 创建秘钥
     */
    private static void createKey(String ALIAS) {
        //获取Android KeyGenerator的实例
        //设置使用KeyGenerator的生成的密钥加密算法是AES,在 AndroidKeyStore 中保存密钥/数据
        final KeyGenerator keyGenerator;
        AlgorithmParameterSpec spec = null;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            //使用KeyGenParameterSpec.Builder 创建KeyGenParameterSpec ,传递给KeyGenerators的init方法
            //KeyGenParameterSpec 是生成的密钥的参数
            //setBlockMode保证了只有指定的block模式下可以加密,解密数据,如果使用其它的block模式,将会被拒绝。
            //使用了“AES/GCM/NoPadding”变换算法,还需要设置KeyGenParameterSpec的padding类型
            //创建一个开始和结束时间,有效范围内的密钥对才会生成。
            Calendar start = new GregorianCalendar();
            Calendar end = new GregorianCalendar();
            end.add(Calendar.YEAR, 10);//往后加十年

            //todo 高于6.0才可以使用KeyGenParameterSpec 来生成秘钥，低版本呢？
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                spec = new KeyGenParameterSpec.Builder(ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setCertificateNotBefore(start.getTime())
                        .setCertificateNotAfter(end.getTime())
                        .build();
            } else {
//                spec = new ;
            }

            keyGenerator.init(spec);
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }


    public String encryptText(String ALIAS, String needEncrypt) {

        if (!isHaveKeyStore(ALIAS)) {
            createKey(ALIAS);
        }

        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(ALIAS, null);

            SecretKey secretKey = secretKeyEntry.getSecretKey();

            //KeyGenParameterSpecs中设置的block模式是KeyProperties.BLOCK_MODE_GCM,所以这里只能使用这个模式解密数据。
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //ciphers initialization vector (IV)的引用,用于解密
            encryptIv = cipher.getIV();
            return Base64.encodeToString(cipher.doFinal(needEncrypt.getBytes()), Base64.NO_WRAP);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "空指针异常";
        }
        return "";
    }

    public String decryptText(String ALIAS, String needDecrypt) {
        if (!isHaveKeyStore(ALIAS)) {
            createKey(ALIAS);
        }

        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(ALIAS, null);

            SecretKey secretKey = secretKeyEntry.getSecretKey();

            //KeyGenParameterSpecs中设置的block模式是KeyProperties.BLOCK_MODE_GCM,所以这里只能使用这个模式解密数据。
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            //需要为GCMParameterSpec 指定一个认证标签长度(可以是128、120、112、104、96这个例子中我们能使用最大的128),
            // 并且用到之前的加密过程中用到的IV。
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, encryptIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
            return new String(cipher.doFinal(Base64.decode(needDecrypt, Base64.NO_WRAP)));

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 是否创建过秘钥
     *
     * @return
     */
    private static boolean isHaveKeyStore(String ALIAS) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            KeyStore.Entry keyentry = keyStore.getEntry(ALIAS, null);
            if (null != keyentry) {
                return true;
            }

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        return false;
    }
}