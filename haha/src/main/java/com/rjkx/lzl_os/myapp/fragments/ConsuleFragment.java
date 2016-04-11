package com.rjkx.lzl_os.myapp.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.rjkx.lzl_os.myapp.R;
import com.rjkx.lzl_os.myapp.adapter.CommonAdapter;
import com.rjkx.lzl_os.myapp.adapter.ViewHolder;
import com.rjkx.lzl_os.myapp.customview.RefreshableView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class ConsuleFragment extends Fragment {

    @ViewInject(R.id.refreshable_view)
    private RefreshableView refreshableView;
    @ViewInject(R.id.et_consul_search)
    private EditText et_search;
    @ViewInject(R.id.consul_lv)
    private ListView lv;
    @ViewInject(R.id.ll_right)
    private LinearLayout ll_right;
    private CommonAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consule_fragment, null);
        x.view().inject(this, view);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("医生" + i);
        }

        adapter = new CommonAdapter(getActivity(), data, R.layout.doctor_item) {
            @Override
            public void convert(ViewHolder helper, Object item) {
                helper.setImageResource(R.id.iv_doctor, R.mipmap.doctor);
                helper.setExText(R.id.expand_text_view, item + "完全诶哦王企鹅接哦我去集合完全融化我去哦额合计为契机鹅鹅鹅鹅鹅鹅鹅鹅鹅饿鹅鹅鹅饿其我去哦IE加我企鹅鹅鹅鹅饿鹅鹅鹅饿鹅鹅鹅饿饿哦无IQ手机的撒娇的我去叫饿哦无IQ金额我去哦额及完全偶尔接哦我IQ金额我接我奇迹饿哦我我去接诶我饥饿哦我叫我去哦纪委  我就恶趣味我哦饿哦我为契机诶哦千万家饿哦诶我就");
            }
        };
        lv.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                SystemClock.sleep(1000);

                refreshableView.finishRefreshing();
            }
        }, 0);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ll_right.setVisibility(View.GONE);
                } else {
                    ll_right.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    @Event(value = {R.id.ll_city, R.id.iv_patient, R.id.iv_delete, R.id.iv_search})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.ll_city:
                Toast.makeText(getActivity(), "选择城市", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_patient:
                Toast.makeText(getActivity(), "选择就诊人", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_delete:
                et_search.setText("");
                break;
            case R.id.iv_search:
                break;
        }
    }
}
