package com.jiechen.base.recyclerview.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * Created by JieChen on 2017/3/29.
 */

public class WrapperUtils {

    public interface SpanSizeCallback {
        /**
         * 返回当前holder要占的列数
         *
         * @param layoutManager GridLayoutManager
         * @param oldLookup     起始的SpanSizeLookup
         * @param position      当前的位置
         * @return 当前holder要占的列数
         */
        int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup,
                        int position);
    }

    /**
     * @param innerAdapter Adapter
     * @param recyclerView RecyclerView
     * @param callback     SpanSizeCallback
     */
    public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView recyclerView,
                                                final SpanSizeCallback callback) {

        innerAdapter.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                }
            });
        }
    }

    /**
     * 设置StaggeredGridLayoutManager ViewHolder占一行
     *
     * @param holder ViewHolder
     */
    public static void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();

        if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams staggeredParams = (StaggeredGridLayoutManager.LayoutParams) params;
            staggeredParams.setFullSpan(true);
        }
    }
}
