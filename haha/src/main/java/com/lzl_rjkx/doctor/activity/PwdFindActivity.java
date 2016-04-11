package com.lzl_rjkx.doctor.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.utils.AppUtils;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2016/3/18.
 */
@ContentView(R.layout.find_pwd)
public class PwdFindActivity extends BaseActivity {
    @ViewInject(R.id.phone_number)
    private EditText phone_number;
    @ViewInject(R.id.ensure_number)
    private EditText ensure_number;
    @ViewInject(R.id.get_ensure_number)
    private TextView get_ensure_number;
    @ViewInject(R.id.btn_submit)
    private Button submit;

    private String appToken;
    private String ensureNum;
    private String phoneNumber;
    private Timer timer = new Timer();
    private int recLen = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appToken = getIntent().getStringExtra("appToken");
        ensure_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    submit.setClickable(false);
                    submit.setBackgroundResource(R.drawable.common_disable_bg);
                } else {
                    submit.setClickable(true);
                    submit.setBackgroundResource(R.drawable.common_shape_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Subscribe
    public void onEvent(FirstEvent event) {
        this.appToken = event.getmMsg();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    get_ensure_number.setText(recLen + "s后重新获取");
                    get_ensure_number.setClickable(false);
                    if (recLen == 0) {
                        timer.cancel();
                        get_ensure_number.setText("重新获取验证码");
                        get_ensure_number.setClickable(true);
                    }
                }
            });
        }
    };

    @Event(value = {R.id.iv_back, R.id.get_ensure_number, R.id.btn_submit})
    private void onClick(View v) {
        phoneNumber = phone_number.getText().toString().trim();
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                break;
            case R.id.get_ensure_number:
                if (phoneNumber.equals("")) {
                    ToastUtils.showToast(this, "请先输入手机号");
                } else {
                    if (AppUtils.isMobile(phoneNumber)) {
                        getEnsureNumber(phoneNumber);
                        timer.schedule(task, 1000);
                    } else {
                        ToastUtils.showToast(this, "您输入的号码有误，请检查后重新输入！");
                    }
                }
                break;
            case R.id.btn_submit:
                String number = ensure_number.getText().toString().trim();
                if (number.equals(ensureNum)) {
                    findPwd(phoneNumber);
                } else {
                    ToastUtils.showToast(this, "验证码输入有误，请重新输入或获取验证码,如无法获取请致电客服找回！");
                    ensure_number.setText("");
                }
                break;
        }
    }

    public void getEnsureNumber(String number) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_GETAUTHCODE, appToken);
        params.addBodyParameter("mobile", number);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.d(result);
                ensureNum = ParseJson(result);
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

    public void findPwd(String phoneNumber) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_FOGOTPWD, appToken);
        params.addBodyParameter("mobile", phoneNumber);
        params.addBodyParameter("usertype", "3");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ToastUtils.showToast(PwdFindActivity.this, "您的密码已于短信的形式下发到您的手机上，请注意查收");
                finish();
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

    public String ParseJson(String json) {
        try {
            JSONObject o = new JSONObject(json).getJSONObject("data");
            return o.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
