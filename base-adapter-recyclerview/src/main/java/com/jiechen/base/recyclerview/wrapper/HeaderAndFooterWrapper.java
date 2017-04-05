package com.jiechen.base.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.jiechen.base.recyclerview.base.ViewHolder;
import com.jiechen.base.recyclerview.utils.WrapperUtils;

/**
 * 封装具有头部View和底部View的Adapter
 * Created by JieChen on 2017/3/29.
 */

public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_HEADER_TYPE = 10000;
    private static final int ITEM_FOOTER_TYPE = 20000;

    private RecyclerView.Adapter mInnerAdapter;

    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mFooterViews = new SparseArray<>();

    /**
     * 添加一个头部View
     *
     * @param headerView 头部View
     */
    public void addHeaderItemView(View headerView) {
        mHeaderViews.put(mHeaderViews.size() + ITEM_HEADER_TYPE, headerView);
    }

    /**
     * 添加一个底部View
     *
     * @param footerView 底部View
     */
    public void addFooterItemView(View footerView) {
        mFooterViews.put(mFooterViews.size() + ITEM_FOOTER_TYPE, footerView);
    }

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
        } else if (mFooterViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mFooterViews.get(viewType));
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderItemView(position) || isFooterItemView(position)) {
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position - getHeaderItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderItemView(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterItemView(position)) {
            return mFooterViews.keyAt(position - getHeaderItemCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeaderItemCount());
    }

    @Override
    public int getItemCount() {
        return getRealItemCount() + getHeaderItemCount() + getFooterItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);

                if (mHeaderViews.get(viewType) != null || mFooterViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }

                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }

                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        int position = holder.getLayoutPosition();
        if (isHeaderItemView(position) || isFooterItemView(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    /**
     * 判断是否是头部View
     *
     * @param position 所处位置
     * @return true:在
     */
    private boolean isHeaderItemView(int position) {
        return getHeaderItemCount() > position;
    }

    /**
     * 判断是否是底部View
     *
     * @param position 所处位置
     * @return true:在
     */
    private boolean isFooterItemView(int position) {
        return position >= getHeaderItemCount() + getRealItemCount();
    }

    /**
     * 获取不包含头部和底部的Adapter的Adapter个数
     *
     * @return 真实Adapter个数
     */
    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    /**
     * 获取头部Adapter个数
     *
     * @return HeaderItemCount
     */
    private int getHeaderItemCount() {
        return mHeaderViews.size();
    }

    /**
     * 获取底部Adapter个数
     *
     * @return FooterItemCount
     */
    private int getFooterItemCount() {
        return mFooterViews.size();
    }

}
