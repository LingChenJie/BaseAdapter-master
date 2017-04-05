package com.jiechen.example.adapter.rv;

import android.widget.ImageView;
import android.widget.TextView;

import com.jiechen.base.recyclerview.base.ItemViewDelegate;
import com.jiechen.base.recyclerview.base.ViewHolder;
import com.jiechen.example.R;
import com.jiechen.example.bean.ChatMessage;

/**
 * Created by JieChen on 2017/3/29.
 */

public class MsgCommingItemDelegate implements ItemViewDelegate<ChatMessage> {

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
        TextView tvName = holder.getView(R.id.chat_from_content);
        TextView tvContent = holder.getView(R.id.chat_from_content);
        ImageView ivIcon = holder.getView(R.id.chat_from_icon);

        tvName.setText(item.getContent());
        tvContent.setText(item.getContent());
        ivIcon.setImageResource(item.getIcon());
    }
}
