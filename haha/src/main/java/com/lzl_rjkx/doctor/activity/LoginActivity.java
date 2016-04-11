package com.lzl_rjkx.doctor.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.PreferenceUtils;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by lzl_os on 16/1/26.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.cb_remember)
    private CheckBox cb_remember;
    private ProgressDialog dialog;
    private final Context context = this;
    private String appToken, name, pwd;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtils.showToast(LoginActivity.this, "登录成功...");
                    writeUserInfo(name, pwd, false);
                    dialog.dismiss();
                    startActivity(new Intent(context, DoctorMainActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSwipeBackLayout().setEnableGesture(false);
        dialog = new ProgressDialog(context);
        dialog.setMessage("正在登录...");
        dialog.setCanceledOnTouchOutside(false);

        EventBus.getDefault().register(this);
        //判断记住密码多选框的状态
        if (PreferenceUtils.getPrefBoolean(context, "isChecked", false)) {
            et_name.setText(PreferenceUtils.getPrefString(context, "userName", ""));
            et_pwd.setText(PreferenceUtils.getPrefString(context, "pwd", ""));
            cb_remember.setChecked(true);
        }

        if (!PreferenceUtils.getPrefBoolean(context, "isFirst", false)) {
            et_name.setText(PreferenceUtils.getPrefString(context, "userName", ""));
        }

        cb_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PreferenceUtils.setPrefBoolean(context, "isChecked", true);
                } else {
                    PreferenceUtils.setPrefBoolean(context, "isChecked", false);
                }
            }
        });
        MyAppTokenRequest.requestData();
    }

    @Subscribe
    public void onEvent(FirstEvent event) {
        this.appToken = event.getmMsg();
    }

    @Event(value = {R.id.btn_login, R.id.tv_forget})
    private void onClick(View v) {
        name = et_name.getText().toString().trim();
        pwd = et_pwd.getText().toString();
        switch (v.getId()) {
            case R.id.btn_login:
                if ("".equals(name) || "".equals(pwd)) {
                    ToastUtils.showToast(this, "请输入用户名或密码。。。");
                } else {
                    dialog.show();
                    RequestParams params = MyParams.getParams(HttpConstants.HTTP_LOGININ, appToken);
                    params.addBodyParameter("mobile", name);
                    params.addBodyParameter("pwd", pwd);
                    params.addBodyParameter("clientid", "1111");
                    params.addBodyParameter("usertype", "3");
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            saveDocId(result);
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
                break;
            case R.id.tv_forget:
                Intent intent = new Intent(this, PwdFindActivity.class);
                intent.putExtra("appToken", appToken);
                startActivity(intent);
                break;
        }
    }

    /**
     * @说明: 保存登录名、密码和登陆状态
     * @author lzl_os
     */

    public void writeUserInfo(String userName, String pwd, boolean firstLogin) {
        PreferenceUtils.setPrefString(context, "userName", userName);
        PreferenceUtils.setPrefString(context, "pwd", pwd);
        PreferenceUtils.setPrefBoolean(this, "firstLogin", firstLogin);
    }

    /**
     * @说明: 保存医生id
     * @author lzl_os
     */
    public void saveDocId(String json) {
        try {
            JSONObject o = new JSONObject(json);
            switch (o.getInt("code")) {
                case 0:
                    handler.sendEmptyMessageDelayed(1, 3000);
                    JSONObject o1 = o.getJSONObject("data");
                    PreferenceUtils.setPrefInt(context, "docId", o1.getInt("docId"));
                    break;
                case -5:
                    SystemClock.sleep(2000);
                    ToastUtils.showToast(this, "用户名或密码错误");
                    dialog.dismiss();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}