package com.example.knowledge.design;

import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.knowledge.BaseActivity;
import com.example.knowledge.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;


public class CollapseViewPagerActivity extends BaseActivity {
    private String[] titles = {
            "头条",
            "新闻",
            "娱乐",
            "体育",
            "科技",
            "美女",
            "财经",
            "汽车",
            "房子",
            "头条"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse_viewpager);
        ViewPager viewPager = findViewById(R.id.vp);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar_layout);
        ImageView head_layout =findViewById(R.id.image);
        CollapsingToolbarLayout mCollapsingToolbarLayout =  findViewById(R.id.collapsing_toolbar_layout);
//        mCollapsingToolbarLayout.setTitle("");
        for (String tab : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            Log.d("gaolei","head_layout.getHeight()："+head_layout.getHeight());
            Log.d("gaolei","verticalOffset："+verticalOffset);
            if (verticalOffset <= -head_layout.getHeight() / 2) {

            } else {

            }
        });
        //1.TabLayout和Viewpager关联
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabUnselected(TabLayout.Tab arg0) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 被选中的时候回调
                viewPager.setCurrentItem(tab.getPosition(), true);
            }
            @Override
            public void onTabReselected(TabLayout.Tab arg0) {
                // TODO Auto-generated method stub

            }
        });
        //2.ViewPager滑动关联tabLayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setAdapter(adapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new NewsDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", titles[position]);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return titles.length;
        }

    }


}
