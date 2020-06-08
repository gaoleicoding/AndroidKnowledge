package com.example.knowledge.recyclerview;

import android.graphics.drawable.Drawable;

/**
 * 介绍：一个普通的JavaBean，但是实现了clone方法，仅仅用于写Demo时，模拟刷新从网络获取数据用，
 * 因为使用DiffUtils比较新老数据集差异时，会遍历新老数据集的每个data，要确保他们的内存地址（指针）不一样，否则比较的是新老data是同一个，就一定相同，
 * 实际项目不需要，因为刷新时，数据一般从网络拉取，并且用Gson等解析出来，内存地址一定是不一样的。
 */
public class Book implements Cloneable {
    private String name;
    private String desc;
    private int pic;
    private boolean isSelected;

    public Book(String name, String desc, int pic) {
        this.name = name;
        this.desc = desc;
        this.pic = pic;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    // 实现克隆方法
    @Override
    public Book clone() throws CloneNotSupportedException {
        Book bean = null;
        try {
            bean = (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }
}