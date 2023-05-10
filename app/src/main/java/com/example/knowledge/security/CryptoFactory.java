package com.example.knowledge.security;

public class CryptoFactory {

    private IEncrypt strategy;

    public void setStrategy(IEncrypt strategy) {
        this.strategy = strategy;
    }

    public IEncrypt getStrategy() {
        return strategy;
    }

    public String getDecryptText(String decryptContent, String alias) {
        return strategy.decryptText(decryptContent, alias);
    }
}
