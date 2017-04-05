package com.jiechen.example.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiechen.adapter.section.SectionAdapter;
import com.jiechen.adapter.section.StatelessSection;
import com.jiechen.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Example1Fragment
 * Created by JieChen on 2017/3/30.
 */

public class Example1Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SectionAdapter mSectionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_example, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSectionAdapter = new SectionAdapter();

        for (char i = 'A'; i <= 'Z'; i++) {
            List<String> contacts = getContactsWithLetter(i);

            if (contacts.size() > 0) {
                mSectionAdapter.addSection(new ContactsSection(String.valueOf(i), contacts));
            }
        }


        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mSectionAdapter);

        return view;
    }

    /**
     * 获取所有的同一个姓氏的名字
     *
     * @param letter 名字的第一个字母
     * @return
     */
    private List<String> getContactsWithLetter(char letter) {
        List<String> contacts = new ArrayList<>();

        for (String contact : getResources().getStringArray(R.array.names)) {
            if (contact.charAt(0) == letter) {
                contacts.add(contact);
            }
        }

        return contacts;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.nav_example1);
            }
        }
    }

    class ContactsSection extends StatelessSection {

        private String title;
        private List<String> list;

        public ContactsSection(String title, List<String> list) {
            super(R.layout.section_ex1_header, R.layout.section_ex1_item);
            this.title = title;
            this.list = list;
        }

        @Override
        public int getContentItemTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder viewHolder = (ItemViewHolder) holder;

            String name = list.get(position);

            viewHolder.tvItem.setText(name);
            viewHolder.imgView.setImageResource(name.hashCode() % 2 == 0 ? R.drawable.ic_face_black_48dp : R.drawable.ic_tag_faces_black_48dp);

            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format("Clicked on position #%s of Section %s", mSectionAdapter.getSectionPosition(viewHolder.getAdapterPosition()), title), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(title);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgView;
        private final TextView tvItem;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgView = (ImageView) view.findViewById(R.id.imgItem);
            tvItem = (TextView) view.findViewById(R.id.tvItem);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }

    }

}
