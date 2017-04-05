package com.jiechen.adapter.ablistview;

import android.content.Context;

import com.jiechen.adapter.ablistview.base.ItemViewDelegate;

import java.util.List;

/**
 *
 * Created by JieChen on 2017/3/28.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context,final int layoutId, List<T> data) {
        super(context, data);

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
