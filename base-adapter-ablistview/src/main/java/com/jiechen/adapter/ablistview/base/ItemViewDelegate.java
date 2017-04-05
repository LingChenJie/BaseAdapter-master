package com.jiechen.adapter.ablistview.base;

import com.jiechen.adapter.ablistview.ViewHolder;

/**
 * 单个类型代理
 * Created by JieChen on 2017/3/28.
 */

public interface ItemViewDelegate<T> {

    /**
     * 获取当前布局id
     * @return layoutId
     */
    int getItemViewLayoutId();

    /**
     * 判断当前位置是否对应数据bean
     * @param item 数据bean
     * @param position 在数据中的位置
     * @return true 表示相对照
     */
    boolean isForViewType(T item, int position);

    /**
     * 转换
     * @param holder item对象的ViewHolder
     * @param item 数据bean
     * @param position 在数据中的位置
     */
    void convert(ViewHolder holder, T item, int position);

}
