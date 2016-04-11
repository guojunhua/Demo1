package com.lzl_rjkx.doctor.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.rjkx.lzl_os.myapp.R;

/**
 * Created by lzl_os on 16/3/3.
 */
public class SelfInfoActivity extends BaseActivity {

    private TextView tv_title;
    private ImageView iv_back, iv_isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
        initView();
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title);
        iv_back.setImageResource(R.mipmap.ic_back);
        iv_isShow.setVisibility(View.INVISIBLE);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
        });
    }

    public void initView() {
        tv_title = (TextView) findViewById(R.id.top_title);
        iv_back = (ImageView) findViewById(R.id.iv_search);
        //iv_isShow = (ImageView) findViewById(R.id.publish_mood);
    }
}
