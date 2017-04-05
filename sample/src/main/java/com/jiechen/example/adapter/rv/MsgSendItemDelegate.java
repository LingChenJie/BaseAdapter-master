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

public class MsgSendItemDelegate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_send_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return !item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage item, int position) {
        TextView tvName = holder.getView(R.id.chat_send_name);
        TextView tvContent = holder.getView(R.id.chat_send_content);
        ImageView ivIcon = holder.getView(R.id.chat_send_icon);

        tvName.setText(item.getName());
        tvContent.setText(item.getContent());
        ivIcon.setImageResource(item.getIcon());
    }
}
