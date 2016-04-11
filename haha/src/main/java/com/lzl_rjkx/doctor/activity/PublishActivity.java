package com.lzl_rjkx.doctor.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzl_rjkx.doctor.base.BaseActivity;
import com.lzl_rjkx.doctor.bean.FirstEvent;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.utils.Bimp;
import com.lzl_rjkx.doctor.utils.FileUtils;
import com.lzl_rjkx.doctor.utils.ImageItem;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.PublicWay;
import com.lzl_rjkx.doctor.utils.Res;
import com.lzl_rjkx.doctor.utils.ToastUtils;
import com.rjkx.lzl_os.myapp.R;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 发布页面activity
 */
public class PublishActivity extends BaseActivity implements OnClickListener {

    private GridView noScrollgridview;
    private GridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private RelativeLayout rl_popup;
    public static Bitmap bimap;

    private TextView tv_send;
    private TextView tv_cancel;
    private EditText et_mood;

    private final String fileName = String.valueOf(System.currentTimeMillis());
    private final List<String> fileList = new ArrayList<>();
    private static final int TAKE_PICTURE = 0x000001;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSwipeBackLayout().setEnableGesture(false);
        Res.init(this);
        EventBus.getDefault().register(this);
        bimap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_addpic_focused);
        PublicWay.activityList.add(this);
        parentView = getLayoutInflater().inflate(R.layout.activity_publish, null);
        setContentView(parentView);
        Init();

        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(receiver, filter);

        noScrollgridview = (GridView) parentView.findViewById(R.id.gridView_mood);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.tempSelectBitmap.size()) {
                    rl_popup.startAnimation(AnimationUtils.loadAnimation(PublishActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(PublishActivity.this, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    };

    public void Init() {
        tv_send = (TextView) parentView.findViewById(R.id.tv_send);
        tv_cancel = (TextView) parentView.findViewById(R.id.tv_cancel);
        et_mood = (EditText) parentView.findViewById(R.id.et_mood);
        tv_send.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        pop = new PopupWindow(this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        rl_popup = (RelativeLayout) view.findViewById(R.id.rl_popup);

        pop.setWidth(LayoutParams.MATCH_PARENT);
        pop.setHeight(LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        TextView tv_photo = (TextView) view
                .findViewById(R.id.tv_photo);
        TextView tv_phone = (TextView) view
                .findViewById(R.id.tv_phone);
        TextView tv_cancel = (TextView) view
                .findViewById(R.id.tv_cancel);

        parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                rl_popup.clearAnimation();
            }
        });
        tv_photo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                pop.dismiss();
                rl_popup.clearAnimation();
            }
        });
        tv_phone.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this, AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                rl_popup.clearAnimation();
            }
        });
        tv_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                rl_popup.clearAnimation();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                for (int i = 0; i < PublicWay.activityList.size(); i++) {
                    if (null != PublicWay.activityList.get(i)) {
                        PublicWay.activityList.get(i).finish();
                    }
                }
                unregisterReceiver(receiver);
                break;
            case R.id.tv_send:
                String mood = et_mood.getText().toString().trim();
                if (mood.equals("") && Bimp.tempSelectBitmap.size() == 0) {
                    ToastUtils.showToast(this, "请发张图片或者写点什么吧");
                } else {
                    MyAppTokenRequest.requestData();
                }
                break;
        }
    }

    @Subscribe
    public void onEvent(FirstEvent event) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_UPLOADFILE, event.getmMsg());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ArrayList<ImageItem> item = Bimp.tempSelectBitmap;
        ArrayList<byte[]> imgs = new ArrayList<>();
        for (ImageItem imageItem : item) {
            imageItem.getBitmap().compress(Bitmap.CompressFormat.WEBP, 100, baos);
            imgs.add(baos.toByteArray());
        }
        params.addParameter("fileup", imgs);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.d(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.d(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        @Override
        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9)
                return 9;
            return Bimp.tempSelectBitmap.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.mood_grid_item, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.moodIcon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageResource(R.mipmap.icon_addpic_focused);
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                    fileList.add(fileName);
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < PublicWay.activityList.size(); i++) {
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
        }
        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
        return true;
    }
}