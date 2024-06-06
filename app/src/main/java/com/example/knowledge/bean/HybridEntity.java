package com.example.knowledge.bean;

import com.google.gson.Gson;

/**
 * H5 和原生交互传输数据实体
 */
public class HybridEntity {
    private String appAction; // Web调用原生方法
    private String webAction; // 原生调用Web方法
    private Object data;

    public String getAppAction() {
        return appAction;
    }

    public void setAppAction(String appAction) {
        this.appAction = appAction;
    }

    public String getWebAction() {
        return webAction;
    }

    public void setWebAction(String webAction) {
        this.webAction = webAction;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public TopBarData getTopBarData() {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return gson.fromJson(json, TopBarData.class);
    }

}
