package com.lzl_rjkx.doctor.utils;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/3/18.
 */
public class MyParams {

    public final static RequestParams getParams(String url,String appToken){
        RequestParams params=new RequestParams(url);
        params.addHeader("USER-AGENT-TYPE", "android");
        params.addBodyParameter("appToken", appToken);
        params.addBodyParameter("imeiStr", "000");
        return params;
    }
}
