package com.jiechen.example;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiechen.base.recyclerview.wrapper.LoadMoreWrapper;
import com.jiechen.example.adapter.rv.ChatAdapter;
import com.jiechen.example.bean.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class MultiItemRvActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LoadMoreWrapper mLoadMoreWrapper;

    private List<ChatMessage> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_item_rv);

        initView();
        processLogic();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void processLogic() {
        mData.addAll(ChatMessage.MOCK_DATAS);
        final ChatAdapter chatAdapter = new ChatAdapter(this, mData);

        mLoadMoreWrapper = new LoadMoreWrapper(chatAdapter);
        mLoadMoreWrapper.setLoadMoreLayoutId(R.layout.item_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequest() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean coming = Math.random() > 0.5;
                        ChatMessage msg = null;
                        msg = new ChatMessage(coming ? R.drawable.renma : R.drawable.xiaohei, coming ? "人马" : "xiaohei", "where are you " + mData.size(),
                                null, coming);
                        mData.add(msg);
                        mLoadMoreWrapper.notifyDataSetChanged();;
                    }
                }, 2000);
            }
        });

        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }
}
