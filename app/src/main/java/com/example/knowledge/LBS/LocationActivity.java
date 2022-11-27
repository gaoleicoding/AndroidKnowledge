package com.example.knowledge.LBS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.knowledge.AppApplication;
import com.example.knowledge.R;
import com.example.knowledge.dialog.PermissionDialog;
import com.example.knowledge.dialog.DialogUtils;
import com.example.knowledge.utils.LogUtil;
import com.example.knowledge.utils.ToastUtil;
import com.example.knowledge.utils.Utils;

import java.io.IOException;
import java.util.List;

public class LocationActivity extends AppCompatActivity {
    private final String TAG = "LocationActivity";
    public static final int LOCATION_CODE = 301;
    private LocationManager mLocationManager;
    private Location location;
    private String locationProvider;
    private TextView textView1, textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        textView1 = findViewById(R.id.tv_location1);
        textView2 = findViewById(R.id.tv_location2);
        requestPermissionLocation();
    }

    private void requestPermissionLocation() {
        //1.获取位置管理器
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
            } else {
                //3.获取上次的位置，一般第一次运行，此值为null
                uploadLocation();
            }
        } else {
            uploadLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                try {
                    uploadLocation();

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                PermissionDialog.showPermissionDialog(LocationActivity.this, "位置", false);

            }
        }
    }
    private void uploadLocation() {
        getLastLocation();
        //第二次获取位置即第一次获取的最新位置。避免第一次getLastKnownLocation为空和获取的上次位置距离获取当前位置已经偏移很远
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                getLastLocation();
                double longitude = 0;
                double latitude = 0;
                if (location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    LogUtil.d(TAG, "uploadLocation：" + longitude + "   " + latitude);
                }

                if (AppApplication.isDebug) {
                    showAddress(latitude, longitude);
                }
            }
        }, 500);

    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        try {
            if (mLocationManager == null) {
                mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            }
            List<String> providers = mLocationManager.getProviders(true);
            if (providers.size() == 0) {
                ToastUtil.showToast(this, getString(R.string.no_location_provider));
                return;
            }

            if (providers.contains(LocationManager.NETWORK_PROVIDER) && Utils.isNetConnected(this)) {
                //首先Network，定位精度高
                locationProvider = LocationManager.NETWORK_PROVIDER;
                LogUtil.d(TAG, "网络定位方式");
            } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                // 次选GPS，因为偏差较大，直线距离偏差大概有600-700米
                locationProvider = LocationManager.GPS_PROVIDER;
                LogUtil.d(TAG, "GPS定位方式");
            }
            if (locationProvider != null) {
                location = mLocationManager.getLastKnownLocation(locationProvider);
            }
//        for (String proqvider : providers) {
//            @SuppressLint("MissingPermission")
//            Location l = mLocationManager.getLastKnownLocation(provider);
//            if (l == null) {
//                continue;
//            }
//            if (location == null || l.getAccuracy() < location.getAccuracy()) {
//                // Found best last known location: %s", l);
//                location = l;
//                locationProvider = provider;
//            }
//        }

            if (location == null) {
                mLocationManager.requestLocationUpdates(locationProvider, 2000, 1, locationListener);

            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getLastLocation-e:" + e.getMessage());
        }

    }

    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                //如果位置发生变化，重新显示地理位置经纬度
                textView1.setText("位置发生变化：" + location.getLongitude() + " " +
                        location.getLatitude());
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(locationListener);
    }


    public void showAddress(double latitude, double longitude) {
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressList != null) {
            for (Address address : addressList) {
                LogUtil.d(TAG, String.format("address: %s", address.toString()));
            }
            String showAddress = null;
            int size = addressList.size();
            if (size == 1) {
                showAddress = String.format("address: %s", addressList.get(0).toString());
            } else if (size > 1) {
                showAddress = String.format("address: %s", addressList.get(size / 2).toString());
            } else {
                showAddress = "没有获取到位置信息";
            }
            String title = null;
            if (LocationManager.NETWORK_PROVIDER.equals(locationProvider)) {
                title = "NETWORK";
            } else if (LocationManager.GPS_PROVIDER.equals(locationProvider)) {
                title = "GPS";
            }
            DialogUtils.createCommonDialog(this, title, showAddress, "知道了", "取消", DialogUtils.DIALOG_SHOWTYPE_ONE, true, new DialogUtils.DialogCallBack() {
                @Override
                public void onConfirm() {
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }
}