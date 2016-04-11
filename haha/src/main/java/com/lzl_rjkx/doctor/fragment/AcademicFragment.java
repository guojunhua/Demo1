package com.lzl_rjkx.doctor.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lzl_rjkx.doctor.activity.ColumnDesActivity;
import com.lzl_rjkx.doctor.adapter.CommonAdapter;
import com.lzl_rjkx.doctor.adapter.ViewHolder;
import com.lzl_rjkx.doctor.bean.Academic;
import com.lzl_rjkx.doctor.bean.Academic.DataEntity.ListEntity;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.bean.SerializableMap;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.customview.RefreshLayout;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.MyRequestUtils;
import com.rjkx.lzl_os.myapp.R;

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
public class AcademicFragment extends Fragment {
    @ViewInject(R.id.swipe_container)
    private RefreshLayout mRefreshLayout;
    @ViewInject(R.id.common_lv)
    private ListView lv;
    private int currentPage = 1;

    private List<ListEntity> adList = new ArrayList<>();
    private CommonAdapter<ListEntity> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refresh_listview, null);
        x.view().inject(this, v);
        EventBus.getDefault().register(this);

        MyAppTokenRequest.requestData();
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
                map.put("type", 2);
                map.put("academic", adList.get(position));
                sMap.setMap(map);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", sMap);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return v;
    }

    @Subscribe
    public void onEvent(FirstEvent event) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_QUERYNEWSLIST, event.getmMsg());
        params.addBodyParameter("userid", Interface.getUserId(getActivity()));
        params.addBodyParameter("usertype", "3");
        params.addBodyParameter("msgtype", "4");
        params.addBodyParameter("page", currentPage + "");
        MyRequestUtils.requestAsObject(params, new Academic());
    }

    @Subscribe
    public void onEvent(Academic academic) {
        adList = academic.getData().getList();
        adapter.getDatas(adList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
