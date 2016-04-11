package com.lzl_rjkx.doctor.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.lzl_rjkx.doctor.adapter.FolderAdapter;
import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.utils.Bimp;
import com.lzl_rjkx.doctor.utils.PublicWay;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 */
@ContentView(R.layout.plugin_camera_image_file)
public class ImageFile extends BaseActivity {

    private FolderAdapter folderAdapter;
    private Context mContext;
    @ViewInject(R.id.cancel)
    private Button bt_cancel;
    @ViewInject(R.id.fileGridView)
    private GridView gridView;
    @ViewInject(R.id.headerTitle)
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSwipeBackLayout().setEnableGesture(false);
        PublicWay.activityList.add(this);
        mContext = this;
        //textView.setText(getResources().getString(R.string.photo));
        folderAdapter = new FolderAdapter(this);
        gridView.setAdapter(folderAdapter);
        bt_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空选择的图片
                Bimp.tempSelectBitmap.clear();
                Bimp.max = 0;
                Intent intent = new Intent(mContext, PublishActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(mContext, PublishActivity.class);
            startActivity(intent);
        }
        return true;
    }
}