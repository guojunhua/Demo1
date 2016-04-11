package com.rjkx.lzl_os.myapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rjkx.lzl_os.myapp.R;

import java.util.List;

public class MyGridAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public MyGridAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.layout = (FrameLayout) convertView
                    .findViewById(R.id.iv_icon);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_keshi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] colors = context.getResources().getStringArray(R.array.colors);
        GradientDrawable myGrad = (GradientDrawable) holder.layout
                .getBackground();
        myGrad.setColor(Color.parseColor(colors[position]));
        holder.tv.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        private FrameLayout layout;
        private TextView tv;
    }
}
