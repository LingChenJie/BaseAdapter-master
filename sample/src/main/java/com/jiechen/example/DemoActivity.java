package com.jiechen.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.jiechen.example.adapter.lv.Demo2Adapter;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private Demo2Adapter demo2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        List<String> list = new ArrayList<>();
        for (char i = 'A'; i <= 'Z'; i++) {
            List<String> contacts = getContactsWithLetter(i);

            if (contacts.size() > 0) {
                list.addAll(contacts);
            }
        }
        demo2Adapter = new Demo2Adapter(list);

        ListView listView = (ListView) findViewById(R.id.recycler_view);
        listView.setAdapter(demo2Adapter);
    }

    private List<String> getContactsWithLetter(char letter) {
        List<String> contacts = new ArrayList<>();

        for (String contact : getResources().getStringArray(R.array.names)) {
            if (contact.charAt(0) == letter) {
                contacts.add(contact);
            }
        }

        return contacts;
    }
}
