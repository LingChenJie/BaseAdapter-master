package com.jiechen.base.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import com.jiechen.base.recyclerview.base.ItemViewDelegate;
import com.jiechen.base.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 基本的Adapter
 * Created by JieChen on 2017/3/29.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context context, final int layoutId, List<T> data) {
        super(context, data);

        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T item, int position) {
                CommonAdapter.this.convert(holder, item, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T item, int position);
}
