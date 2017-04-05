package com.jiechen.base.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jiechen.base.recyclerview.base.ItemViewDelegate;
import com.jiechen.base.recyclerview.base.ItemViewDelegateManager;
import com.jiechen.base.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 多类型的Adapter
 * Created by JieChen on 2017/3/29.
 */

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected List<T> mData;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;

    public MultiItemTypeAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 添加一个类型代理
     *
     * @param itemViewDelegate 类型代理
     * @return 当前Adapter
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 添加一个类型代理
     *
     * @param viewType         类型标识
     * @param itemViewDelegate 类型代理
     * @return 当前Adapter
     */
    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    /**
     * 是否使用了代理管理器
     *
     * @return true使用
     */
    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate delegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = delegate.getItemViewLayoutId();

        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreate(holder, holder.getConvertView());
        setListener(parent, holder, viewType);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    /**
     * 当首次创建时调用
     *
     * @param holder      ViewHolder
     * @param convertView 布局文件
     */
    private void onViewHolderCreate(ViewHolder holder, View convertView) {
    }

    /**
     * 设置监听
     *
     * @param parent   父ViewGroup
     * @param holder   ViewHolder
     * @param viewType 当前类型
     */
    private void setListener(final ViewGroup parent, final ViewHolder holder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder, holder.getAdapterPosition());
                }
            }
        });

        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    return mOnItemClickListener.onItemLongClick(v, holder, holder.getAdapterPosition());
                }
                return false;
            }
        });
    }

    /**
     * 当前view是否有事件
     *
     * @param viewType 类型标识
     * @return 默认为true
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }

    /**
     * 转换
     *
     * @param holder ViewHolder
     * @param item   数据bean
     */
    private void convert(ViewHolder holder, T item) {
        mItemViewDelegateManager.convert(holder, item, holder.getAdapterPosition());
    }

    public interface OnItemClickListener {
        /**
         * 点击事件
         *
         * @param view     布局文件
         * @param holder   ViewHolder
         * @param position 数据位置
         */
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        /**
         * 长按事件
         *
         * @param view     布局文件
         * @param holder   ViewHolder
         * @param position 数据位置
         * @return 是否生效
         */
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * 设置事件的监听
     *
     * @param onItemClickListener OnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
