package com.lzl_rjkx.doctor.utils;

import com.lzl_rjkx.doctor.bean.FirstEvent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import de.greenrobot.event.EventBus;

/**
 * Created by lzl_os on 16/3/1.
 * 获取AppToken类
 */
public class MyAppTokenRequest {

    public static void requestData() {
        final String url = "http://192.168.1.103:8080/SurgicalKeeper/appAuth/getAppToken.do";
        RequestParams params = new RequestParams(url);
        params.addHeader("USER-AGENT-TYPE", "android");
        params.addBodyParameter("appId", "live_aSBKFzu787M=");
        params.addBodyParameter("appPwd", "app_aSBKFzu787M=");
        params.addBodyParameter("imeiStr", "000");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new FirstEvent(subStr(result)));
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
        });
    }

    private static String subStr(String result) {
        try {
            JSONObject o = new JSONObject(result);
            return o.getString("app_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}