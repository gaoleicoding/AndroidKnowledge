package com.example.knowledge.cryptography;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.knowledge.BaseActivity;
import com.example.knowledge.MainActivity;
import com.example.knowledge.R;
import com.example.knowledge.cryptography.aes.AESEncryptUtil;
import com.example.knowledge.cryptography.rsa.RSAEncryptUtil;
import com.example.knowledge.utils.ToastUtil;

public class CryptoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    EditText encryptEt;

    TextView encryptTv;

    TextView decryptTv;

    private CryptoFactory cryptoFactory;
    private IEncrypt aesEncrypt = new AESEncryptUtil();
    private IEncrypt rsaEncrypt = new RSAEncryptUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cryptoFactory = new CryptoFactory();


    }

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

        tvHeader.setText("CryptoActivity");
    }

    @Override
    public void onClick(final View view) {

        final int id = view.getId();
        if (TextUtils.isEmpty(encryptEt.getText().toString().trim())) {
            ToastUtil.showToast(CryptoActivity.this, "请输入加密内容");
            return;
        }
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

}