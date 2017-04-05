package com.jiechen.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 具有加载状态的Section
 * Created by JieChen on 2017/3/30.
 */

public abstract class Section {

    public enum State {LOADING, LOADED, FAILED}

    private State mState = State.LOADED;
    protected boolean mVisible = true;
    private Integer mLoadingResourceId, mFailedResourceId;

    protected boolean mHasHeader = false, mHasFooter = false;
    protected Integer mHeaderResourceId, mFooterResourceId;

    protected int mItemResourceId;

    public Section() {
    }

    /**
     * 创建一个含有加载中、加载失败的布局的Section
     *
     * @param loadingResourceId loadingId
     * @param failedResourceId  failedId
     * @param itemResourceId    layoutId
     */
    public Section(int loadingResourceId, int failedResourceId, int itemResourceId) {
        mLoadingResourceId = loadingResourceId;
        mFailedResourceId = failedResourceId;
        mItemResourceId = itemResourceId;
    }

    /**
     * 创建一个含有头部和加载中、加载失败的布局的Section
     *
     * @param headerResourceId  header_Id
     * @param loadingResourceId loading_Id
     * @param failedResourceId  failed_Id
     * @param itemResourceId    layout_Id
     */
    public Section(int headerResourceId, int loadingResourceId, int failedResourceId, int itemResourceId) {
        this(loadingResourceId, failedResourceId, itemResourceId);
        mHeaderResourceId = headerResourceId;
        mHasHeader = true;
    }

    /**
     * 创建一个含有头部底部和加载中、加载失败的布局的Section
     *
     * @param headerResourceId  header_Id
     * @param footerResourceId  footer_Id
     * @param loadingResourceId loading_Id
     * @param failedResourceId  failed_Id
     * @param itemResourceId    layout_Id
     */
    public Section(int headerResourceId, int footerResourceId, int loadingResourceId, int failedResourceId, int itemResourceId) {
        this(headerResourceId, loadingResourceId, failedResourceId, itemResourceId);
        mFooterResourceId = footerResourceId;
        mHasFooter = true;
    }

    /**
     * 绑定ViewHolder
     *
     * @param holder   ViewHolder
     * @param position 数据的位置
     */
    public final void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (mState) {
            case LOADING:
                onBindLoadingViewHolder(holder);
                break;

            case LOADED:
                onBindItemViewHolder(holder, position);
                break;

            case FAILED:
                onBindFailedViewHolder(holder);
                break;

            default:
                throw new IllegalStateException("Invalid state");
        }
    }

    public final int getSectionItemTotal() {
        int contentItemTotal;

        switch (mState) {
            case LOADING:
            case FAILED:
                contentItemTotal = 1;
                break;

            case LOADED:
                contentItemTotal = getContentItemTotal();
                break;
            default:
                throw new IllegalStateException("Invalid state");
        }
        return contentItemTotal + (mHasHeader ? 1 : 0) + (mHasFooter ? 1 : 0);
    }


    /**
     * 获取当前Section数据的总数
     *
     * @return 数据的总数
     */
    public abstract int getContentItemTotal();

    /**
     * 获取ViewHolder
     *
     * @param view 布局View
     * @return
     */
    public abstract RecyclerView.ViewHolder getItemViewHolder(View view);

    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionAdapter.EmptyViewHolder(view);
    }

    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new SectionAdapter.EmptyViewHolder(view);
    }

    public RecyclerView.ViewHolder getLoadingViewHolder(View view) {
        return new SectionAdapter.EmptyViewHolder(view);
    }

    public RecyclerView.ViewHolder getFailedViewHolder(View view) {
        return new SectionAdapter.EmptyViewHolder(view);
    }

    /**
     * 绑定的数据
     *
     * @param holder   ViewHolder
     * @param position 数据的位置
     */
    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    public void onBindLoadingViewHolder(RecyclerView.ViewHolder holder) {
        // Nothing to bind here.
    }

    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        // Nothing to bind here.
    }

    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        // Nothing to bind here.
    }

    public void onBindFailedViewHolder(RecyclerView.ViewHolder holder) {
        // Nothing to bind here.
    }


    public final void setState(State state) {
        mState = state;
    }

    public final State getState() {
        return mState;
    }

    public final void setVisible(boolean visible) {
        mVisible = visible;
    }

    public final boolean isVisible() {
        return mVisible;
    }

    public final void setHasHeader(boolean hasHeader) {
        mHasHeader = hasHeader;
    }

    public final boolean hasHeader() {
        return mHasHeader;
    }

    public final void setHasFooter(boolean hasFooter) {
        mHasFooter = hasFooter;
    }

    public final boolean hasFooter() {
        return mHasFooter;
    }

    public final Integer getLoadingResourceId() {
        return mLoadingResourceId;
    }

    public final Integer getFailedResourceId() {
        return mFailedResourceId;
    }

    public final int getItemResourceId() {
        return mItemResourceId;
    }

    public final Integer getHeaderResourceId() {
        return mHeaderResourceId;
    }

    public final Integer getFooterResourceId() {
        return mFooterResourceId;
    }

    public final void setLoadingResourceId(int loadingResourceId) {
        this.mLoadingResourceId = loadingResourceId;
    }

    public final void setFailedResourceId(int failedResourceId) {
        this.mFailedResourceId = failedResourceId;
    }

    public final void setHeaderResourceId(int headerResourceId) {
        this.mHeaderResourceId = headerResourceId;
    }

    public final void setFooterResourceId(int footerResourceId) {
        this.mFooterResourceId = footerResourceId;
    }

    public final void setItemResourceId(int itemResourceId) {
        this.mItemResourceId = itemResourceId;
    }
}
