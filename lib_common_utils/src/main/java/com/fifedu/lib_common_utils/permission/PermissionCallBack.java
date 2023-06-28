package com.fifedu.lib_common_utils.permission;

/**
 * @author bff007
 * @date 2019/4/3
 */

public abstract class PermissionCallBack {
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
