package com.example.knowledge.cryptography;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.knowledge.BaseActivity;
import com.example.knowledge.MainActivity;
import com.example.knowledge.R;
import com.example.knowledge.cryptography.rsa.RSAEncryptUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CryptoActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.ed_text_to_encrypt)
    EditText edTextToEncrypt;

    @BindView(R.id.tv_encrypted_text)
    TextView tvEncryptedText;

    @BindView(R.id.tv_decrypted_text)
    TextView tvDecryptedText;

    private CryptoFactory cryptoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        ButterKnife.bind(this);
        cryptoFactory = new CryptoFactory();
        IEncrypt iEncrypt = new RSAEncryptUtil();
        cryptoFactory.setStrategy(iEncrypt);
    }

    @OnClick({R.id.btn_encrypt_aes, R.id.btn_decrypt_aes, R.id.btn_encrypt_rsa, R.id.btn_decrypt_rsa})
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