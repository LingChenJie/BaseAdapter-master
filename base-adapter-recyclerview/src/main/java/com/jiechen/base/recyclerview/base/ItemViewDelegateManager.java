package com.jiechen.base.recyclerview.base;

import android.util.SparseArray;

/**
 * 类型代理管理器
 * Created by JieChen on 2017/3/29.
 */

public class ItemViewDelegateManager<T> {

    private SparseArray<ItemViewDelegate<T>> delegates = new SparseArray<>();

    /**
     * 获取当前有多少种类型
     * @return 多少种类型
     */
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    /**
     * 添加一个类型代理
     * @param delegate 类型代理
     * @return 类型管理器
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        if (delegate != null) {
            delegates.put(delegates.size(), delegate);
        }

        return this;
    }

    /**
     * 添加一个类型代理
     * @param viewType 类型
     * @param delegate 类型代理
     * @return 类型管理器
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);
        return this;
    }

    /**
     * 移除类型代理
     * @param delegate 类型代理
     * @return 类型管理器
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }

        int indexOfValue = delegates.indexOfValue(delegate);
        if (indexOfValue >= 0) {
            delegates.removeAt(indexOfValue);
        }

        return this;
    }

    /**
     * 移除类型代理
     * @param itemType 当前类型
     * @return 类型管理器
     */
    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexOfKey = delegates.indexOfKey(itemType);

        if (indexOfKey >= 0) {
            delegates.removeAt(indexOfKey);
        }

        return this;
    }

    /**
     * 获取当前类型
     * @param item 数据bean
     * @param position 当前位置
     * @return 当前类型
     */
    public int getItemViewType(T item, int position) {
        for (int i = delegates.size() - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }

        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 转换
     * @param holder ViewHolder
     * @param item 数据bean
     * @param position 数据位置
     */
    public void convert(ViewHolder holder, T item, int position) {
        for (int i = 0, count = delegates.size(); i < count; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }

        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**
     * 根据viewType获取当前类型代理
     * @param viewType 当前类型
     * @return 当前类型代理
     */
    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    /**
     * 获取当前布局view
     * @param viewType 当前类型
     * @return layoutId
     */
    public int getItemViewLayout(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    /**
     * 根据ItemViewDelegate获取当前的类型
     * @param itemViewDelegate ItemViewDelegate
     * @return 当前的类型
     */
    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
