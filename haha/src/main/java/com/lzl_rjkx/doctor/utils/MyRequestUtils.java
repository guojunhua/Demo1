package com.lzl_rjkx.doctor.utils;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import de.greenrobot.event.EventBus;

/**
 * Created by lzl_os on 16/3/16.
 * 网络请求数据类，图片下载类
 */
public class MyRequestUtils {
    private static Object object;

    public final static void requestAsObject(final RequestParams params, final Object obj) {
        object = obj;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                object = new Gson().fromJson(result, object.getClass());
                EventBus.getDefault().post(object);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.d("错误信息:" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
