package com.jiechen.adapter.ablistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiechen.adapter.ablistview.base.ItemViewDelegate;
import com.jiechen.adapter.ablistview.base.ItemViewDelegateManager;

import java.util.List;

/**
 * 多类型的Adapter
 * Created by JieChen on 2017/3/28.
 */

public class MultiItemTypeAdapter<T> extends BaseAdapter{

    protected Context mContext;
    protected List<T> mData;
    private ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 添加一个类型代理
     * @param itemViewDelegate 类型代理
     * @return 当前的Adapter
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 是否使用了多类型
     * @return true 表示使用
     */
    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if(useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(useItemViewDelegateManager()) {
            int viewType = mItemViewDelegateManager.getItemViewType(mData.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mData.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();

        ViewHolder holder = null;
        if(convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(mContext, itemView, position);
            holder.mLayoutId = layoutId;
            onViewHolderCreate(holder, holder.getConvertView());
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }

        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    /**
     * 首次创建holder
     * @param holder 自定义的ViewHolder
     * @param convertView 自定义的布局文件
     */
    protected void onViewHolderCreate(ViewHolder holder, View convertView) {
    }

    /**
     * 填充数据
     * @param holder 自定义的ViewHolder
     * @param item 数据bean
     * @param position 数据位置
     */
    protected void convert(ViewHolder holder, T item, int position) {
    }

}
