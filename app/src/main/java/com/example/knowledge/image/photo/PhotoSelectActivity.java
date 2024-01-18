package com.example.knowledge.image.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.fifedu.lib_common_utils.BitmapUtil;
import com.fifedu.lib_common_utils.FileUtils;
import com.fifedu.lib_common_utils.Uri2PathUtil;
import com.fifedu.lib_common_utils.log.LogUtils;
import com.fifedu.lib_common_utils.permission.PermissionCallBack;
import com.fifedu.lib_common_utils.permission.PermissionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoSelectActivity extends AppCompatActivity {
    private String TAG = "PhotoSelectActivity";
    public static final int REQUEST_FILE_PICKER = 1002;
    private Uri imageUri;
    private String imagePath;
    private ImageView ivCamera;
    public boolean isExam = false;


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
        PermissionUtils.requestPermissions(PhotoSelectActivity.this, false, new PermissionCallBack() {
            @Override
            public void onGranted() {
                //允许了
                takePhoto(REQUEST_FILE_PICKER);
            }

            @Override
            public void onDeniedRational() {
                //勾选不在提示，且拒绝"
            }
        }, PermissionUtils.PERMISSIONS_CAMERA);
    }

    protected void takePhoto(final int requestCode) {
        try {
            //Context.getExternalFilesDir("glide").getPath(); 对应路径：Android/data/<应用程序包>/files/glide  （如果想缓存文件可用这个，会随应用删除而删除，节约用户空间）
            File imageStorageDir = getExternalFilesDir("photo");

            imagePath = imageStorageDir + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg";
            File file = new File(imagePath);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            imageUri = FileUtils.getIntentFileUri(cameraIntent, file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            if (!isExam) {
                Intent imgIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imgIntent.addCategory(Intent.CATEGORY_OPENABLE);
                imgIntent.setType("image/*");
                List<Intent> imgIntents = new ArrayList<>();
                imgIntents.add(cameraIntent);
                Intent chooserIntent = Intent.createChooser(imgIntent, "请选择图片：");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, imgIntents.toArray(new Parcelable[]{}));
                startActivityForResult(chooserIntent, requestCode);
            } else {
                startActivityForResult(cameraIntent, requestCode);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_FILE_PICKER) {
            Uri resultUri = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            if (resultUri != null) {
                // 选择图片结果回调
                imagePath = Uri2PathUtil.getRealPathFromUri(getApplicationContext(), resultUri);
                if (TextUtils.isEmpty(imagePath)) {
                    return;
                }
                imageUri = FileUtils.getFileUri(new File(imagePath));
            }
            if (imageUri != null) {
                try {
                    int degree = BitmapUtil.readPictureDegree(imagePath);
                    Bitmap bitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap rotatedBitmap = BitmapUtil.rotateBitmap(bitMap, degree);
                    ivCamera.setImageBitmap(rotatedBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
