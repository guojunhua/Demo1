package com.lzl_rjkx.doctor.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyHeadPagerAdapter extends PagerAdapter {

    private List<ImageView> mList;

    public MyHeadPagerAdapter(List<ImageView> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }
}
