package com.rjkx.lzl_os.myapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rjkx.lzl_os.myapp.adapter.MyViewPagerAdapter;
import com.rjkx.lzl_os.myapp.fragments.ConsuleFragment;
import com.rjkx.lzl_os.myapp.fragments.HeadFragment;
import com.rjkx.lzl_os.myapp.fragments.LoveFragment;
import com.rjkx.lzl_os.myapp.fragments.MineFragment;
import com.rjkx.lzl_os.myapp.fragments.OrderFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)//给activity设置setContentView的
public class MainActivity extends AppCompatActivity implements OnPageChangeListener {

    @ViewInject(R.id.myVp)
    private ViewPager vp;
    // TextView注解
    @ViewInject(R.id.tv_head)
    private TextView tv_head;
    @ViewInject(R.id.tv_order)
    private TextView tv_order;
    @ViewInject(R.id.tv_love)

    private TextView tv_love;
    @ViewInject(R.id.tv_consul)
    private TextView tv_consul;
    @ViewInject(R.id.tv_mine)
    private TextView tv_mine;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    // ImageView注解
    @ViewInject(R.id.iv_head)
    private ImageView iv_head;
    @ViewInject(R.id.iv_order)
    private ImageView iv_order;
    @ViewInject(R.id.iv_love)
    private ImageView iv_love;
    @ViewInject(R.id.iv_consul)
    private ImageView iv_consul;
    @ViewInject(R.id.iv_mine)
    private ImageView iv_mine;
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.iv_chatting)
    private ImageView iv_chatting;
    // 退出时间
    private long exitTime = 0;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
        iv_back.setVisibility(View.INVISIBLE);
        tv_title.setText("医愿@骨科");
        vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), list));
        vp.addOnPageChangeListener(this);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new HeadFragment());
        list.add(new OrderFragment());
        list.add(new LoveFragment());
        list.add(new ConsuleFragment());
        list.add(new MineFragment());
    }

    // 改变图片和文字的方法
    public void change(int id) {
        int a1 = R.mipmap.head;
        int a2 = R.mipmap.order;
        int a3 = R.mipmap.love;
        int a4 = R.mipmap.consul;
        int a5 = R.mipmap.mine;

        int color1 = getResources().getColor(R.color.color_bottom1);
        int color2 = getResources().getColor(R.color.color_bottom2);
        int c1 = color2, c2 = color2, c3 = color2, c4 = color2, c5 = color2;
        int iv=0;
        if (id == 0) {
            c1 = color1;
            a1 = R.mipmap.head_show;
            iv=R.mipmap.right_one;
            tv_title.setText("医愿@骨科");
        } else if (id == 1) {
            c2 = color1;
            a2 = R.mipmap.order_show;
            iv=R.mipmap.right_one;
            tv_title.setText("快速预约");
        } else if (id == 2) {
            c3 = color1;
            a3 = R.mipmap.love_show;
            iv=R.mipmap.right_one;
            tv_title.setText("医愿关爱");
        } else if (id == 3) {
            c4 = color1;
            a4 = R.mipmap.consul_show;
            iv=R.mipmap.right_one;
            tv_title.setText("预约会诊");
        } else if (id == 4) {
            c5 = color1;
            a5 = R.mipmap.mine_show;
            iv=R.mipmap.d_pic3;
            tv_title.setText("个人中心");
        }
        tv_head.setTextColor(c1);
        tv_order.setTextColor(c2);
        tv_love.setTextColor(c3);
        tv_consul.setTextColor(c4);
        tv_mine.setTextColor(c5);

        iv_head.setImageResource(a1);
        iv_order.setImageResource(a2);
        iv_love.setImageResource(a3);
        iv_consul.setImageResource(a4);
        iv_mine.setImageResource(a5);
        iv_chatting.setImageResource(iv);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                vp.setCurrentItem(0);
                change(0);
                break;
            case R.id.ll_order:
                vp.setCurrentItem(1);
                change(1);
                break;
            case R.id.ll_love:
                vp.setCurrentItem(2);
                change(2);
                break;
            case R.id.ll_consul:
                vp.setCurrentItem(3);
                change(3);
                break;
            case R.id.ll_mine:
                iv_chatting.setImageResource(R.mipmap.d_pic3);
                vp.setCurrentItem(4);
                change(4);
                break;
        }
    }

    // 退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出医愿",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        change(position);
    }
}
