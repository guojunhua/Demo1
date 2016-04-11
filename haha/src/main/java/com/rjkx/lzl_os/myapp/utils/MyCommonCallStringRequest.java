package com.rjkx.lzl_os.myapp.utils;

import com.google.gson.Gson;

import org.xutils.common.Callback;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/12/23.
 */
public class MyCommonCallStringRequest implements Callback.CommonCallback<String> {
    private Object object;//用于解析json数据的对象，需要什么就传递什么

    public MyCommonCallStringRequest(Object object) {
        this.object = object;
    }

    @Override
    public void onSuccess(String result) {
        object = new Gson().fromJson(result, object.getClass());//解析最终生成的对象类型是有object.getClass()来决定的
        EventBus.getDefault().post(object);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
