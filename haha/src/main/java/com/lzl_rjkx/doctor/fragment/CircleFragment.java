package com.lzl_rjkx.doctor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzl_rjkx.doctor.customview.CategoryTabStrip;
import com.rjkx.lzl_os.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzl_os on 16/1/27.
 */
public class CircleFragment extends Fragment {

    private CategoryTabStrip tabs;
    private ViewPager vp;
    private List<Fragment> fList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_circle, null);
        initView(v);
        initList();
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(adapter);
        tabs.setViewPager(vp);
        return v;
    }

    public void initView(View v) {
        tabs = (CategoryTabStrip) v.findViewById(R.id.category_strip);
        vp = (ViewPager) v.findViewById(R.id.mvp_circle);
    }

    public void initList() {
        fList = new ArrayList<>();
        fList.add(new TopicFragment());
        fList.add(new ArticleFragment());
        fList.add(new VideoFragment());
        fList.add(new AcademicFragment());
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<String> catalogs = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            catalogs.add("话题");
            catalogs.add("文章");
            catalogs.add("视频");
            catalogs.add("学术");
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
            return fList.get(position);
        }
    }
}
