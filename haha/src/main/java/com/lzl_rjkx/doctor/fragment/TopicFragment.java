package com.lzl_rjkx.doctor.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzl_rjkx.doctor.activity.ColumnDesActivity;
import com.lzl_rjkx.doctor.adapter.CommonAdapter;
import com.lzl_rjkx.doctor.adapter.ViewHolder;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.bean.SerializableMap;
import com.lzl_rjkx.doctor.bean.Topic;
import com.lzl_rjkx.doctor.bean.Topic.DataEntity.ListEntity;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.customview.RefreshLayout;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.MyRequestUtils;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by lzl_os on 16/3/14.
 */
public class TopicFragment extends Fragment {
    @ViewInject(R.id.swipe_container)
    private RefreshLayout mRefreshLayout;
    @ViewInject(R.id.common_lv)
    private ListView lv;
    private View footerLayout;
    private TextView textMore;
    private ProgressBar progressBar;

    private List<ListEntity> tList = new ArrayList<>();
    private CommonAdapter<ListEntity> adapter;
    private int currentPage = 1;
    private String appToken;
    private Boolean isRefresh = false, isMore = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refresh_listview, null);
        x.view().inject(this, v);
        EventBus.getDefault().register(this);
        MyAppTokenRequest.requestData();

        footerLayout = inflater.inflate(R.layout.listview_footer, null);
        textMore = (TextView) footerLayout.findViewById(R.id.text_more);
        progressBar = (ProgressBar) footerLayout.findViewById(R.id.load_progress_bar);

        lv.addFooterView(footerLayout);
        mRefreshLayout.setChildView(lv);
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);

        adapter = new CommonAdapter<ListEntity>(getActivity(), R.layout.article_item) {
            @Override
            public void convert(ViewHolder helper, ListEntity item) {
                helper.setImageByUrl(R.id.article_icon, item.getMsgImg());
                helper.setText(R.id.article_title, item.getMsgTitle());
                helper.setText(R.id.article_author, item.getOpName());
                helper.setText(R.id.article_pubTime, item.getSTime());
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ColumnDesActivity.class);
                SerializableMap sMap = new SerializableMap();
                Map<String, Object> map = new HashMap<>();
                map.put("type", 0);
                map.put("topic", tList.get(position));
                sMap.setMap(map);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", sMap);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("确认").setMessage("确定要删除吗？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delNews(tList.get(position).getMsgId(), position);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
                return false;
            }
        });

        textMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMore = true;
                currentPage++;
                initData(currentPage);
                textMore.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                currentPage++;
                initData(currentPage);
            }
        });
        return v;
    }

    public void delNews(final int msgId, final int position) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_DELNEWS, appToken);
        params.addBodyParameter("userid", Interface.getUserId(getActivity()));
        params.addBodyParameter("usertype", "3");
        params.addParameter("msgId", msgId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject o = new JSONObject(result).getJSONObject("data");
                    String code = o.getString("msg");
                    if (code.equals("OK")) {
                        tList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.i(ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void initData(int currentPage) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_QUERYNEWSLIST, appToken);
        params.addBodyParameter("usertype", "3");
        params.addBodyParameter("msgtype", "1");
        params.addBodyParameter("page", currentPage + "");
        MyRequestUtils.requestAsObject(params, new Topic());
    }

    @Subscribe
    public void onEvent(FirstEvent event) {
        this.appToken = event.getmMsg();
        initData(currentPage);
    }

    @Subscribe
    public void onEvent(Topic topic) {
        if (isRefresh) {
            mRefreshLayout.setRefreshing(false);
            textMore.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            ToastUtils.showToast(getActivity(), "刷新完成！");
        }

        if (isMore) {
            mRefreshLayout.setLoading(false);
            textMore.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            ToastUtils.showToast(getActivity(), "加载完成...");
        }

        tList.addAll(topic.getData().getList());
        adapter.getDatas(tList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
