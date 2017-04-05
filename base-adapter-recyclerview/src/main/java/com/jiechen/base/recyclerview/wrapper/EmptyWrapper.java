package com.jiechen.base.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jiechen.base.recyclerview.base.ViewHolder;
import com.jiechen.base.recyclerview.utils.WrapperUtils;

/**
 * 封装具有空Adapter
 * Created by JieChen on 2017/3/29.
 */

public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_EMPTY_TYPE = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;

    public EmptyWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    /**
     * 判断是否是EmptyView
     *
     * @return true表示是
     */
    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()) {
            ViewHolder holder;
            if (mEmptyView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) {
            return 1;
        }
        return mInnerAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_EMPTY_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isEmpty()) {// 判断是否EmptyView
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {// 按原始的执行
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty()) {// 判断是否EmptyView
            WrapperUtils.setFullSpan(holder);
        }
    }

    /**
     * 设置EmptyView
     * @param emptyView 空布局
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    /**
     * 设置EmptyLayoutId
     * @param layoutId 空布局id
     */
    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }
}
