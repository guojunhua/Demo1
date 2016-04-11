package com.lzl_rjkx.doctor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lzl_rjkx.doctor.adapter.AlbumGridViewAdapter;
import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.utils.AlbumHelper;
import com.lzl_rjkx.doctor.utils.Bimp;
import com.lzl_rjkx.doctor.utils.ImageBucket;
import com.lzl_rjkx.doctor.utils.ImageItem;
import com.lzl_rjkx.doctor.utils.PublicWay;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是进入相册显示所有图片的界面
 */

@ContentView(R.layout.plugin_camera_album)
public class AlbumActivity extends BaseActivity implements OnClickListener {
    //显示手机里的所有图片的列表控件
    @ViewInject(R.id.myGrid)
    private GridView gridView;
    //当手机里没有图片时，提示用户没有图片的控件
    @ViewInject(R.id.myText)
    private TextView tv;
    //gridView的adapter
    private AlbumGridViewAdapter gridImageAdapter;
    //完成按钮
    @ViewInject(R.id.ok_button)
    private Button okButton;
    // 返回按钮
    @ViewInject(R.id.back)
    private Button back;
    // 取消按钮
    @ViewInject(R.id.cancel)
    private Button cancel;
    private Intent intent;
    // 预览按钮
    @ViewInject(R.id.preview)
    private Button preview;
    private Context mContext;
    private ArrayList<ImageItem> dataList;
    private AlbumHelper helper;
    public static List<ImageBucket> contentList;
    public static Bitmap bitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSwipeBackLayout().setEnableGesture(false);
        PublicWay.activityList.add(this);
        mContext = this;
        //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plugin_camera_no_pictures);
        init();
        initListener();
        //这个函数主要用来控制预览和完成按钮的状态
        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                Bimp.tempSelectBitmap.clear();
                Bimp.max = 0;
                intent.setClass(mContext, PublishActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                intent.setClass(AlbumActivity.this, ImageFile.class);
                startActivity(intent);
                break;
            case R.id.preview:
                if (Bimp.tempSelectBitmap.size() > 0) {
                    intent.putExtra("position", "1");
                    intent.setClass(AlbumActivity.this, GalleryActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ok_button:
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                intent.setClass(mContext, PublishActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    // 初始化，给一些对象赋值
    private void init() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<>();
        for (int i = 0; i < contentList.size(); i++) {
            dataList.addAll(contentList.get(i).imageList);
        }

        intent = getIntent();
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList, Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        gridView.setEmptyView(tv);
        okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
    }

    private void initListener() {
        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(final ToggleButton toggleButton,
                                    int position, boolean isChecked, Button chooseBt) {
                if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
                    toggleButton.setChecked(false);
                    chooseBt.setVisibility(View.GONE);
                    if (!removeOneData(dataList.get(position))) {
                       // ToastUtils.showToast(AlbumActivity.this, getResources().getString(R.string.only_choose_num));
                    }
                    return;
                }
                if (isChecked) {
                    chooseBt.setVisibility(View.VISIBLE);
                    Bimp.tempSelectBitmap.add(dataList.get(position));
                    okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                } else {
                    Bimp.tempSelectBitmap.remove(dataList.get(position));
                    chooseBt.setVisibility(View.GONE);
                    okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                }
                isShowOkBt();
            }
        });
        cancel.setOnClickListener(this);
        back.setOnClickListener(this);
        preview.setOnClickListener(this);
        okButton.setOnClickListener(this);
    }

    private boolean removeOneData(ImageItem imageItem) {
        if (Bimp.tempSelectBitmap.contains(imageItem)) {
            Bimp.tempSelectBitmap.remove(imageItem);
            okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            return true;
        }
        return false;
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
            intent.setClass(AlbumActivity.this, ImageFile.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
