package com.lzl_rjkx.doctor.activity;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.rjkx.lzl_os.myapp.R;

public class VideoPlayActivity extends BaseActivity {
    private VideoView vv;
    private ProgressBar pb;
    private LinearLayout bar;
    private FrameLayout fl;
    private TextView tv_title;
    private ImageView back;
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
        setContentView(R.layout.activity_video_play);
        //getSwipeBackLayout().setEnableGesture(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
        initView();
        String url = getIntent().getStringExtra("url");
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

    public void initView() {
        vv = (VideoView) findViewById(R.id.vv);
        pb = (ProgressBar) findViewById(R.id.img_pb);
        bar = (LinearLayout) findViewById(R.id.bar);
        fl = (FrameLayout) findViewById(R.id.fl);
        tv_title = (TextView) findViewById(R.id.tVideo_title);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
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