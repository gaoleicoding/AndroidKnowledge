package com.example.knowledge.lbs;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.knowledge.R;
import com.example.knowledge.dialog.PermissionDialog;

import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    public static final int LOCATION_CODE = 301;
    private LocationManager locationManager;
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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
                getLastLocation();
            }
        } else {
            getLastLocation();
        }
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        Location location = null;

        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            @SuppressLint("MissingPermission")
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (location == null || l.getAccuracy() < location.getAccuracy()) {
                // Found best last known location: %s", l);
                location = l;
                locationProvider = provider;
            }
        }
        if (location != null) {

            textView1.setText("经纬度：" + location.getLongitude() + " " +
                    location.getLatitude());
            getAddress(location);

        } else {
            //监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                try {
                    getLastLocation();

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                PermissionDialog.showPermissionDialog(LocationActivity.this, "位置", false);

            }
        }
    }


    //获取地址信息:城市、街道等信息
    private void getAddress(Location location) {
        List<Address> addressList;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                addressList = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                Address address = addressList.get(0);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(address.getLocality());
                stringBuilder.append(address.getSubLocality());
                stringBuilder.append(address.getSubAdminArea());
                stringBuilder.append(address.getThoroughfare());
                int index = address.getMaxAddressLineIndex() / 2;
                stringBuilder.append(address.getAddressLine(index));
                textView2.setText("地址信息：" + stringBuilder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }


}