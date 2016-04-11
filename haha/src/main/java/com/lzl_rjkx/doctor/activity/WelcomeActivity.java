package com.lzl_rjkx.doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.utils.PreferenceUtils;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {
    private Boolean isFirst;
    private final Context context = this;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = null;
            switch (msg.what) {
                case 1:
                    if (isFirst) {
                        intent = new Intent(context, SplashActivity.class);
                        PreferenceUtils.setPrefBoolean(context, "isFirst", false);
                    } else if (PreferenceUtils.getPrefBoolean(context, "firstLogin", true)) {
                        intent = new Intent(context, LoginActivity.class);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                    }
                    break;
            }
            startActivity(intent);
            //startActivity(new Intent(context, DoctorMainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSwipeBackLayout().setEnableGesture(false);
        isFirst = PreferenceUtils.getPrefBoolean(context, "isFirst", true);
        mHandler.sendEmptyMessageDelayed(1, 3000);

    }
}
