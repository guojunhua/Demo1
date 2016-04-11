package com.rjkx.lzl_os.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.rjkx.lzl_os.myapp.customview.AnimatedExpandableListView;
import com.rjkx.lzl_os.myapp.customview.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_sickness)
public class SicknessActivity extends AppCompatActivity {

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.my_listView)
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sickness);
        x.view().inject(this);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
        });
        List<GroupItem> items = new ArrayList<>();

        // Populate our list with groups and it's children

        String[] str_group_items = {"骨折", "脱位", "四肢血管损伤", "跟腱损伤", "周围神经损伤"};
        String[] str_child_items = {"臂丛神经损伤", "正中神经损伤", "尺神经损伤", "挠神经损伤",
                "坐骨神经损伤"};

        for (int i = 1; i < str_group_items.length; i++) {
            GroupItem item = new GroupItem();
            item.title = str_group_items[i];
            for (int j = 0; j < str_child_items.length; j++) {
                ChildItem child = new ChildItem();
                child.title = str_child_items[j];
                item.items.add(child);
            }
            items.add(item);
        }
        adapter = new ExampleAdapter(this);
        adapter.setData(items);

        listView.setGroupIndicator(null);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(adapter);

        listView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });

        listView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getApplicationContext(),
                        SickDetailsActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<>();
    }

    private static class ChildItem {
        String title;
    }

    private static class ChildHolder {
        TextView title;
    }

    private static class GroupHolder {
        TextView title;
        ImageView iv;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListAdapter {

        private LayoutInflater inflater;
        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.child_item, parent,
                        false);
                holder.title = (TextView) convertView
                        .findViewById(R.id.child_txt);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            holder.title.setText(item.title);
            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent,
                        false);
                holder.title = (TextView) convertView
                        .findViewById(R.id.group_txt);
                holder.iv = (ImageView) convertView.findViewById(R.id.grop_img);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }
            holder.title.setText(item.title);
            if (isExpanded) {
                holder.title.setTextColor(getResources().getColor(
                        R.color.color_keShi));
                holder.iv.setImageResource(R.mipmap.add);
            } else {
                holder.title.setTextColor(getResources().getColor(
                        R.color.color_black));
                holder.iv.setImageResource(R.mipmap.jian);
            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }
    }
}
