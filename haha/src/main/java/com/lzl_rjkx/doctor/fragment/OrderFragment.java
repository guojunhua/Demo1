package com.lzl_rjkx.doctor.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzl_rjkx.doctor.adapter.CommonAdapter;
import com.lzl_rjkx.doctor.adapter.ViewHolder;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.bean.Patient1;
import com.lzl_rjkx.doctor.bean.Patient1.DataEntity.OrderEntity;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.customview.RefreshLayout;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.MyRequestUtils;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by lzl_os on 16/1/27.
 */
public class OrderFragment extends Fragment {
    @ViewInject(R.id.swipe_container)
    private RefreshLayout mRefreshLayout;
    @ViewInject(R.id.common_lv)
    private ListView lv;
    private CommonAdapter<OrderEntity> adapter;
    private List<OrderEntity> data = new ArrayList<>();
    private String appToken;
    private int currentPage = 1;
    private Boolean isRefresh = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.refresh_listview, null);
        x.view().inject(this, view);
        EventBus.getDefault().register(this);
        MyAppTokenRequest.requestData();

        mRefreshLayout.setChildView(lv);
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);

        adapter = new CommonAdapter<OrderEntity>(getActivity(), R.layout.order_item) {
            @Override
            public void convert(ViewHolder helper, OrderEntity item) {
                helper.setText(R.id.patient_name, item.getPName());
                helper.setText(R.id.patient_vip, item.getVip() == 0 ? "精准预约" : "vip会诊");
                String state = null;
                int a = item.getOStatus();
                if (a == 3)
                    state = "待就诊";
                if (a == 4)
                    state = "已就诊";
                if (a == 5)
                    state = "已评价";
                helper.setText(R.id.patient_state, state);
                helper.setText(R.id.patient_time, "预约时间：" + item.getSTime());
                helper.setText(R.id.patient_disease, "疾病：" + item.getPSick());
                helper.setText(R.id.patient_describe, "病情描述：" + item.getPDesc());
            }
        };
        lv.setAdapter(adapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (data.size() == 0) {
                    MyAppTokenRequest.requestData();
                    initData(appToken, currentPage);
                } else {
                    currentPage++;
                    initData(appToken, currentPage);
                    isRefresh = true;
                }
            }
        });

        return view;
    }

    public void initData(String appToken, int currentPage) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_QUERYDOCTOR_ORDER, appToken);
        params.addBodyParameter("userid", Interface.getUserId(getActivity()));
        params.addBodyParameter("page", currentPage + "");

        MyRequestUtils.requestAsObject(params, new Patient1());
    }

    @Subscribe
    public void onEvent(FirstEvent event) {
        this.appToken = event.getmMsg();
        initData(appToken, currentPage);
    }

    @Subscribe
    public void onEvent(Patient1 patient) {
        if (isRefresh) {
            data.addAll(0, patient.getData().getOrder());
            adapter.getDatas(data);
            adapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        } else {
            data.addAll(patient.getData().getOrder());
            adapter.getDatas(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}