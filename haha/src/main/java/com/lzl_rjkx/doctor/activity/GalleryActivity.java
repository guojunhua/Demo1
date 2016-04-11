package com.lzl_rjkx.doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.utils.Bimp;
import com.lzl_rjkx.doctor.utils.PublicWay;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 这个是用于进行图片浏览时的界面
 */
@ContentView(R.layout.plugin_camera_gallery)
public class GalleryActivity extends BaseActivity {
    private Intent intent;
    // 返回按钮
    @ViewInject(R.id.gallery_back)
    private Button back_bt;
    // 发送按钮
    @ViewInject(R.id.send_button)
    private Button send_bt;
    //删除按钮
    @ViewInject(R.id.gallery_del)
    private Button del_bt;

    //获取前一个activity传过来的position
    private int position;
    //当前的位置
    private int location = 0;

    private ArrayList<View> listViews = null;
    @ViewInject(R.id.gallery01)
    private ViewPager pager;
    private MyPageAdapter adapter;

    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicWay.activityList.add(this);
        //getSwipeBackLayout().setEnableGesture(false);
        mContext = this;
        back_bt.setOnClickListener(new BackListener());
        send_bt.setOnClickListener(new GallerySendListener());
        del_bt.setOnClickListener(new DelListener());
        intent = getIntent();
        position = Integer.parseInt(intent.getStringExtra("position"));
        isShowOkBt();
        // 为发送按钮设置文字
        pager.addOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            initListViews(Bimp.tempSelectBitmap.get(i).getBitmap());
        }

        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        pager.setPageMargin(10);
        int id = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(id);
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<>();
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageBitmap(bm);
        listViews.add(iv);
    }

    // 返回按钮添加的监听器
    private class BackListener implements OnClickListener {
        public void onClick(View v) {
            intent.setClass(GalleryActivity.this, ImageFile.class);
            startActivity(intent);
        }
    }

    // 删除按钮添加的监听器
    private class DelListener implements OnClickListener {
        public void onClick(View v) {
            if (listViews.size() == 1) {
                Bimp.tempSelectBitmap.clear();
                Bimp.max = 0;
                send_bt.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                Intent intent = new Intent("data.broadcast.action");
                sendBroadcast(intent);
                finish();
            } else {
                Bimp.tempSelectBitmap.remove(location);
                Bimp.max--;
                pager.removeAllViews();
                listViews.remove(location);
                adapter.setListViews(listViews);
                send_bt.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                adapter.notifyDataSetChanged();
            }
        }
    }

    // 完成按钮的监听
    private class GallerySendListener implements OnClickListener {
        public void onClick(View v) {
            finish();
            intent.setClass(mContext, PublishActivity.class);
            intent.setAction("data.broadcast.action");
            sendBroadcast(intent);
            startActivity(intent);
        }
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            send_bt.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            send_bt.setPressed(true);
            send_bt.setClickable(true);
            send_bt.setTextColor(Color.WHITE);
        } else {
            send_bt.setPressed(false);
            send_bt.setClickable(false);
            send_bt.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    /**
     * 监听返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (position == 1) {
                this.finish();
                intent.setClass(GalleryActivity.this, AlbumActivity.class);
                startActivity(intent);
            } else if (position == 2) {
                this.finish();
                intent.setClass(GalleryActivity.this, ShowAllPhoto.class);
                startActivity(intent);
            }
        }
        return true;
    }


    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;

        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
