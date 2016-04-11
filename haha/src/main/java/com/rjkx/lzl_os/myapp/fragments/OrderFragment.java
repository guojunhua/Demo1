package com.rjkx.lzl_os.myapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rjkx.lzl_os.myapp.R;
import com.rjkx.lzl_os.myapp.customview.TimePickerView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/22.
 */
public class OrderFragment extends Fragment {

    @ViewInject(R.id.pre_time)
    private EditText pre_time;
    @ViewInject(R.id.next_time)
    private EditText next_time;
    @ViewInject(R.id.et_sickDesc)
    private EditText et_sickDesc;
    private TimePickerView pvTime;
    private EditText et = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment, null);
        x.view().inject(this, view);
        // 时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.ALL);
        pvTime.setTime(new Date());
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                et.setText(getTime(date));
            }
        });
        return view;
    }

    @Event(value = {R.id.pre_time, R.id.next_time})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.pre_time:
                et = pre_time;
                pvTime.show();
                break;
            case R.id.next_time:
                et = next_time;
                pvTime.show();
                break;
        }
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
