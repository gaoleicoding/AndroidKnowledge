package com.example.knowledge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.knowledge.R;
import com.example.knowledge.bean.ChildBean;

import java.util.List;

public class MyExListAdapter extends BaseExpandableListAdapter {

    Context context;
    private List<List<ChildBean>> child ;           //一共有两个List 第一个是组别，第二个是组别中的数据
    private List<String> group;                  //组的名称

    public MyExListAdapter(Context context, List<String> group, List<List<ChildBean>> child){
         this.context = context;
         this.child = child;
         this.group = group;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();                   //需要注意
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder viewHolder;
        if(convertView == null){
            viewHolder = new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.eplistv_test_group,null);
            //对viewHolder的属性进行赋值
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.ivArrow = convertView.findViewById(R.id.iv_arrow);
            //使用setTag缓存起来方便多次重用
            convertView.setTag(viewHolder);
        }
        else{ //如果缓存池中有对应的缓存，则直接通过getTag取出viewHolder
            viewHolder = (GroupHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(group.get(groupPosition));
        if(isExpanded){
            viewHolder.ivArrow.setImageResource(R.drawable.arrow_down);
        }else {
            viewHolder.ivArrow.setImageResource(R.drawable.arrow_right);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder viewHolder;
        if(convertView == null){
            viewHolder = new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.eplistv_test_child,null);
            viewHolder.tvState= convertView.findViewById(R.id.tv_state);
            //使用setTag缓存起来方便多次重用
            convertView.setTag(viewHolder);
        }
        else{ //如果缓存池中有对应的缓存，则直接通过getTag取出viewHolder
            viewHolder = (ChildHolder) convertView.getTag();
        }
        ChildBean info = (ChildBean) child.get(groupPosition).get(childPosition);
        viewHolder.tvState.setText(info.getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        public TextView tvTitle;
        public ImageView ivArrow;
    }
    class ChildHolder {
        public TextView  tvState;
    }
}

