package com.jiechen.example.adapter.rv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiechen.example.R;

import java.util.List;

/**
 * Created by JieChen on 2017/3/30.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.MyViewHolder> {

    private List<String> list;

    public DemoAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_ex1_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvItem.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
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
