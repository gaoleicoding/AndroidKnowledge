package com.example.knowledge.recyclerview.stagger;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.knowledge.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class RVStaggerActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private StaggerAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_stagger);
        initData();
    }


    protected void initData() {
        recyclerView = findViewById(R.id.rv_stagger);
        recyclerAdapter = new StaggerAdapter(getData(), this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //防止item交换位置
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        //以下三行去掉 RecyclerView 动画代码，防止闪烁
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                layoutManager.invalidateSpanAssignments();//防止第一行到顶部有空白
            }
        });
    }


    private List<WaterfullBean> getData() {
        String[] names = {"晓风残月", "杨柳依依", "二月春分", "杏花诗雨随风至，", "中华少年不屈不挠，少年强中国强，壮我中国魂", "铁马冰河", "庐山迷雾", "竹林幽潭", "大唐雄风", "暖风袭的游人醉", "一切疫情都是纸老虎",
                "我自横刀向天笑，去留肝胆两昆仑", "春如一夜春风留春痕", "人生若只是初见", "风雨不曾动我心", "皎皎明月", "流觞", "若水三千"};
        String[] imgHeight = {"260", "300", "100", "220", "180", "260", "200", "320", "200", "300", "250", "280", "150", "240", "200", "280"};
        String path1 = "http://img0.imgtn.bdimg.com/it/u=1595128334,82706458&fm=26&gp=0.jpg";
        String path2 = "http://img2.imgtn.bdimg.com/it/u=3663359702,1992818410&fm=26&gp=0.jpg";
        String path3 = "http://img1.imgtn.bdimg.com/it/u=1935467811,195414982&fm=26&gp=0.jpg";
        String path4 = "http://img1.imgtn.bdimg.com/it/u=2939811527,4256764476&fm=26&gp=0.jpg";
        String path5 = "http://img5.imgtn.bdimg.com/it/u=2433540234,3071973675&fm=11&gp=0.jpg";
        String path6 = "http://img0.imgtn.bdimg.com/it/u=2252193884,3728807126&fm=11&gp=0.jpg";
        String path7 = "http://img5.imgtn.bdimg.com/it/u=3404931913,2642312894&fm=26&gp=0.jpg";
        String path8 = "http://img2.imgtn.bdimg.com/it/u=3656563200,458275370&fm=26&gp=0.jpg";
        String path9 = "http://img3.imgtn.bdimg.com/it/u=272626499,1769311702&fm=11&gp=0.jpg";
        String path10 = "http://img2.imgtn.bdimg.com/it/u=3656563200,458275370&fm=26&gp=0.jpg";
        String path11 = "http://img0.imgtn.bdimg.com/it/u=1641821854,2305393622&fm=15&gp=0.jpg";
        String path12 = "http://img0.imgtn.bdimg.com/it/u=1641821854,2305393622&fm=15&gp=0.jpg";
        String path13 = "http://img.mp.itc.cn/upload/20161019/177f8df712764c3ea2355a5dfed785da_th.gif";
        String path14 = "http://img3.imgtn.bdimg.com/it/u=547082689,2172122564&fm=11&gp=0.jpg";
        String path15 = "http://img4.imgtn.bdimg.com/it/u=4213113895,2522756905&fm=11&gp=0.jpg";
        String path16 = "http://img4.imgtn.bdimg.com/it/u=3043589085,1773439913&fm=26&gp=0.jpg";
        String[] imgUrs = {path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12, path13, path14, path15, path16};
        List<WaterfullBean> waterfullList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            WaterfullBean bean = new WaterfullBean();
            bean.img = imgUrs[i];
            bean.tv = names[i];
            bean.imgHeight = imgHeight[i];
            waterfullList.add(bean);
        }
        return waterfullList;
    }

    //获取随机字符串的 测试textview数据值 可以测试用 不用就删除
    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(10) + 1;//取0~20之间的一个随机数
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);//字符串拼接
        }
        return builder.toString();
    }

}
