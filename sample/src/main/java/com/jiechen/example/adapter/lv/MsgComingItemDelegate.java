package com.jiechen.example.adapter.lv;

import android.widget.ImageView;
import android.widget.TextView;

import com.jiechen.example.R;
import com.jiechen.adapter.ablistview.ViewHolder;
import com.jiechen.adapter.ablistview.base.ItemViewDelegate;
import com.jiechen.example.bean.ChatMessage;

/**
 * Created by JieChen on 2017/3/28.
 */

public class MsgComingItemDelegate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_from_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage item, int position) {
        TextView tvContent = holder.getView(R.id.chat_from_content);
        TextView tvName = holder.getView(R.id.chat_from_name);
        ImageView ivIcon = holder.getView(R.id.chat_from_icon);

        tvContent.setText(item.getContent());
        tvName.setText(item.getName());
        ivIcon.setImageResource(item.getIcon());
    }
}
