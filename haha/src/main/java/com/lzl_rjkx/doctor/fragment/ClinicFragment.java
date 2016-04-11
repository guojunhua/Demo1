package com.lzl_rjkx.doctor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rjkx.lzl_os.myapp.R;


/**
 * Created by lzl_os on 16/2/23.
 */
public class ClinicFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_welcome, null);
        return v;
    }
}
