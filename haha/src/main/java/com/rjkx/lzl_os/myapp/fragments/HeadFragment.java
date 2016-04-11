package com.rjkx.lzl_os.myapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rjkx.lzl_os.myapp.R;
import com.rjkx.lzl_os.myapp.VideoActivity;
import com.rjkx.lzl_os.myapp.VideoPlayActivity;
import com.rjkx.lzl_os.myapp.adapter.MyHeadPagerAdapter;
import com.rjkx.lzl_os.myapp.customview.AutoScrollViewPager;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/22.
 */
public class HeadFragment extends Fragment {

    @ViewInject(R.id.my_grid)
    private GridView myGrid;
    @ViewInject(R.id.mVp)
    private AutoScrollViewPager vp;
    @ViewInject(R.id.kt_list)
    private ListView kt_lv;
    @ViewInject(R.id.dt_list)
    private ListView dt_lv;

    private List<Map<String, Object>> gridList;
    private SimpleAdapter gridAdapter;
    private MyHeadPagerAdapter pagerAdapter;
    private List<ImageView> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.head_fragment, null);
        x.view().inject(this, view);
        getGridList();
        gridAdapter = new SimpleAdapter(getActivity(), gridList,
                R.layout.head_grid, new String[]{"img", "name"}, new int[]{
                R.id.imageView1, R.id.textView1});
        myGrid.setAdapter(gridAdapter);

        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });

        pagerAdapter = new MyHeadPagerAdapter(list);
        vp.setAdapter(pagerAdapter);
        vp.startAutoScroll(3000);
        vp.setInterval(3000);
        vp.setSelected(true);
        vp.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

        kt_lv.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, new String[]{"视频1",
                "视频2", "视频3"}));

        kt_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("title", "视频" + position + 1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });

        dt_lv.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, new String[]{"文章1",
                "文章2", "文章3"}));
        return view;
    }

    @Event(value = {R.id.iv_morekt, R.id.iv_moredt})
    private void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_morekt:
                intent = new Intent(getActivity(), VideoActivity.class);
                break;
            case R.id.iv_moredt:
                Toast.makeText(getActivity(), "骨科动态", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), VideoActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void getGridList() {
        String[] names = {"快速预约", "预约会诊", "快速挂号"};
        int[] imgs = {R.mipmap.head_icon1, R.mipmap.head_icon2,
                R.mipmap.head_icon3};
        gridList = new ArrayList<>();
        list = new ArrayList<>();
//        int[] icons = {R.mipmap.banner_loading, R.mipmap.banner_failed,
//                R.mipmap.banner_faileding};
        String[] icons = {"http://img4.imgtn.bdimg.com/it/u=3701433706,2171570602&fm=11&gp=0.jpg", "http://img2.imgtn.bdimg.com/it/u=406240065,2540596649&fm=11&gp=0.jpg", "http://img2.imgtn.bdimg.com/it/u=3874580467,3204113675&fm=11&gp=0.jpg"};

        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", imgs[i]);
            map.put("name", names[i]);
            gridList.add(map);
            ImageView iv = new ImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            x.image().bind(iv, icons[i]);
            list.add(iv);
        }
    }
}
