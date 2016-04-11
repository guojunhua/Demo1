package com.rjkx.lzl_os.myapp.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2015/12/23.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);//是否开启debug模式
    }
}
