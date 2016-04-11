package com.rjkx.lzl_os.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rjkx.lzl_os.myapp.adapter.CommonAdapter;
import com.rjkx.lzl_os.myapp.adapter.ViewHolder;
import com.rjkx.lzl_os.myapp.bean.Data1;
import com.rjkx.lzl_os.myapp.utils.MyCommonCallStringRequest;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

@ContentView(R.layout.activity_video)
public class VideoActivity extends AppCompatActivity implements
		OnItemClickListener {

	private String path = "http://www.appzg.org/APPVideoPalyInterface?type=1&AppUserID=0b465072fdba2334249759af9e6b9d6d&Page=1";
	@ViewInject(R.id.iv_chatting)
	private ImageView iv_chat;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	@ViewInject(R.id.video_list)
	private ListView lv;
	private List<Data1.DataEntity> list;
	private CommonAdapter<Data1.DataEntity> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		iv_chat.setVisibility(View.INVISIBLE);
		tv_title.setText("健康课堂");
		list=new ArrayList<>();
		x.http().get(new RequestParams(path),new MyCommonCallStringRequest(new Data1()));

		EventBus.getDefault().register(this);

		adapter = new CommonAdapter<Data1.DataEntity>(this,R.layout.video_item) {
			@Override
			public void convert(ViewHolder helper, Data1.DataEntity item) {
				helper.setImageByUrl(R.id.video_icon, item.getVideo_picture());
				helper.setText(R.id.video_title, item.getVideo_name());
				helper.setText(R.id.video_summary, item.getVideo_desc());
			}
		};
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	@Subscribe
	public void getData(Data1 data1){
		list.addAll(data1.getData());
		adapter.getDatas(list);
		adapter.notifyDataSetChanged();
	}

	@Event(R.id.iv_back)
	private void click(View v) {
		finish();
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(VideoActivity.this, VideoPlayActivity.class);
		intent.putExtra("url",list.get(position).getVideo_androidUrl());
		intent.putExtra("title", list.get(position).getVideo_name());
		startActivity(intent);
		this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
