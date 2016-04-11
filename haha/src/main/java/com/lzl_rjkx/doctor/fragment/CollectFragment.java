package com.lzl_rjkx.doctor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rjkx.lzl_os.myapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzl_os on 16/3/3.
 */
public class CollectFragment extends Fragment {
    private ListView lv;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int index = getArguments().getInt("index", 0);
        View view = inflater.inflate(R.layout.common_listview, null);
        lv = (ListView) view.findViewById(R.id.common_lv);
        initAdapter();
        //adapter = new SimpleAdapter(getActivity(), data, R.layout.article_item, new String[]{"img", "title", "author", "pubTime"}, new int[]{R.id.article_icon, R.id.article_title, R.id.article_author, R.id.article_pubTime});
        if (index == 0) {
            adapter = new SimpleAdapter(getActivity(), data, R.layout.article_item, new String[]{"img", "title", "author", "pubTime"}, new int[]{R.id.article_icon, R.id.article_title, R.id.article_author, R.id.article_pubTime});
        } else if (index == 1) {
            adapter = new SimpleAdapter(getActivity(), data, R.layout.article_item, new String[]{"img", "title", "author", "pubTime"}, new int[]{R.id.article_icon, R.id.article_title, R.id.article_author, R.id.article_pubTime});
        }
        lv.setAdapter(adapter);
        return view;
    }

    private void initAdapter() {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + "");
            map.put("img", R.mipmap.icon);
            map.put("title", "红楼梦" + i);
            map.put("author", "曹雪芹" + i);
            map.put("pubTime", "18世纪中叶");
            data.add(map);
        }
    }
}
