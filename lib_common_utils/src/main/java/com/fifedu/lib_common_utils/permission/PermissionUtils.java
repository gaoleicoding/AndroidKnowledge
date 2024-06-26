package com.fifedu.lib_common_utils.permission;

import android.Manifest;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.FragmentActivity;

import com.fifedu.lib_common_utils.ContextProvider;
import com.fifedu.lib_common_utils.R;
import com.fifedu.lib_common_utils.SystemUtil;
import com.fifedu.lib_common_utils.dialog.BaseDialogCallBack;
import com.fifedu.lib_common_utils.dialog.BaseDialogUtils;
import com.fifedu.lib_common_utils.log.LogUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 权限申请
 */

public class PermissionUtils {

    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;//相机权限单独判断
    public static final String PERMISSION_AUDIO = Manifest.permission.RECORD_AUDIO;//录音权限强制获取
    public static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;//录音权限强制获取
    public static final String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String AUTHORITY_NOTICE = "authority_notice";//通知权限

    public static final String[] PERMISSIONS_ALL = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            AUTHORITY_NOTICE
    };
    public static final String[] PERMISSIONS_VIDEO = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISSIONS_VIDEO_SIMPLE = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    public static final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISSIONS_CAMERA_SIMPLE = {Manifest.permission.CAMERA};
    public static final String[] PERMISSIONS_FILE = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISSIONS_FILE_R = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE};
    private static final boolean isEqualOrAboveVersion = SystemUtil.isEqualOrAboveVersion(Build.VERSION_CODES.R);
    public static final String[] PERMISSIONS_MANAGE_FILE = !isEqualOrAboveVersion ? PERMISSIONS_FILE : PERMISSIONS_FILE_R;
    public static final String[] PERMISSIONS_RECORD = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISSIONS_RECORD_SIMPLE = {Manifest.permission.RECORD_AUDIO};
    public static String[] PERMISIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE};
    public static String[] PERMISIONS_LOCATION_SIMPLE = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static String PERMISSION_CAMERA_DES = ContextProvider.getAppContext().getString(R.string.lib_utils_perm_camera_des);
    public static String PERMISSION_AUDIO_DES = ContextProvider.getAppContext().getString(R.string.lib_utils_perm_audio_des);
    public static String PERMISSION_VIDEO_DES = ContextProvider.getAppContext().getString(R.string.lib_utils_perm_video_des);
    public static String PERMISSION_STORAGE_DES = ContextProvider.getAppContext().getString(R.string.lib_utils_perm_storage_des);
    public static String PERMISSION_LOCATION_DES = ContextProvider.getAppContext().getString(R.string.lib_utils_perm_location_des);

    public static String PERMISSION_COMMON_DES = ContextProvider.getAppContext().getString(R.string.lib_utils_perm_common_des);
    public static final String CAMERA = ContextProvider.getAppContext().getString(R.string.lib_utils_camera_des);
    public static final String AUDIO = ContextProvider.getAppContext().getString(R.string.lib_utils_audio_des);
    public static final String VIDEO = ContextProvider.getAppContext().getString(R.string.lib_utils_video_des);
    public static final String STORAGE = ContextProvider.getAppContext().getString(R.string.lib_utils_storage_des);
    public static final String LOCATION = ContextProvider.getAppContext().getString(R.string.lib_utils_location_des);
    public static boolean isShowingPermissionDialog = false; // 是否正在展示权限弹窗

    private static void delaySet() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isShowingPermissionDialog = false;
            }
        }, 1000);
    }

    /**
     * 请求权限
     *
     * @param activity fragmentActivity
     * @param isExit   是否退出当前页面
     */
    public static void requestPermissions(final FragmentActivity activity, final boolean isExit, final PermissionCallBack permissionCallBack, final String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    isShowingPermissionDialog = true;
                    boolean isAllPermissionsGranted = checkPermissionsIsGranted(activity, permissions);
                    if (isAllPermissionsGranted) {
                        isShowingPermissionDialog = false;
                        realRequestPermissions(activity, permissionCallBack, isExit, permissions);
                    } else {
                        String title = activity.getString(R.string.lib_utils_perm_request_title);
                        String content = null;
                        List<String> permissionList = Arrays.asList(permissions);
                        if (permissionList.contains(PERMISSION_CAMERA) && permissionList.contains(PERMISSION_AUDIO)) {
                            content = PERMISSION_VIDEO_DES;
                        } else if (permissionList.contains(PERMISSION_CAMERA)) {
                            content = PERMISSION_CAMERA_DES;
                        } else if (permissionList.contains(PERMISSION_AUDIO)) {
                            content = PERMISSION_AUDIO_DES;
                        } else if (permissionList.contains(PERMISSION_STORAGE)) {
                            content = PERMISSION_STORAGE_DES;
                        } else if (permissionList.contains(PERMISSION_LOCATION)) {
                            content = PERMISSION_LOCATION_DES;
                        } else {
                            content = PERMISSION_COMMON_DES;
                        }
                        String confirm = activity.getString(R.string.lib_utils_confirm);
                        String cancel = activity.getString(R.string.lib_utils_cancel);
                        BaseDialogUtils.createCommonDialog(activity, title, content, confirm, cancel, BaseDialogUtils.DIALOG_SHOWTYPE_NORMAL, false, new BaseDialogCallBack() {
                            @Override
                            public void onConfirm() {
                                realRequestPermissions(activity, permissionCallBack, isExit, permissions);
                            }

                            @Override
                            public void onCancel() {
                                permissionCallBack.onDenied();
                                delaySet();
                                if (isExit) activity.finish();
                            }
                        });
                    }
                } catch (Throwable e) {
                }
            }
        });


    }

    private static void realRequestPermissions(final FragmentActivity activity, final PermissionCallBack permissionCallBack, final boolean isExit, final String... permissions) {
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
                                    if (isExit) {
                                        activity.finish();
                                    } else {
                                        permissionCallBack.onDenied();
                                    }
                                }
                            } else {
                                //只要有一个选择：禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                if (permissionCallBack != null) {

                                    String content = "";
                                    List<String> permissionList = Arrays.asList(permissions);
                                    if (permissionList.contains(PERMISSION_CAMERA) && permissionList.contains(PERMISSION_AUDIO)) {
                                        content = VIDEO;
                                    } else if (permissionList.contains(PERMISSION_CAMERA)) {
                                        content = CAMERA;
                                    } else if (permissionList.contains(PERMISSION_AUDIO)) {
                                        content = AUDIO;
                                    } else if (permissionList.contains(PERMISSION_STORAGE)) {
                                        content = STORAGE;
                                    } else if (permissionList.contains(PERMISSION_LOCATION)) {
                                        content = LOCATION;
                                    }
                                    //不是首页
                                    PermissionDialog.showPermissionDialog(activity, content, isExit);
                                    permissionCallBack.onDeniedRational();
                                }
                            }
                            delaySet();
                        }
                    });
        } catch (Exception e) {
            if (permissionCallBack != null) {
                LogUtils.e("requestPermissions==", e.getMessage() + "===");
                permissionCallBack.onException();
            }
            delaySet();
        }
    }

    /**
     * 判断是否有录音权限（只检测录音权限）
     */
    public static boolean isHasAudioPermission(FragmentActivity activity) {
        return checkPermissionsIsGranted(activity, PERMISSION_AUDIO);
    }

    /**
     * 检查某个权限是否被申请
     */
    public static boolean checkPermissionsIsGranted(FragmentActivity activity, String... permissions) {
        try {
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
        } catch (Exception e) {

        }
        return false;
    }
}

