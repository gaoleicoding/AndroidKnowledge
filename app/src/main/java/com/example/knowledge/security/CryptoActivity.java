package com.example.knowledge.security;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.activity.BaseActivity;
import com.example.knowledge.security.aes.AESEncryptUtil;
import com.example.knowledge.security.rsa.RSAEncryptUtil;

public class CryptoActivity extends BaseActivity implements View.OnClickListener {

    EditText encryptEt;

    TextView encryptTv;

    TextView decryptTv;

    private CryptoFactory cryptoFactory;
    private IEncrypt aesEncrypt = new AESEncryptUtil();
    private IEncrypt rsaEncrypt = new RSAEncryptUtil();

    @Override
    public int getLayoutId() {
        return R.layout.activity_decrypt;
    }

    @Override
    public void initView() {
        encryptEt = findViewById(R.id.et_encrypt);
        encryptTv = findViewById(R.id.tv_encrypt);
        decryptTv = findViewById(R.id.tv_decrypt);

        findViewById(R.id.btn_encrypt_aes).setOnClickListener(this);
        findViewById(R.id.btn_decrypt_aes).setOnClickListener(this);
        findViewById(R.id.btn_encrypt_rsa).setOnClickListener(this);
        findViewById(R.id.btn_decrypt_rsa).setOnClickListener(this);
        findViewById(R.id.tv_encrypt_md5).setOnClickListener(this);

        mTitleTv.setText("Crypto");
    }

    @Override
    public void onClick(final View view) {

        final int id = view.getId();

        switch (id) {
            case R.id.btn_encrypt_aes:
                cryptoFactory.setStrategy(aesEncrypt);
                encryptText("AES_ALIAS");
                break;
            case R.id.btn_decrypt_aes:
                cryptoFactory.setStrategy(aesEncrypt);
                decryptText("AES_ALIAS");
                break;
            case R.id.btn_encrypt_rsa:
                cryptoFactory.setStrategy(rsaEncrypt);
                encryptText("RSA_ALIAS");
                break;
            case R.id.btn_decrypt_rsa:
                cryptoFactory.setStrategy(rsaEncrypt);
                decryptText("RSA_ALIAS");
                break;
            case R.id.tv_encrypt_md5:


                break;
        }
    }


    private void encryptText(String alias) {
        final String encryptedText = cryptoFactory.getStrategy()
                .encryptText(alias, encryptEt.getText().toString());
        encryptTv.setText(encryptedText);
    }

    private void decryptText(String alias) {
        final String decryptedText = cryptoFactory.getDecryptText(alias, encryptTv.getText().toString());
        decryptTv.setText(decryptedText);
    }

    @Override
    public void initData() {
        cryptoFactory = new CryptoFactory();
    }
}