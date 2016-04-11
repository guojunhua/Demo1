package com.rjkx.lzl_os.myapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.rjkx.lzl_os.myapp.R;
import com.rjkx.lzl_os.myapp.SicknessActivity;
import com.rjkx.lzl_os.myapp.adapter.MyGridAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class LoveFragment extends Fragment {

    @ViewInject(R.id.gridView_search)
    private GridView mGridView;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    private MyGridAdapter adapter;
    private List<String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.love_fragment, null);
        x.view().inject(this, view);
        initData();
        adapter = new MyGridAdapter(getActivity(), data);
        mGridView.setAdapter(adapter);
        mGridView.setPadding(10, 10, 10, 10);

        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),
                        SicknessActivity.class);
                startActivity(intent);
            }
        });

        et_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是测试", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        return view;
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            data.add("创伤科" + i);
        }
    }
}
