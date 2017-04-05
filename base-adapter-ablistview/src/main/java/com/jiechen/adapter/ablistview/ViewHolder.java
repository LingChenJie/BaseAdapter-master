package com.jiechen.adapter.ablistview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 数据的ViewHolder
 * Created by JieChen on 2017/3/28.
 */

public class ViewHolder {

    private SparseArray<View> mViews;
    protected int mPosition;
    private View mConvertView;
    private Context mContext;
    protected int mLayoutId;

    public ViewHolder(Context context, View itemView, int position) {
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<>();
        mConvertView.setTag(this);
    }

    /**
     * 获取当前viewHolder
     * @param context 上下文
     * @param convertView 缓存布局view
     * @param parent 父view
     * @param layoutId 布局id
     * @param position 数据位置
     * @return ViewHolder
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if(convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(context, itemView, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 根据id获取当前布局中的view
     * @param id 布局id
     * @param <T>
     * @return
     */
    public <T extends View>T getView(int id) {
        View view = mViews.get(id);
        if(view == null) {
            view = mConvertView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    /**
     * 获取当前布局view
     * @return view
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 获取当前布局id
     * @return layoutId
     */
    public int getLayoutId() {
        return mLayoutId;
    }

    /**
     * 更新当前item的位置
     * @param position 当前item的位置
     */
    public void updatePosition(int position) {
        mPosition = position;
    }

    /**
     * 获取当前item的位置
     * @return 当前item的位置
     */
    public int getItemPosition() {
        return mPosition;
    }
}
