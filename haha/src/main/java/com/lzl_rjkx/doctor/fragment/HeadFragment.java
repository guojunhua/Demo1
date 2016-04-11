package com.lzl_rjkx.doctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lzl_rjkx.doctor.activity.ColumnDesActivity;
import com.lzl_rjkx.doctor.adapter.CommonAdapter;
import com.lzl_rjkx.doctor.adapter.MyHeadPagerAdapter;
import com.lzl_rjkx.doctor.adapter.ViewHolder;
import com.lzl_rjkx.doctor.bean.Advert;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.bean.HotTopic;
import com.lzl_rjkx.doctor.bean.HotTopic.DataEntity.ListEntity;
import com.lzl_rjkx.doctor.bean.Patient;
import com.lzl_rjkx.doctor.bean.Patient.DataEntity.OrderEntity;
import com.lzl_rjkx.doctor.bean.SerializableMap;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.customview.AutoScrollViewPager;
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
 * Created by lzl_os on 16/1/27.
 */
public class HeadFragment extends Fragment {
    @ViewInject(R.id.head_vp)
    private AutoScrollViewPager vp;
    private MyHeadPagerAdapter pagerAdapter;
    private List<ImageView> data = new ArrayList<>();

    @ViewInject(R.id.lv_topic)
    private ListView lv_topic;
    @ViewInject(R.id.lv_order)
    private ListView lv_order;

    private List<Advert.DataEntity.AdvEntity> bList = new ArrayList<>();
    private List<OrderEntity> tList = new ArrayList<>();
    private CommonAdapter<OrderEntity> adapter;

    private List<ListEntity> hotList = new ArrayList<>();
    private CommonAdapter<ListEntity> hotAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_head, null);
        x.view().inject(this, v);
        EventBus.getDefault().register(this);
        MyAppTokenRequest.requestData();

        pagerAdapter = new MyHeadPagerAdapter(data);
        vp.setAdapter(pagerAdapter);
        vp.startAutoScroll(3000);
        vp.setInterval(3000);
        vp.setSelected(true);//获取焦点
        vp.setFocusable(true);

        adapter = new CommonAdapter<OrderEntity>(getActivity(), R.layout.order_item) {
            @Override
            public void convert(ViewHolder helper, OrderEntity item) {
                helper.setText(R.id.patient_name, item.getPName());
                helper.setText(R.id.patient_vip, item.getVip() == 0 ? "精准预约" : "vip会诊");
                String state = null;
                int a = item.getOStatus();
                if (a == 3)
                    state = "待就诊";
                if (a == 4)
                    state = "已就诊";
                if (a == 5)
                    state = "已评价";
                helper.setText(R.id.patient_state, state);
                helper.setText(R.id.patient_time, "预约时间：" + item.getSTime());
                helper.setText(R.id.patient_disease, "疾病：" + item.getPSick());
                helper.setText(R.id.patient_describe, "病情描述：" + item.getPDesc());
            }
        };
        lv_order.setAdapter(adapter);

        hotAdapter = new CommonAdapter<ListEntity>(getActivity(), R.layout.article_item) {
            @Override
            public void convert(ViewHolder helper, ListEntity item) {
                helper.setImageByUrl(R.id.article_icon, item.getMsgImg());
                helper.setText(R.id.article_title, item.getMsgTitle());
                helper.setText(R.id.article_author, item.getOpName());
                helper.setText(R.id.article_pubTime, item.getSTime());
            }
        };
        lv_topic.setAdapter(hotAdapter);
        lv_topic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ColumnDesActivity.class);
                SerializableMap sMap = new SerializableMap();
                Map<String, Object> map = new HashMap<>();
                map.put("type", 3);
                map.put("hotTopic", hotList.get(position));
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
        RequestParams advParams = MyParams.getParams(HttpConstants.HTTP_QUERYNEWSADVERT, event.getmMsg());
        advParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        advParams.addBodyParameter("usertype", "3");
        MyRequestUtils.requestAsObject(advParams, new Advert());

        RequestParams patParams = MyParams.getParams(HttpConstants.HTTP_QUERYDOCTOR_TODAYORDER, event.getmMsg());
        patParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        MyRequestUtils.requestAsObject(patParams, new Patient());


        RequestParams hotParams = MyParams.getParams(HttpConstants.HTTP_QUERYHOTSNEWS, event.getmMsg());
        hotParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        hotParams.addBodyParameter("usertype", "3");
        hotParams.addBodyParameter("msgtype", "1");
        MyRequestUtils.requestAsObject(hotParams, new HotTopic());
    }

    @Subscribe
    public void onEvent(Advert advert) {
        bList = advert.getData().getAdv();
        for (int i = 0; i < bList.size(); i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //Picasso.with(getActivity()).load(bList.get(i).getNewsImg()).into(iv);
            data.add(iv);
            pagerAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onEvent(Patient patient) {
        tList = patient.getData().getOrder();
        adapter.getDatas(tList);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEvent(HotTopic topic) {
        hotList = topic.getData().getList();
        hotAdapter.getDatas(hotList);
        hotAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}