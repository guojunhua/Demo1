package com.lzl_rjkx.doctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lzl_rjkx.doctor.activity.CollectActivity;
import com.lzl_rjkx.doctor.activity.LoginActivity;
import com.lzl_rjkx.doctor.activity.SelfInfoActivity;
import com.lzl_rjkx.doctor.utils.PreferenceUtils;
import com.rjkx.lzl_os.myapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzl_os on 16/1/27.
 */
public class MineFragment extends Fragment {
    private ListView lv;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_mine, null);
        lv = (ListView) v.findViewById(R.id.common_lv);

        //inal String[] items = getResources().getStringArray(R.array.mine);
        int[] imgs = {R.mipmap.mine_collect, R.mipmap.mine_clinic, R.mipmap.mine_money, R.mipmap.mine_about, R.mipmap.mine_advice, R.mipmap.mine_collect};
        for (int i = 0; i < imgs.length; i++) {
            Map<String, Object> map = new HashMap<>();
            //map.put("name", items[i]);
            map.put("img", imgs[i]);
            data.add(map);
        }

        adapter = new SimpleAdapter(getActivity(), data, R.layout.mine_item, new String[]{"name", "img"}, new int[]{R.id.mine_textView, R.id.mine_former});

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (position == 0) {
                    intent = new Intent(getActivity(), CollectActivity.class);
                } else if (position == 5) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    PreferenceUtils.setPrefBoolean(getActivity(), "firstLogin", true);
                    getActivity().finish();
                } else {
                    intent = new Intent(getActivity(), SelfInfoActivity.class);
                    //intent.putExtra("title", items[position]);
                }
                startActivity(intent);
            }
        });
        return v;
    }
}
