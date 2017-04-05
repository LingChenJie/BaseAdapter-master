package com.jiechen.base.recyclerview.base;

/**
 * 类型代理
 * Created by JieChen on 2017/3/29.
 */

public interface ItemViewDelegate<T> {

    /**
     * 获取当前布局id
     *
     * @return layoutId
     */
    int getItemViewLayoutId();

    /**
     * 根据位置判断当前是某一类型
     *
     * @param item     数据bean
     * @param position 数据位置
     * @return true表示匹配
     */
    boolean isForViewType(T item, int position);

    /**
     * 转换
     *
     * @param holder   ViewHolder
     * @param item     数据bean
     * @param position 数据位置
     */
    void convert(ViewHolder holder, T item, int position);
}
