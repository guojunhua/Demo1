package com.rjkx.lzl_os.myapp;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_video_play)
public class VideoPlayActivity extends AppCompatActivity {
    @ViewInject(R.id.vv)
    private VideoView vv;
    @ViewInject(R.id.img_pb)
    private ProgressBar pb;
    @ViewInject(R.id.bar)
    private LinearLayout bar;
    @ViewInject(R.id.fl)
    private FrameLayout fl;
    @ViewInject(R.id.tVideo_title)
    private TextView tv_title;
    private long mLastTime, mCurTime;
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            if (!vv.isPlaying()) {
                pb.setVisibility(View.VISIBLE);
            } else {
                pb.setVisibility(View.GONE);
            }
            handler.postDelayed(runnable, 500);// 每0.5秒监听一次是否在播放视频
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
        x.view().inject(this);
        String url = getIntent().getStringExtra("url");
        //String url = "http://pl.youku.com/playlist/m3u8?vid=152911254&type=flv&ts=1379777176&keyframe=0";
        tv_title.setText(getIntent().getStringExtra("title"));
        fl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLastTime = mCurTime;
                mCurTime = System.currentTimeMillis();
                bar.setVisibility(View.VISIBLE);
                if (mCurTime - mLastTime < 3000) {
                    bar.setVisibility(View.INVISIBLE);
                    mCurTime = 0;
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bar.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                }
            }
        });
        PlayRtspStream(url, vv);
    }

    @Event(R.id.back)
    private void click(View v) {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    @SuppressLint("NewApi")
    private void PlayRtspStream(String rtspUrl, VideoView vv) {

        vv.setVideoURI(Uri.parse(rtspUrl));
        vv.requestFocus();
        vv.setFocusable(true);
        vv.setMediaController(new MediaController(vv.getContext()));
        vv.setOnInfoListener(new OnInfoListener() {
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    pb.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    // 此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
                    if (mp.isPlaying()) {
                        pb.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });
        vv.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                pb.setVisibility(View.GONE);// 缓冲完成就隐藏
            }
        });

        vv.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplication(), "抱歉无法播放该视屏",
                        Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                return true;
            }
        });
        vv.start();
    }
}