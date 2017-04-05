package com.jiechen.base.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jiechen.base.recyclerview.base.ViewHolder;
import com.jiechen.base.recyclerview.utils.WrapperUtils;

/**
 * 封装具有加载更多的Adapter
 * Created by JieChen on 2017/3/29.
 */

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_LOAD_MORE_TYPE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    /**
     * 设置加载更多布局
     * @param loadMoreView 布局View
     */
    public void setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
    }

    /**
     * 设置加载更多布局
     * @param layoutId 布局id
     */
    public void setLoadMoreLayoutId(int layoutId) {
        mLoadMoreLayoutId = layoutId;
    }

    /**
     * 是否显示加载更多
     *
     * @return true 显示
     */
    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    /**
     * 当前位置是否是加载更多条目
     *
     * @param position 当前位置
     * @return true 是
     */
    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (ITEM_LOAD_MORE_TYPE == viewType) {// 判断是否是加载更多条目
            ViewHolder holder;
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }

        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequest();// 触发加载更多数据
            }
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_LOAD_MORE_TYPE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }

                if (oldLookup != null) {
                    oldLookup.getSpanSize(position);
                }

                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getAdapterPosition())) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public int getItemCount() {
        return getRealItemCount() + (hasLoadMore() ? 1 : 0);
    }

    /**
     * 真实显示的条目count
     *
     * @return Adapter的数量
     */
    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    public interface OnLoadMoreListener {
        /**
         * 加载更多的请求
         */
        void onLoadMoreRequest();
    }

    /**
     * 设置加载更多的监听
     *
     * @param onLoadMoreListener OnLoadMoreListener
     * @return 当前LoadMoreWrapper
     */
    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        if (onLoadMoreListener != null) {
            mOnLoadMoreListener = onLoadMoreListener;
        }
        return this;
    }
}
