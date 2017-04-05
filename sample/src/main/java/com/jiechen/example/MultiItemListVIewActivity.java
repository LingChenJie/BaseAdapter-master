package com.jiechen.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jiechen.example.adapter.lv.ChatAdapter;
import com.jiechen.example.bean.ChatMessage;

public class MultiItemListVIewActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_item_list_view);

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setDivider(null);
        mListView.setAdapter(new ChatAdapter(this, ChatMessage.MOCK_DATAS));
    }
}
