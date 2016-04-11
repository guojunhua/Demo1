package com.lzl_rjkx.doctor.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.xutils.x;


/**
 * 所有的Activity继承的基类Activity
 * Created by lzl_os on 16/1/20.
 */

public class BaseActivity extends FragmentActivity {

//    private long lastClickTime;
//    //是否限制点击的开关
//    private boolean clickLimit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        x.view().inject(this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }
//
//    /**
//     * 是否开启重限制点击的开关
//     */
//    protected void setClickLimit(boolean clickLimit) {
//        this.clickLimit = clickLimit;
//    }
//
//    /**
//     * 防止重复点击
//     *
//     * @return
//     */
//    private boolean isFastDoubleClick() {
//        long time = System.currentTimeMillis();
//        long timeD = time - lastClickTime;
//        if (timeD > 300)
//            lastClickTime = time;
//        if (!clickLimit)
//            return timeD < 0;
//        return timeD <= 300;
//    }

    private void initData() {
        //SocializeConstants.APPKEY = "52c4c16956240bce2e08eeb0";
        // 首先在您的Activity中添加如下成员变量
        //final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);

        // 设置分享内容
        //mController.setShareContent("Android开发者必备BaseAnimation应用,一些想要的效果能过快速找到,并添加到自己的应用中,作者博客地址:http://blog.csdn.net/duguang77");


        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appID = "wx88818f8c48a95eb4";
        // 微信图文分享必须设置一个url
        String contentUrl = "http://www.umeng.com/social";
        // 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
        // UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(this,appID, contentUrl);
        //设置分享标题
        // wxHandler.setWXTitle("Android开发者必备BaseAnimation");
        // 支持微信朋友圈
        // UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(this,appID, contentUrl) ;
        //circleHandler.setCircleTitle("一些想要的效果能过快速找到,并添加到自己的应用中,BaseAnimation还不错哦...");

        //  参数1为当前Activity， 参数2为用户点击分享内容时跳转到的目标地址
        // mController.getConfig().supportQQPlatform(this, "http://www.umeng.com/social");

        //mController.getConfig().setSsoHandler(new QZoneSsoHandler(this));

        //设置腾讯微博SSO handler
        //mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    }
}
