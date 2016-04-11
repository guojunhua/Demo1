package com.lzl_rjkx.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by lzl_os on 16/1/26.
 */

@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(R.id.tv_start)
    private void onClik(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
