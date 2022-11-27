package com.example.knowledge.utils;

import android.Manifest;

import androidx.fragment.app.FragmentActivity;

import com.example.knowledge.dialog.PermissionDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class PermissionUtils {

    public static final String[] PERMISSION_CAMERA = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISSION_RECORD = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] PERMISSION_FILE = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] PERMISION_LOCATION = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

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
                                        PermissionDialog.showPermissionDialog(activity, "", isExit);
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
        return checkPermissionsIsGranted(activity, Manifest.permission.RECORD_AUDIO);
    }

    public static void getPermissions(final FragmentActivity context, final PermissionCallBack permissionCallBack, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(context);
        rxPermissions.setLogging(true);
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
//                                    PermissionDialog.showPermissionDialog(context, false);
                                    permissionCallBack.onDeniedRational();
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            if (permissionCallBack != null) {
                permissionCallBack.onException();
            }
        }
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

    public static abstract class PermissionCallBack {
        //所有权限被通过
        public abstract void onGranted();

        //权限被拒绝
        public void onDenied() {
        }

        //权限被选择不再提醒且拒绝
        public void onDeniedRational() {

        }

        //发生异常
        public void onException() {
        }

    }
}

