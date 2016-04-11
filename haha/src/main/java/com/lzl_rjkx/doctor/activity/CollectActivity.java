package com.lzl_rjkx.doctor.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzl_rjkx.doctor.adapter.MyPagerAdapter;
import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.fragment.CollectFragment;
import com.rjkx.lzl_os.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzl_os on 16/2/21.
 */
public class CollectActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private TextView tv_article, tv_video;
    private RelativeLayout rl_article, rl_video;
    private ViewPager vp;

    private List<Fragment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
        for (int i = 0; i < 2; i++) {
            CollectFragment ft = new CollectFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            ft.setArguments(bundle);
            list.add(ft);
        }

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), list);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(this);
    }

    public void initView() {
        tv_article = (TextView) findViewById(R.id.tv_article);
        tv_video = (TextView) findViewById(R.id.tv_video);
        rl_article = (RelativeLayout) findViewById(R.id.rl_article);
        rl_video = (RelativeLayout) findViewById(R.id.rl_video);
        vp = (ViewPager) findViewById(R.id.vp_collect);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                break;
            case R.id.ll_article:
                change(0);
                vp.setCurrentItem(0);
                break;
            case R.id.ll_video:
                change(1);
                vp.setCurrentItem(1);
                break;
        }
    }

    public void change(int index) {

        //int color1 = getResources().getColor(R.color.colorBlack);
        //int color2 = getResources().getColor(R.color.colorBlack);

        if (index == 0) {
            rl_article.setVisibility(View.VISIBLE);
            rl_video.setVisibility(View.GONE);
            //color1 = getResources().getColor(R.color.top_bg);
        } else if (index == 1) {
            rl_article.setVisibility(View.GONE);
            rl_video.setVisibility(View.VISIBLE);
            //color2 = getResources().getColor(R.color.top_bg);
        }
//        tv_article.setTextColor(color1);
//        tv_video.setTextColor(color2);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        change(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}