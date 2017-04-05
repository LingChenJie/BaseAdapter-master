package com.jiechen.example.adapter.lv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiechen.example.R;

import java.util.List;

/**
 *
 * Created by JieChen on 2017/3/31.
 */

public class Demo2Adapter extends BaseAdapter {

    private List<String> list;

    public Demo2Adapter(List<String> list) {
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

        MyViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.section_ex1_item, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.tvItem.setText(list.get(position));
        return holder.rootView;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgView;
        private final TextView tvItem;

        public MyViewHolder(View view) {
            super(view);

            rootView = view;
            imgView = (ImageView) view.findViewById(R.id.imgItem);
            tvItem = (TextView) view.findViewById(R.id.tvItem);
        }
    }
}
