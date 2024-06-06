package com.example.knowledge.bean;

public class TopBarData {
    // 小程序栏样式
    public class AppletNaviStyle {
        public String appletNaviStyle;  // 小程序风格: 0: 白色(默认) 1: 黑色
        public String statusBarHexColor; // 状态栏颜色, 默认白色 #FFFFFF
        public String statusBarStyle; // 状态栏风格, 0: 黑色(默认) 1: 白色

    }
    // 导航栏样式
    public class NavigationBarStyle {
        public String backgroundHexColor; //  "导航栏颜色, 默认白色 #FFFFFF",
        public String statusBarStyle; // 状态栏风格, 0: 黑色(默认) 1: 白色",
        public String statusBarHexColor; // 状态栏颜色, 默认白色 #FFFFFF

        public String titleFontSize; // 导航栏字体大小, 默认16",
        public String titleHexColor; // 导航栏字体颜色, 默认黑色",
        public String  backButtonStyle; //导航栏字返回按钮风格: 0: 黑色(默认) 1: 白色"
    }

    public String navigationBarType; // 导航栏类型: 0: 导航条样式(默认) 1: 小程序样式 2: 无导航条无小程序样式
    public AppletNaviStyle appletNaviStyle; // 小程序样式
    public NavigationBarStyle navigationBarStyle;  // 导航栏样式


}
