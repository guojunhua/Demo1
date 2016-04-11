package com.lzl_rjkx.doctor.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzl_rjkx.doctor.adapter.CommonAdapter;
import com.lzl_rjkx.doctor.adapter.ViewHolder;
import com.lzl_rjkx.doctor.bean.Topic;
import com.lzl_rjkx.doctor.bean.Topic.DataEntity.ListEntity;
import com.lzl_rjkx.doctor.customview.RefreshLayout;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.rjkx.lzl_os.myapp.R;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class NewsFragment extends Fragment {
    private RefreshLayout mRefreshLayout;
    private ListView lv;
    private View footerLayout;
    private TextView textMore;
    private ProgressBar progressBar;

    private int pts;
    private static final String ARG_POSITION = "Position";

    private List<ListEntity> aList = new ArrayList<>();
    private CommonAdapter<ListEntity> adapter;

    public static NewsFragment newInstance(int position) {
        NewsFragment f = new NewsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        MyAppTokenRequest.requestData();
        pts = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refresh_listview, null);
        mRefreshLayout = (RefreshLayout) v.findViewById(R.id.swipe_container);
        lv = (ListView) v.findViewById(R.id.common_lv);

        footerLayout = inflater.inflate(R.layout.listview_footer, null);
        textMore = (TextView) footerLayout.findViewById(R.id.text_more);
        progressBar = (ProgressBar) footerLayout.findViewById(R.id.load_progress_bar);

        textMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //simulateLoadingData();
            }
        });

        //这里可以替换为自定义的footer布局
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
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), ColumnDesActivity.class);
//                SerializableMap sMap = new SerializableMap();
//                sMap.setMap(data.get(position));
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", sMap);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        //使用SwipeRefreshLayout的下拉刷新监听
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //simulateFetchingData();
            }
        });

        //使用自定义的RefreshLayout加载更多监听
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                //simulateFetchingData();
            }
        });
        return v;
    }

    /**
     * 模拟下拉刷新时获取新数据
     */
//    private void getNewTopData() {
//        int size = data.size();
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", size + "");
//        map.put("img", R.mipmap.icon);
//        map.put("title", "红楼梦" + size);
//        map.put("author", "曹雪芹");
//        map.put("pubTime", "18世纪中叶");
//        data.add(0, map);
//    }

    /**
     * 模拟上拉加载更多时获得更多数据
     */
//    private void getNewBottomData() {
//        int size = data.size();
//        for (int i = 0; i < 3; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("img", R.mipmap.icon);
//            map.put("title", "红楼梦" + (size + i));
//            map.put("author", "曹雪芹" + (size + i));
//            map.put("pubTime", "18世纪中叶");
//            data.add(map);
//        }
//    }

    /**
     * 模拟一个耗时操作，获取完数据后刷新ListView
     */
//    private void simulateFetchingData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getNewTopData();
//                mRefreshLayout.setRefreshing(false);
//                textMore.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
//                adapter.notifyDataSetChanged();
//                ToastUtils.showToast(getActivity(), "刷新完成！");
//            }
//        }, 2000);
//    }

    /**
     * 模拟一个耗时操作，加载完更多底部数据后刷新ListView
     */
//    private void simulateLoadingData() {
//        textMore.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getNewBottomData();
//                mRefreshLayout.setLoading(false);
//                adapter.notifyDataSetChanged();
//                textMore.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
//                ToastUtils.showToast(getActivity(), "加载完成...");
//            }
//        }, 200);
//    }
//    @Subscribe
//    public void onEvent(FirstEvent event) {
//        OkHttpUtils.post().url(Interface.getUrl() + "queryNewsList.do")
//                .addParams("userid", Interface.getUserId(getActivity()) + "")
//                .addParams("usertype", 3 + "")
//                .addParams("msgtype", 1 + "")
//                .addParams("appToken", event.getmMsg())
//                .addParams("imeiStr", "000")
//                .addParams("page", 1 + "")
//                .build().execute(new MyStringCallback(new Topic()));
//    }

    @Subscribe
    public void onEvent(Topic topic) {
        aList = topic.getData().getList();
        adapter.getDatas(aList);
        adapter.notifyDataSetChanged();
    }
}