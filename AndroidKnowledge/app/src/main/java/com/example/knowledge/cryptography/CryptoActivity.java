package com.example.knowledge.cryptography;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.knowledge.BaseActivity;
import com.example.knowledge.MainActivity;
import com.example.knowledge.R;
import com.example.knowledge.cryptography.rsa.RSAEncryptUtil;

public class CryptoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    EditText edTextToEncrypt;

    TextView tvEncryptedText;

    TextView tvDecryptedText;

    private CryptoFactory cryptoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        edTextToEncrypt = findViewById(R.id.ed_text_to_encrypt);
        tvEncryptedText = findViewById(R.id.tv_encrypted_text);
        tvDecryptedText = findViewById(R.id.tv_decrypted_text);

        findViewById(R.id.btn_encrypt_aes).setOnClickListener(this);
        findViewById(R.id.btn_decrypt_aes).setOnClickListener(this);
        findViewById(R.id.btn_encrypt_rsa).setOnClickListener(this);
        findViewById(R.id.btn_decrypt_rsa).setOnClickListener(this);

        cryptoFactory = new CryptoFactory();
        IEncrypt iEncrypt = new RSAEncryptUtil();
        cryptoFactory.setStrategy(iEncrypt);
    }

    @Override
    public void onClick(final View view) {

        final int id = view.getId();

        switch (id) {
            case R.id.btn_encrypt_aes:
                encryptText("AES_ALIAS");
                break;
            case R.id.btn_decrypt_aes:
                decryptText("AES_ALIAS");
                break;
            case R.id.btn_encrypt_rsa:
                encryptText("RSA_ALIAS");
                break;
            case R.id.btn_decrypt_rsa:
                decryptText("RSA_ALIAS");
                break;
        }
    }

    private void encryptText(String alias) {
        final String encryptedText = cryptoFactory.getStrategy()
                .encryptText(alias, edTextToEncrypt.getText().toString());
        tvEncryptedText.setText(encryptedText);
    }

    private void decryptText(String alias) {
        final String decryptedText = cryptoFactory.getDecryptText(alias, tvEncryptedText.getText().toString());
        tvDecryptedText.setText(decryptedText);
    }

}