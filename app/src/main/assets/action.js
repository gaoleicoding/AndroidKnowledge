
function action(code) {
    var command;
    if (code == 1) {
        // 获取位置
         command = {
                    appAction: "getUserCurrentLocation",         // Web调用原生方法: 移动端开始获取用户位置
                    webAction: "setUserCurrentLocation",         // 原生调用Web方法: 移动端将用为位置传递给Web端
                }
    } else if (code == 2) {

    } else if (code == 3) {
        // 显示导航栏
        command = {
            appAction : "modifyNavigationBarStyle",
            data : {
                navigationBarType: "0",
                navigationBarStyle : {
                    backgroundHexColor: "#00FF00",        // 导航栏颜色, 默认白色
                    statusBarStyle: "1",                  // 状态栏风格(状态栏字体颜色), 0: 黑色(默认) 1: 白色
                    statusBarHexColor: "#00FF00",         // 状态栏背景色，优先级高于 statusBarStyle
                    titleFontSize: "14",                  // 导航栏字体大小, 默认14sp
                    titleHexColor: "#000000",             // 导航栏字体颜色, 默认黑色
                    backButtonStyle: "0"                  // 导航栏字返回按钮风格: 0: 黑色(默认) 1: 白色              
                }
            }
        }

    } else if (code == 4) {
        // 亮主题小程序控件
        command = {
            appAction : "modifyNavigationBarStyle",
            data : {
                navigationBarType: "1", // 导航栏类型: 0: 导航条样式(默认) 1: 小程序样式 2: 无导航条无小程序样式
                appletNaviStyle : {
                    appletNaviStyle: "0",        // 小程序样式: 0: 白色(默认) 1: 黑色
                    statusBarHexColor: "#FFFFFF",         // 状态栏背景色，优先级高于 statusBarStyle
                    statusBarStyle: "1"                // 状态栏风格(状态栏字体颜色), 0: 黑色(默认) 1: 白色
                }
        
            }
        }
    } else if (code == 5) {
        // 暗主题小程序控件
        command = {
            appAction : "modifyNavigationBarStyle",
            data : {
                navigationBarType: "1", // 导航栏类型: 0: 导航条样式(默认) 1: 小程序样式 2: 无导航条无小程序样式
                appletNaviStyle : {
                    appletNaviStyle: "1",        // 小程序样式: 0: 白色(默认) 1: 黑色
                    statusBarHexColor: "#FFFFFF",         // 状态栏背景色，优先级高于 statusBarStyle
                    statusBarStyle: "1"                // 状态栏风格(状态栏字体颜色), 0: 黑色(默认) 1: 白色
                }
        
            }
        }
    } else if (code == 6) {
        // 隐藏顶部栏
        command = {
            appAction : "modifyNavigationBarStyle",
            data : {
                navigationBarType: "2", // 导航栏类型: 0: 导航条样式(默认) 1: 小程序样式 2: 无导航条无小程序样式
            }
        }
    }

    callNative(command);
}

function callNative(command) {
    console.log('命令类型：' + typeof(command));
    var jsonString = JSON.stringify(command);
    console.log('json 字符串：' + typeof(jsonString) + " " + jsonString);
    if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
        // ios, webCallNative 可替换为iOS 注册消息体
        window.webkit.messageHandlers.webCallNative.postMessage(jsonString);
    } else {
        // android androidInjected
        window.androidInjected?.webCallNative(jsonString);

    }
}