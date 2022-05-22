package com.example.knowledge.view.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.utils.LogUtil;
import com.example.knowledge.utils.PermissionUtils;

import java.io.File;
import java.util.Calendar;

public class PhotoSelectActivity extends AppCompatActivity {
    private String TAG = "PhotoSelectActivity";
    private static final int SET_PIC_BY_CAMERA = 1001;// 拍照获取照片
    private Uri photoUri;
    private String photoPath;
    private ImageView ivCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_select);
        ivCamera = findViewById(R.id.iv_camera);
        findViewById(R.id.tv_camera).setOnClickListener(v -> {
            requestCameraPermission();
        });
    }

    private void requestCameraPermission() {
        //判断是否有读取sdcard权限
        PermissionUtils.requestPermissions(PhotoSelectActivity.this, false, new PermissionUtils.PermissionCallBack() {
            @Override
            public void onGranted() {
                //允许了
                takePhoto();
            }

            @Override
            public void onDeniedRational() {
                //勾选不在提示，且拒绝"
            }
        }, PermissionUtils.PERMISSION_CAMERA);
    }

    protected void takePhoto() {
        try {
            Intent intent;
            File rootDir = new File(PicUtils.getPicRootDirectory());
            if (!rootDir.exists()) {
                rootDir.mkdirs();
            }
            photoPath = PicUtils.getPicRootDirectory() + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg";
            File pictureFile = new File(photoPath);

            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //这一句非常重要
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                photoUri = FileProviderUtil.getFileProviderUri(pictureFile);
            } else {
                photoUri = Uri.fromFile(pictureFile);
            }
            // 调用前置摄像头
            //intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            // 去拍照,拍照的结果存到 photoUri对应的路径中
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SET_PIC_BY_CAMERA);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SET_PIC_BY_CAMERA) {
            if (photoUri != null) {
                try {
                    int degree = PicUtils.readPicRotate(photoPath);
                    Bitmap bitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    Bitmap result = PicUtils.rotatePic(bitMap, degree);
                    ivCamera.setImageBitmap(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
