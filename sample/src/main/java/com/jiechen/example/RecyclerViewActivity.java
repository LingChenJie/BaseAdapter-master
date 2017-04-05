package com.jiechen.example;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiechen.base.recyclerview.CommonAdapter;
import com.jiechen.base.recyclerview.MultiItemTypeAdapter;
import com.jiechen.base.recyclerview.base.ViewHolder;
import com.jiechen.base.recyclerview.wrapper.EmptyWrapper;
import com.jiechen.base.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.jiechen.base.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";

    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
        processLogic();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

//        initEmptyView();
//        initHeaderAndFooterView();
        initLoadMoreView();
    }

    /**
     * 测试空View
     */
    private void initEmptyView() {
        mAdapter = new CommonAdapter<String>(this, R.layout.item_list, mData) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(this).inflate(R.layout.item_empty, mRecyclerView, false));


        mRecyclerView.setAdapter(mEmptyWrapper);
    }

    /**
     * 测试头部和底部View
     */
    private void initHeaderAndFooterView() {
        mAdapter = new CommonAdapter<String>(this, R.layout.item_list, mData) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                TextView tvTitle = holder.getView(R.id.item_list_title);
                tvTitle.setText(item + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
            }
        };

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        TextView t3 = new TextView(this);
        t3.setText("Footer 1");
        TextView t4 = new TextView(this);
        t4.setText("Footer 2");
        mHeaderAndFooterWrapper.addHeaderItemView(t1);
        mHeaderAndFooterWrapper.addHeaderItemView(t2);
        mHeaderAndFooterWrapper.addFooterItemView(t3);
        mHeaderAndFooterWrapper.addFooterItemView(t4);

        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
    }

    /**
     * 测试加载更多
     */
    private void initLoadMoreView() {
        mAdapter = new CommonAdapter<String>(this, R.layout.item_list, mData) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                TextView tvTitle = holder.getView(R.id.item_list_title);
                tvTitle.setText(item + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
            }
        };

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        mHeaderAndFooterWrapper.addHeaderItemView(t1);
        mHeaderAndFooterWrapper.addHeaderItemView(t2);

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreLayoutId(R.layout.item_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequest() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            mData.add("add : " + i);
                        }
                        mLoadMoreWrapper.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(RecyclerViewActivity.this, "onItemClick; position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(RecyclerViewActivity.this, "onItemLongClick; position = " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * 处理业务逻辑
     */
    private void processLogic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                for (int i = 'A'; i <= 'z'; i++) {
                    mData.add((char) i + "");
                }

                RecyclerViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHeaderAndFooterWrapper.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_grid:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;

            case R.id.action_liner:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.action_staggered:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        mRecyclerView.setAdapter(mLoadMoreWrapper);

        return super.onOptionsItemSelected(item);
    }
}
