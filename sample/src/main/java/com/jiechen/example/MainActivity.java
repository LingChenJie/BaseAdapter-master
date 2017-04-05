package com.jiechen.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiechen.adapter.ablistview.CommonAdapter;
import com.jiechen.adapter.ablistview.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mData = new ArrayList<>(Arrays.asList("MultiItem ListView",
            "RecyclerView", "MultiItem RecyclerView", "Section RecyclerView", "DemoAdapter"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);

        mListView.setAdapter(new CommonAdapter<String>(this, R.layout.item_list, mData) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                TextView tvTitle = holder.getView(R.id.item_list_title);
                tvTitle.setText(item);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, MultiItemListVIewActivity.class);
                        break;

                    case 1:
                        intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                        break;

                    case 2:
                        intent = new Intent(MainActivity.this, MultiItemRvActivity.class);
                        break;

                    case 3:
                        intent = new Intent(MainActivity.this, SectionActivity.class);
                        break;

                    case 4:
                        intent = new Intent(MainActivity.this, DemoActivity.class);
                        break;
                }

                startActivity(intent);
            }
        });
    }


}
