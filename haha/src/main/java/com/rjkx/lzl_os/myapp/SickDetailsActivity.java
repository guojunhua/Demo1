package com.rjkx.lzl_os.myapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.rjkx.lzl_os.myapp.customview.CategoryTabStrip;
import com.rjkx.lzl_os.myapp.fragments.NewsFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_sick_detils)
public class SickDetailsActivity extends AppCompatActivity {
    @ViewInject(R.id.category_strip)
    private CategoryTabStrip tabs;
    @ViewInject(R.id.view_pager)
    private ViewPager pager;
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_detils);
        x.view().inject(this);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
        });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<String> catalogs = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            String[] tabs = getResources().getStringArray(R.array.tabs);
            for (int i = 0; i < tabs.length; i++) {
                catalogs.add(tabs[i]);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catalogs.get(position);
        }

        @Override
        public int getCount() {
            return catalogs.size();
        }

        @Override
        public Fragment getItem(int position) {
            return NewsFragment.newInstance(position);
        }
    }
}
