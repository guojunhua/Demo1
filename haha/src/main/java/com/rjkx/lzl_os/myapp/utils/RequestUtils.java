package com.rjkx.lzl_os.myapp.utils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/23.
 */
public class RequestUtils {

    public static void get(Map map) {
        //反射获取到调用的类，然后反射获取对应的数据类型的注解，获取到解析格式的对象包名，根据工厂模式 通过包名返回请求地址，具体的参数是map传递的
        //在这里新建请求参数，将map中的参数放进去，地址放进去

        RequestParams params = new RequestParams("www.baidu.com");
        x.http().request(HttpMethod.GET, params, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {

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

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });
    }
}
