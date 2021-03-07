package com.example.knowledge.cryptography;

public interface IEncrypt {

    String encryptText(String alias, String encryptContent);

    String decryptText(String alias, String decryptContent);

}
