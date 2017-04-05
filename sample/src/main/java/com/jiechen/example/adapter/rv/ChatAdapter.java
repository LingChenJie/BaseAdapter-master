package com.jiechen.example.adapter.rv;

import android.content.Context;

import com.jiechen.base.recyclerview.MultiItemTypeAdapter;
import com.jiechen.example.bean.ChatMessage;

import java.util.List;

/**
 * Created by JieChen on 2017/3/29.
 */

public class ChatAdapter extends MultiItemTypeAdapter<ChatMessage> {

    public ChatAdapter(Context context, List<ChatMessage> data) {
        super(context, data);

        addItemViewDelegate(new MsgSendItemDelegate());
        addItemViewDelegate(new MsgCommingItemDelegate());
    }
}
