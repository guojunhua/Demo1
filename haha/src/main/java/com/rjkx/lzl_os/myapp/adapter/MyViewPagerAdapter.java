package com.rjkx.lzl_os.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private List<String> catalogs;

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> list,
                              List<String> catalogs) {
        super(fm);
        this.list = list;
        this.catalogs = catalogs;
    }

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return catalogs.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
