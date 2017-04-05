package com.jiechen.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 没有加载状态的Section
 * Created by JieChen on 2017/3/30.
 */

public abstract class StatelessSection extends Section {

    public StatelessSection(int itemResourceId) {
        mItemResourceId = itemResourceId;
    }

    public StatelessSection(int headerResourceId, int itemResourceId) {
        this(itemResourceId);
        mHeaderResourceId = headerResourceId;
        mHasHeader = true;
    }

    public StatelessSection(int headerResourceId, int footerResourceId, int itemResourceId) {
        this(headerResourceId, itemResourceId);
        mFooterResourceId = footerResourceId;
        mHasFooter = true;
    }

    @Override
    public final void onBindLoadingViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindLoadingViewHolder(holder);
    }

    @Override
    public final RecyclerView.ViewHolder getLoadingViewHolder(View view) {
        return super.getLoadingViewHolder(view);
    }

    @Override
    public final void onBindFailedViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindFailedViewHolder(holder);
    }

    @Override
    public final RecyclerView.ViewHolder getFailedViewHolder(View view) {
        return super.getFailedViewHolder(view);
    }

}
