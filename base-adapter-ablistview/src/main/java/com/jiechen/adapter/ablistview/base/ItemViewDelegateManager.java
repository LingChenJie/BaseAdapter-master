package com.jiechen.adapter.ablistview.base;

import android.util.SparseArray;

import com.jiechen.adapter.ablistview.ViewHolder;

/**
 * 类型管理器，对所有类型进行统一管理
 * Created by JieChen on 2017/3/28.
 */

public class ItemViewDelegateManager<T> {

    private SparseArray<ItemViewDelegate<T>> delegates = new SparseArray<>();

    /**
     * 获取当前多少种类型
     * @return 类型个数
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
     * @param viewType 类型位置
     * @param delegate 类型代理
     * @return 类型管理器
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        if (delegate != null) {
            delegates.put(viewType, delegate);
        }
        return this;
    }

    /**
     * 移除一个类型代理
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
     * 移除一个指定位置的类型代理
     * @param itemType 类型位置
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
     * @param position 当前类型中数据所处的位置
     * @return 类型位置
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
     * @param holder ViewHolder对象
     * @param item 数据bean
     * @param position 当前类型中数据所处的位置
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
     * 获取当前数据类型使用的布局
     * @param viewType 类型位置
     * @return 布局id
     */
    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    /**
     * 获取类型位置
     * @param itemViewDelegate 类型代理
     * @return 类型位置
     */
    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }

    /**
     * 获取代理对象
     * @param item 数据bean
     * @param position 数据所处的位置
     * @return 代理对象
     */
    public ItemViewDelegate getItemViewDelegate(T item, int position) {
        for(int i = delegates.size() - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if(delegate.isForViewType(item, position)) {
                return delegate;
            }
        }

        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 获取当前数据类型使用的布局
     * @param item 数据bean
     * @param position 数据所处的位置
     * @return 布局id
     */
    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }
}
