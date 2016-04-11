package com.rjkx.lzl_os.myapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by Administrator on 2015/12/22.
 */
public class MineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, null);
        x.view().inject(this, view);
        return view;
    }

    @Event(value = {R.id.iv_phone, R.id.ll_pay, R.id.ll_see, R.id.ll_evaluate, R.id.ll_manage, R.id.ll_balance, R.id.ll_case, R.id.ll_help})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.iv_phone:
                Toast.makeText(getActivity(), "手机", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_pay:
                Toast.makeText(getActivity(), "待支付", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_see:
                Toast.makeText(getActivity(), "待就诊", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_evaluate:
                Toast.makeText(getActivity(), "待评价", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_manage:
                Toast.makeText(getActivity(), "就诊人管理", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_balance:
                Toast.makeText(getActivity(), "账户余额", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_case:
                Toast.makeText(getActivity(), "我的病例", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_help:
                Toast.makeText(getActivity(), "帮助与反馈", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
