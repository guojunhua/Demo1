package com.lzl_rjkx.doctor.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.fragment.CircleFragment;
import com.lzl_rjkx.doctor.fragment.ClinicFragment;
import com.lzl_rjkx.doctor.fragment.HeadFragment;
import com.lzl_rjkx.doctor.fragment.MineFragment;
import com.lzl_rjkx.doctor.fragment.OrderFragment;
import com.lzl_rjkx.doctor.utils.Bimp;
import com.lzl_rjkx.doctor.utils.FileUtils;
import com.lzl_rjkx.doctor.utils.ImageItem;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by lzl_os on 16/1/26.
 */
@ContentView(R.layout.activity_main)
public class DoctorMainActivity extends BaseActivity {

    /**
     * TextView 注解
     */
    @ViewInject(R.id.top_title)
    private TextView tv_title;
    //@ViewInject(R.id.tv_clinic)
    private TextView tv_clinic;
    @ViewInject(R.id.tv_head)
    private TextView tv_head;
    @ViewInject(R.id.tv_order)
    private TextView tv_order;
    //@ViewInject(R.id.tv_circle)
    private TextView tv_circle;
    @ViewInject(R.id.tv_mine)
    private TextView tv_mine;
    /**
     * ImageView 注解
     */
    @ViewInject(R.id.iv_head)
    private ImageView iv_head;
  //  @ViewInject(R.id.iv_clinic)
    private ImageView iv_clinic;
    //@ViewInject(R.id.iv_circle)
    private ImageView iv_circle;
    @ViewInject(R.id.iv_order)
    private ImageView iv_order;
    @ViewInject(R.id.iv_mine)
    private ImageView iv_mine;
    // 退出时间
    private long exitTime = 0;
    private String appToken;

    private Fragment headFragment, clinicFragment, circleFragment, orderFragment, mineFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        fm = getSupportFragmentManager();
        showFragment(0);
        //getSwipeBackLayout().setEnableGesture(false);
        MyAppTokenRequest.requestData();
    }

    //显示Fragment的方法
    private void showFragment(int index) {
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        switch (index) {
            case 0:
                if (headFragment == null) {
                    headFragment = new HeadFragment();
                    ft.add(R.id.fl_layout, headFragment);
                } else {
                    ft.show(headFragment);
                }
                break;
            case 1:
                if (clinicFragment == null) {
                    clinicFragment = new ClinicFragment();
                    ft.add(R.id.fl_layout, clinicFragment);
                } else {
                    ft.show(clinicFragment);
                }
                break;
            case 2:
                if (circleFragment == null) {
                    circleFragment = new CircleFragment();
                    ft.add(R.id.fl_layout, circleFragment);
                } else {
                    ft.show(circleFragment);
                }
                break;
            case 3:
                if (orderFragment == null) {
                    orderFragment = new OrderFragment();
                    ft.add(R.id.fl_layout, orderFragment);
                } else {
                    ft.show(orderFragment);
                }
                break;
            case 4:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    ft.add(R.id.fl_layout, mineFragment);
                } else {
                    ft.show(mineFragment);
                }
                break;
        }
        ft.commit();
    }

    //当Fragment已被实例化，就隐藏起来
    private void hideFragment(FragmentTransaction ft) {
        if (headFragment != null)
            ft.hide(headFragment);
        if (clinicFragment != null)
            ft.hide(clinicFragment);
        if (orderFragment != null)
            ft.hide(orderFragment);
        if (circleFragment != null)
            ft.hide(circleFragment);
        if (mineFragment != null)
            ft.hide(mineFragment);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                change(0);
                showFragment(0);
                break;
//            case R.id.ll_clinic:
//                change(1);
//                showFragment(1);
//                break;
//            case R.id.ll_circle:
//                change(2);
//                showFragment(2);
//                break;
            case R.id.ll_order:
                change(3);
                showFragment(3);
                break;
            case R.id.ll_mine:
                change(4);
                showFragment(4);
                break;
//            case R.id.publish_mood:
//                showPopuwindow();
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 0.7f;
//                getWindow().setAttributes(lp);
//                break;
        }
    }

    public void showPopuwindow() {
        final PopupWindow pop = new PopupWindow(this);
        View v = getLayoutInflater().inflate(R.layout.top_popuwindow, null);
        LinearLayout ll_album = (LinearLayout) v.findViewById(R.id.ll_album);
        LinearLayout ll_video = (LinearLayout) v.findViewById(R.id.ll_video);
        LinearLayout ll_photo = (LinearLayout) v.findViewById(R.id.ll_photo);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(v);
        pop.showAsDropDown(tv_title);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });

        ll_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorMainActivity.this, AlbumActivity.class));
                pop.dismiss();
            }
        });

        ll_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(DoctorMainActivity.this, MovieRecorderActivity.class);
                startActivity(videoIntent);
                pop.dismiss();
            }
        });

        ll_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCameraIntent, 1);
                pop.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            FileUtils.saveBitmap(bm, String.valueOf(System.currentTimeMillis()));
            ImageItem takePhoto = new ImageItem();
            takePhoto.setBitmap(bm);
            Bimp.tempSelectBitmap.add(takePhoto);
            startActivity(new Intent(this, PublishActivity.class));
        }
    }

    public void change(int index) {
        int c1 = getResources().getColor(R.color.color_bottom1);
        int c2 = getResources().getColor(R.color.color_bottom2);
        int color1 = c2, color2 = c2, color3 = c2, color4 = c2, color5 = c2;
        int icon1 = R.mipmap.head, icon2 = R.mipmap.clinic, icon3 = R.mipmap.circle, icon4 = R.mipmap.order, icon5 = R.mipmap.mine;
        String top_title = null;
        if (index == 0) {
            color1 = c1;
            icon1 = R.mipmap.head_show;
            //top_title = getResources().getString(R.string.head);
        } else if (index == 1) {
            color2 = c1;
            icon2 = R.mipmap.clinic;
            //top_title = getResources().getString(R.string.clinic);
        } else if (index == 2) {
            color3 = c1;
            icon3 = R.mipmap.circle_show;
           // top_title = getResources().getString(R.string.circle);
        } else if (index == 3) {
            color4 = c1;
            icon4 = R.mipmap.order_show;
            //top_title = getResources().getString(R.string.order);
        } else if (index == 4) {
            color5 = c1;
            icon5 = R.mipmap.mine_show;
            //top_title = getResources().getString(R.string.mine);
        }
        tv_head.setTextColor(color1);
        tv_clinic.setTextColor(color2);
        tv_circle.setTextColor(color3);
        tv_order.setTextColor(color4);
        tv_mine.setTextColor(color5);
        iv_head.setImageResource(icon1);
        iv_clinic.setImageResource(icon2);
        iv_circle.setImageResource(icon3);
        iv_order.setImageResource(icon4);
        iv_mine.setImageResource(icon5);
        tv_title.setText(top_title);
    }

    @Subscribe
    public void onEvent(FirstEvent event) {
        appToken = event.getmMsg();
    }

    // 退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showToast(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                RequestParams params = MyParams.getParams(HttpConstants.HTTP_LOGINOUT, appToken);
                params.addBodyParameter("clientid", "1111");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.d("loginOut:" + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LogUtil.d("退出异常：" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
