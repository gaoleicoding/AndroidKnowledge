package com.example.knowledge.android.LBS;

//调用h5方法

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.bean.HybridEntity;
import com.example.knowledge.bean.LocationData;
import com.fifedu.lib_common_utils.SystemUtil;
import com.fifedu.lib_common_utils.ToastUtil;
import com.fifedu.lib_common_utils.log.LogUtils;
import com.fifedu.lib_common_utils.permission.PermissionCallBack;
import com.fifedu.lib_common_utils.permission.PermissionDialog;
import com.fifedu.lib_common_utils.permission.PermissionUtils;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.ValueCallback;

import java.io.IOException;
import java.util.List;

public class LocationUtil {
    private final String TAG = "JSCallAppLocationUtil";
    public static final String getUserCurrentLocation = "getUserCurrentLocation";

    private final AppCompatActivity mActivity;
    private WebView mWebView;
    private LocationManager mLocationManager;
    private String callNativeAction;
    private String callWebAction;
    private Location location;
    private String locationProvider = null;

    public LocationUtil(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void handleLocation(WebView wv_content, String callNativeAction, String callWebAction) {
        mWebView = wv_content;
        this.callNativeAction = callNativeAction;
        this.callWebAction = callWebAction;
        requestLocation(null);

    }

    public void requestLocation(LocationCallBack callBack) {

        PermissionUtils.requestPermissions(mActivity, false, new PermissionCallBack() {
            @Override
            public void onGranted() {
                getLocation(callBack);
            }

            @Override
            public void onDenied() {
                PermissionDialog.showPermissionDialog(mActivity, "位置", false);
            }

        }, PermissionUtils.PERMISIONS_LOCATION);
    }

    private void getLocation(LocationCallBack callBack) {
        getLastLocation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //第二次获取位置即第一次获取的最新位置。避免第一次getLastKnownLocation为空和获取的上次位置距离获取当前位置已经偏移很远
                getLastLocation();
                double longitude;
                double latitude;
                if (location != null) {
                    LocationData locationData = new LocationData();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    locationData.setLongitude(longitude + "");
                    locationData.setLatitude(latitude + "");
                    locationData.setAddress(getAddress(latitude, longitude));
                    locationData.setLoacationStyle(locationProvider);

                    if (mWebView != null) {
                        sendLocationToJS(locationData);
                    }
                    if (callBack != null) {
                        callBack.onLocation(locationData);
                    }
                    LogUtils.d(TAG, "uploadLocation：" + longitude + "   " + latitude);
                }

            }
        }, 500);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        try {
            if (mLocationManager == null) {
                mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
            }
            List<String> providers = mLocationManager.getProviders(true);
            if (providers.size() == 0) {
                ToastUtil.showToast(mActivity, mActivity.getString(R.string.no_location_provider));
                return;
            }

            if (providers.contains(LocationManager.NETWORK_PROVIDER) && SystemUtil.isNetConnected()) {
                //首先Network，定位精度高
                locationProvider = LocationManager.NETWORK_PROVIDER;
                LogUtils.d(TAG, "网络定位方式");
            } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                // 次选GPS，因为偏差较大，直线距离偏差大概有600-700米
                locationProvider = LocationManager.GPS_PROVIDER;
                LogUtils.d(TAG, "GPS定位方式");
            }
            if (locationProvider != null) {
                location = mLocationManager.getLastKnownLocation(locationProvider);
            }

        } catch (Exception e) {
            LogUtils.e(TAG, "getLastLocation-e:" + e.getMessage());
        }
    }

    private void sendLocationToJS(LocationData data) {

        HybridEntity h5CallAppData = new HybridEntity();
        h5CallAppData.setAppAction(callNativeAction);
        h5CallAppData.setWebAction(callWebAction);
        h5CallAppData.setData(data);

        Gson gson = new Gson();
        String json = gson.toJson(h5CallAppData);
        LogUtils.d(TAG, "nativeCallWeb: " + json);
        mWebView.evaluateJavascript("javascript:nativeCallWeb(" + json + ")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
            }
        });
    }

    public String getAddress(double latitude, double longitude) {
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(mActivity);
        String showAddress = null;
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressList != null) {
            Address address;
            int size = addressList.size();
            if (size == 1) {
                address = addressList.get(0);
                showAddress = address.getAddressLine(0);
            } else if (size > 1) {
                address = addressList.get(size / 2);
                showAddress = address.getAddressLine(0);
            } else {
                showAddress = "没有获取到位置信息";
            }
        }
        return showAddress;
    }

    interface LocationCallBack {
        void onLocation(LocationData locationData);
    }
}