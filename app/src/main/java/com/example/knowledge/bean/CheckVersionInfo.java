package com.example.knowledge.bean;

/**
 * 存储版本更新信息
 *
 * @author bff007
 * @date 2019/8/5
 */

public class CheckVersionInfo {
    private String apkUrl;//版本下载地址
    private String currentVersionName;//服务器apk版本号
    private CharSequence msg;//版本更新说明
    private String isHaveNewVer;//是否有新版本（0代表没有 1代表有）
    private String isUpdate;//是否强制更新(0代表不强制 1代表强制)
    private String isRemind;//是否出现更新提示
    private boolean isDownloaded;//安装包是否已下载
    private String apkPath;//安装包路径
    private boolean isError;//是否下载出错

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getCurrentVersionName() {
        return currentVersionName;
    }

    public void setCurrentVersionName(String currentVersionName) {
        this.currentVersionName = currentVersionName;
    }

    public CharSequence getMsg() {
        return msg;
    }

    public void setMsg(CharSequence msg) {
        this.msg = msg;
    }

    public String getIsHaveNewVer() {
        return isHaveNewVer;
    }

    public void setIsHaveNewVer(String isHaveNewVer) {
        this.isHaveNewVer = isHaveNewVer;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }


    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    @Override
    public String toString() {
        return "CheckVersionInfo{" +
                "apkUrl='" + apkUrl + '\'' +
                ", currentVersionName='" + currentVersionName + '\'' +
                ", msg=" + msg +
                ", isHaveNewVer='" + isHaveNewVer + '\'' +
                ", isUpdate='" + isUpdate + '\'' +
                ", isRemind='" + isRemind + '\'' +
                ", isDownloaded=" + isDownloaded +
                ", apkPath='" + apkPath + '\'' +
                '}';
    }
}
