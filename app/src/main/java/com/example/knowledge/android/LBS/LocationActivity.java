package com.example.knowledge.android.LBS;

import android.view.View;

import com.example.knowledge.R;
import com.example.knowledge.activity.BaseActivity;
import com.example.knowledge.bean.LocationData;
import com.example.knowledge.databinding.ActivityLocationBinding;
import com.fifedu.lib_common_utils.dialog.BaseDialogCallBack;
import com.fifedu.lib_common_utils.dialog.BaseDialogUtils;

public class LocationActivity extends BaseActivity {
    private final String TAG = "LocationActivity";
    ActivityLocationBinding locationBinding;

    @Override

    public int getLayoutId() {
        return R.layout.activity_location;
    }

    @Override
    public void initView() {
        mTitleTv.setText("Location");
        locationBinding = ActivityLocationBinding.inflate(getLayoutInflater());
        findViewById(R.id.bt_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LocationUtil jsCallAppUtil = new LocationUtil(LocationActivity.this);
                jsCallAppUtil.requestLocation(new LocationUtil.LocationCallBack() {
                    @Override
                    public void onLocation(LocationData locationData) {
                        showAddressDialog(locationData);
                    }
                });
            }
        });
    }

    @Override
    public void initData() {

    }

    public void showAddressDialog(LocationData locationData) {
        if (locationData == null) return;
        String title = locationData.getLoacationStyle();
        BaseDialogUtils.createCommonDialog(this, title, locationData.getAddress(), "知道了", "取消", BaseDialogUtils.DIALOG_SHOWTYPE_ONE, true, new BaseDialogCallBack() {
            @Override
            public void onConfirm() {
            }

            @Override
            public void onCancel() {
            }
        });
    }

}