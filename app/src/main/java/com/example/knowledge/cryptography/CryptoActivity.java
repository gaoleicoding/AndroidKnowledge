package com.example.knowledge.cryptography;

import static com.example.knowledge.utils.FileUtils.geFileUri;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knowledge.BaseActivity;
import com.example.knowledge.MainActivity;
import com.example.knowledge.R;
import com.example.knowledge.cryptography.aes.AESEncryptUtil;
import com.example.knowledge.cryptography.rsa.RSAEncryptUtil;
import com.example.knowledge.utils.PermissionUtils;
import com.example.knowledge.utils.ToastUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.util.List;

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
        findViewById(R.id.tv_encrypt_md5).setOnClickListener(this);

        tvHeader.setText("加解密");
    }

    @Override
    public void onClick(final View view) {

        final int id = view.getId();
//        if (TextUtils.isEmpty(encryptEt.getText().toString().trim())) {
//            ToastUtil.showToast(CryptoActivity.this, "请输入加密内容");
//            return;
//        }
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

                getFileMD5();

                break;
        }
    }

    private void getStringMD5() {
        encryptEt.setText("aioralxs00110001Sx3bM3Lj");
        String content = encryptEt.getText().toString();
        String md5 = MD5Util.md5(content);
        encryptTv.setText(md5);
    }

    private void getFileMD5() {
        PermissionUtils.requestPermissions(CryptoActivity.this, false, new PermissionUtils.PermissionCallBack() {
            @Override
            public void onGranted() {
                //允许了/mnt/sdcard/Android/data/io.dcloud.HBuilder/downloads/progress_dialog_icon.png
                String path = "/storage/emulated/0/Android/data/io.dcloud.HBuilder/downloads/fif_2.1.9.apk";

                File file = new File(path, "fif_2.1.9.apk");
                Uri uri = geFileUri(file);

                if (file.exists()) {
                    String md5 = MD5Util.getFileMD5(file);
                    Toast.makeText(CryptoActivity.this, md5, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onDeniedRational() {
                //勾选不在提示，且拒绝"
            }
        }, PermissionUtils.PERMISSIONS_FILE);
    }

    private void getFileMD52() {
        XXPermissions.with(this)
                // 申请单个权限
                // 申请多个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                // 设置权限请求拦截器（局部设置）
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制（局部设置）
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            String path = "/storage/emulated/0/Android/data/io.dcloud.HBuilder/downloads/";
                            File file = new File(path, "fif_2.1.9.apk");
                            if (file.exists()) {
                                String md5 = MD5Util.getFileMD5(file);
                                Toast.makeText(CryptoActivity.this, md5, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            ToastUtil.showToast(CryptoActivity.this, "获取部分权限成功，但部分权限未正常授予");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            ToastUtil.showToast(CryptoActivity.this, "被永久拒绝授权，请手动授予权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(CryptoActivity.this, permissions);
                        } else {
                            ToastUtil.showToast(CryptoActivity.this, "获取权限失败");
                        }
                    }
                });
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