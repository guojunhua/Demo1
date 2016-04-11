package com.lzl_rjkx.doctor.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.customview.MovieRecorderView;
import com.lzl_rjkx.doctor.customview.RoundProgressBar;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Administrator on 2016/3/29.
 */

@ContentView(R.layout.activity_movie_recorder)
public class MovieRecorderActivity extends BaseActivity {
    @ViewInject(R.id.movieRecorderView)
    private MovieRecorderView mRecoderView;
    @ViewInject(R.id.rl_shoot_video)
    private RelativeLayout shoot;
    @ViewInject(R.id.roundProgressBar)
    private RoundProgressBar progressBar;

    private boolean isFinish = true;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isFinish) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSwipeBackLayout().setEnableGesture(false);

        mRecoderView.setProgressBar(progressBar);
        shoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mRecoderView.record(new MovieRecorderView.OnRecordFinishListener() {
                        @Override
                        public void onRecordFinish() {
                            handler.sendEmptyMessage(1);
                        }
                    });
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mRecoderView.getTimeCount() > 1)
                        handler.sendEmptyMessage(1);
                    else {
                        if (mRecoderView.getmRecordFile() != null)
                            mRecoderView.getmRecordFile().delete();
                        mRecoderView.stop();
                        ToastUtils.showToast(MovieRecorderActivity.this, "视频录制时间太短");
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFinish = true;
    }
}