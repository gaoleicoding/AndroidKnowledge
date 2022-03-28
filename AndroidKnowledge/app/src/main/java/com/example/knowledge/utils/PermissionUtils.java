package com.example.knowledge.utils;

import android.Manifest;
import androidx.fragment.app.FragmentActivity;
import com.example.knowledge.dialog.PermissionDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;

/**
 * 权限申请
 * Created by baiqingtian on 2019/5/14.
 */

public class PermissionUtils {

    //需要处理的权限
    public static final String[] PERMISSIONS_ALL = new String[]{
            Manifest.permission.RECORD_AUDIO,//录音权限(可单独检测申请)
            Manifest.permission.READ_EXTERNAL_STORAGE,//读文件权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写文件权限
            Manifest.permission.READ_PHONE_STATE,//读取设备信息
    };
    public static final String PERMISS_CAMERA = Manifest.permission.CAMERA;//相机权限单独判断
    public static final String PERMISS_AUDIO = Manifest.permission.RECORD_AUDIO;//录音权限强制获取
    public static final String[] permissions_record = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISS_LOCAL_RACORD = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};//录音权限强制获取
    public static String[] permissions_file = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 请求权限
     *
     * @param activity           fragmentActivity
     * @param isExit             是否退出程序
     * @param permissionCallBack
     * @param permissions
     */
    public static void requestPermissions(final FragmentActivity activity, final boolean isExit, final PermissionCallBack permissionCallBack, String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(activity);
        try {
            rxPermissions.requestEachCombined(permissions)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                //全部同意后调用
                                if (permissionCallBack != null) {
                                    permissionCallBack.onGranted();
                                }
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                //只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                if (permissionCallBack != null) {
                                    permissionCallBack.onDenied();
                                }
                            } else {
                                //只要有一个选择：禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                if (permissionCallBack != null) {
                                    //2020年4.14日新需求修改拒绝权限也能进入系统
                                    if (isExit) {
                                        //首页的话算是拒绝能进入
                                        permissionCallBack.onDenied();
                                    } else {
                                        //不是首页
                                        PermissionDialog.showPermissionDialog(activity, isExit);
                                        permissionCallBack.onDeniedRational();
                                    }
//
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            if (permissionCallBack != null) {
                LogUtil.e("requestPermissions==", "Exception===");
                permissionCallBack.onException();
            }
        }

    }

    /**
     * 判断是否有录音权限（只检测录音权限）
     *
     * @return
     */
    public static boolean isHasAudioPermission(FragmentActivity activity) {
        return checkPermissionsIsGranted(activity, PERMISS_AUDIO);
    }

    /**
     * 检查某个权限是否被申请
     *
     * @param activity
     */
    public static boolean checkPermissionsIsGranted(FragmentActivity activity, String... permissions) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.setLogging(true);
        boolean isGranted = true;
        for (String permission : permissions) {
            if (!rxPermission.isGranted(permission)) {
                isGranted = false;
                break;
            }

        }
        return isGranted;
    }

    interface PermissionCallBack {
        //所有权限被通过
        void onGranted();

        //权限被拒绝
        void onDenied();

        //权限被选择不再提醒且拒绝
        void onDeniedRational();


        //发生异常
        void onException();

    }
}

