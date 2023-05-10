package com.example.knowledge.bean;

public class ChildBean {

    String content;
    String className;

    public ChildBean(String state, String className) {
        this.content = state;
        this.className = className;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

