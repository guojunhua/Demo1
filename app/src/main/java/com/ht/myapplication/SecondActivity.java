package com.ht.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by 郭君华 on 2016/4/11.
 * Email：guojunhua3369@163.com
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity);
    }
}
