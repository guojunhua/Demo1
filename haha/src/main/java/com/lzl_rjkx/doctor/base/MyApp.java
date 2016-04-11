package com.lzl_rjkx.doctor.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.xutils.x;

/**
 * Created by lzl_os on 16/1/25.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(true);
    }

    public SharedPreferences getSharedPreferences(Context context) {
        return getSharedPreferences("config", context.MODE_PRIVATE);
    }
}
