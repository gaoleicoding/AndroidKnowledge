package com.example.knowledge.security;

public interface IEncrypt {

    String encryptText(String alias, String encryptContent);

    String decryptText(String alias, String decryptContent);

}
