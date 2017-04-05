package com.jiechen.base.recyclerview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 定义自己的ViewHolder
 * Created by JieChen on 2017/3/29.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mViews = new SparseArray<>();
    }

    /**
     * 创建一个ViewHolder
     *
     * @param context  上下文
     * @param itemView 布局文件
     * @return ViewHolder
     */
    public static ViewHolder createViewHolder(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    /**
     * 创建一个ViewHolder
     *
     * @param context  上下文
     * @param parent   父ViewGroup
     * @param layoutId 布局id
     * @return ViewHolder
     */
    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }

    /**
     * 根据id获取View
     * @param id id
     * @param <T>
     * @return View
     */
    public <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    /**
     * 获取当前的布局view
     * @return view
     */
    public View getConvertView() {
        return itemView;
    }
}
