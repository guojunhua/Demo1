package com.lzl_rjkx.doctor.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.bean.Academic;
import com.lzl_rjkx.doctor.bean.Article;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.bean.HotTopic;
import com.lzl_rjkx.doctor.bean.SerializableMap;
import com.lzl_rjkx.doctor.bean.Topic;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.PreferenceUtils;
import com.lzl_rjkx.doctor.utils.StringUtils;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by lzl_os on 16/2/19.
 */
@ContentView(R.layout.activity_column)
public class ColumnDesActivity extends BaseActivity {
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_pubTime)
    private TextView tv_pubTime;
    @ViewInject(R.id.tv_content)
    private TextView tv_content;
    @ViewInject(R.id.favCount)
    private TextView tv_favCount;
    @ViewInject(R.id.zanCount)
    private TextView tv_zanCount;
    @ViewInject(R.id.readCount)
    private TextView tv_readCount;
    private Map<String, Object> map;
    private String appToken;
    private int msgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SerializableMap sMap = (SerializableMap) getIntent().getExtras().getSerializable("data");
        map = sMap.getMap();
        EventBus.getDefault().register(this);
        int type = (int) map.get("type");
        show(type);
        MyAppTokenRequest.requestData();
    }

    public void show(int type) {
        if (type == 0) {
            Topic.DataEntity.ListEntity entity = (Topic.DataEntity.ListEntity) map.get("topic");
            tv_title.setText(entity.getMsgTitle());
            tv_content.setText(StringUtils.getString(entity.getMsgContent()));
            tv_pubTime.setText(entity.getSTime());
            tv_favCount.setText(entity.getFavCount() + "");
            tv_zanCount.setText(entity.getZanCount() + "");
            tv_readCount.setText(entity.getReadCount() + "");
            msgId = entity.getMsgId();
        } else if (type == 1) {
            Article.DataEntity.ListEntity entity = (Article.DataEntity.ListEntity) map.get("article");
            tv_title.setText(entity.getMsgTitle());
            tv_content.setText(StringUtils.getString(entity.getMsgContent()));
            tv_pubTime.setText(entity.getSTime());
            tv_favCount.setText(entity.getFavCount() + "");
            tv_zanCount.setText(entity.getZanCount() + "");
            tv_readCount.setText(entity.getReadCount() + "");
            msgId = entity.getMsgId();
        } else if (type == 2) {
            Academic.DataEntity.ListEntity entity = (Academic.DataEntity.ListEntity) map.get("academic");
            tv_title.setText(entity.getMsgTitle());
            tv_content.setText(StringUtils.getString(entity.getMsgContent()));
            tv_pubTime.setText(entity.getSTime());
            tv_favCount.setText(entity.getFavCount() + "");
            tv_zanCount.setText(entity.getZanCount() + "");
            tv_readCount.setText(entity.getReadCount() + "");
            msgId = entity.getMsgId();
        } else if (type == 3) {
            HotTopic.DataEntity.ListEntity entity = (HotTopic.DataEntity.ListEntity) map.get("hotTopic");
            tv_title.setText(entity.getMsgTitle());
            tv_content.setText(StringUtils.getString(entity.getMsgContent()));
            tv_pubTime.setText(entity.getSTime());
            tv_favCount.setText(entity.getFavCount() + "");
            tv_zanCount.setText(entity.getZanCount() + "");
            tv_readCount.setText(entity.getReadCount() + "");
            msgId = entity.getMsgId();
        }
    }

    public void option(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                break;
            case R.id.ll_praise:
                if (PreferenceUtils.getPrefBoolean(this, "isZan", false) && PreferenceUtils.getPrefInt(this, "zanID", 0) == msgId) {
                    ToastUtils.showToast(this, "您已经赞过了...");
                } else {
                    zanOrCollect(HttpConstants.HTTP_ADDNEWSZAN, 0);
                }
                break;
            case R.id.ll_collect:
                if (PreferenceUtils.getPrefBoolean(this, "isCollect", false) && PreferenceUtils.getPrefInt(this, "collectId", 0) == msgId) {
                    ToastUtils.showToast(this, "您已经收藏过啦...");
                } else {
                    zanOrCollect(HttpConstants.HTTP_ADDNEWSFAV, 1);
                }
                break;
            case R.id.ll_comment:
                break;
        }
    }

    public void zanOrCollect(final String url, final int type) {
        RequestParams params = MyParams.getParams(url, appToken);
        params.addBodyParameter("userid", Interface.getUserId(this));
        params.addBodyParameter("usertype", "3");
        params.addParameter("msgId", msgId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject o = new JSONObject(result).getJSONObject("data");
                    String code = o.getString("msg");
                    if (code.equals("OK")) {
                        if (type == 0) {
                            PreferenceUtils.setPrefBoolean(ColumnDesActivity.this, "isZan", true);
                            PreferenceUtils.setPrefInt(ColumnDesActivity.this, "zanID", msgId);
                        } else if (type == 1) {
                            PreferenceUtils.setPrefBoolean(ColumnDesActivity.this, "isCollect", true);
                            PreferenceUtils.setPrefInt(ColumnDesActivity.this, "collectId", msgId);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    @Subscribe
    public void onEvent(FirstEvent event) {
        this.appToken = event.getmMsg();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}