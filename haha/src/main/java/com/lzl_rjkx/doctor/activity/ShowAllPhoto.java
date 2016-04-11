package com.lzl_rjkx.doctor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lzl_rjkx.doctor.adapter.AlbumGridViewAdapter;
import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.utils.Bimp;
import com.lzl_rjkx.doctor.utils.ImageItem;
import com.lzl_rjkx.doctor.utils.PublicWay;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 */
@ContentView(R.layout.plugin_camera_show_all_photo)
public class ShowAllPhoto extends BaseActivity {
    @ViewInject(R.id.showallphoto_myGrid)
    private GridView gridView;
    @ViewInject(R.id.showallphoto_progressbar)
    private ProgressBar progressBar;
    private AlbumGridViewAdapter gridImageAdapter;
    // 完成按钮
    @ViewInject(R.id.showallphoto_ok_button)
    private Button okButton;
    // 预览按钮
    @ViewInject(R.id.showallphoto_preview)
    private Button preview;
    // 返回按钮
    @ViewInject(R.id.showallphoto_back)
    private Button back;
    // 取消按钮
    @ViewInject(R.id.showallphoto_cancel)
    private Button cancel;
    // 标题
    @ViewInject(R.id.showallphoto_headtitle)
    private TextView headTitle;
    private Intent intent;
    private Context mContext;
    public static ArrayList<ImageItem> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSwipeBackLayout().setEnableGesture(false);
        PublicWay.activityList.add(this);
        mContext = this;

        this.intent = getIntent();
        String folderName = intent.getStringExtra("folderName");
        if (folderName.length() > 8) {
            folderName = folderName.substring(0, 9) + "...";
        }
        headTitle.setText(folderName);
        cancel.setOnClickListener(new CancelListener());
        back.setOnClickListener(new BackListener(intent));
        preview.setOnClickListener(new PreviewListener());
        init();
        initListener();
        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                intent.putExtra("position", "2");
                intent.setClass(ShowAllPhoto.this, GalleryActivity.class);
                startActivity(intent);
            }
        }
    }

    private class BackListener implements OnClickListener {// 返回按钮监听
        Intent intent;

        public BackListener(Intent intent) {
            this.intent = intent;
        }

        public void onClick(View v) {
            intent.setClass(ShowAllPhoto.this, ImageFile.class);
            startActivity(intent);
        }
    }

    private class CancelListener implements OnClickListener {// 取消按钮的监听

        public void onClick(View v) {
            //清空选择的图片
            Bimp.tempSelectBitmap.clear();
            Bimp.max = 0;
            intent.setClass(mContext, PublishActivity.class);
            startActivity(intent);
        }
    }

    private void init() {
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        progressBar.setVisibility(View.GONE);
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList, Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
    }

    private void initListener() {
        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
            public void onItemClick(final ToggleButton toggleButton,
                                    int position, boolean isChecked,
                                    Button button) {
                if (Bimp.tempSelectBitmap.size() >= PublicWay.num && isChecked) {
                    button.setVisibility(View.GONE);
                    toggleButton.setChecked(false);
                    //ToastUtils.showToast(ShowAllPhoto.this, getResources().getString(R.string.only_choose_num));
                    return;
                }

                if (isChecked) {
                    button.setVisibility(View.VISIBLE);
                    Bimp.tempSelectBitmap.add(dataList.get(position));
                    okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                } else {
                    button.setVisibility(View.GONE);
                    Bimp.tempSelectBitmap.remove(dataList.get(position));
                    okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                }
                isShowOkBt();
            }
        });

        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                okButton.setClickable(false);
                intent.setClass(mContext, PublishActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
            preview.setTextColor(Color.WHITE);
        } else {
            okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            okButton.setTextColor(Color.parseColor("#E1E0DE"));
            preview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            intent.setClass(ShowAllPhoto.this, ImageFile.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }
}