package com.jiechen.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Locale;

/**
 * Example4Fragment
 * Created by JieChen on 2017/3/31.
 */

public class Example4Fragment extends Fragment {

    private SectionAdapter sectionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_example, container, false);

        sectionAdapter = new SectionAdapter();

        for (char i = 'A'; i < 'Z'; i++) {
            List<String> contacts = getContactsWithLetter(i);

            if (contacts.size() > 0) {
                sectionAdapter.addSection(new ExpandableContactsSection(String.valueOf(i), contacts));
            }
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);

        return view;
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

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.nav_example4);
            }
        }
    }

    class ExpandableContactsSection extends StatelessSection {

        private String title;
        private List<String> list;
        private boolean expanded = true;

        public ExpandableContactsSection(String title, List<String> list) {
            super(R.layout.section_ex4_header, R.layout.section_ex4_item);

            this.title = title;
            this.list = list;
        }

        @Override
        public int getContentItemTotal() {
            return expanded ? list.size() : 0;
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String name = list.get(position);
            itemHolder.tvItem.setText(name);
            itemHolder.imgItem.setImageResource(name.hashCode() % 2 == 0 ? R.drawable.ic_face_black_48dp : R.drawable.ic_tag_faces_black_48dp);

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format(Locale.CHINESE, "click on position #%d of section %s",
                            sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()), title), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);

            headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expanded = !expanded;

                    headerHolder.imgArrow.setImageResource(expanded ?
                            R.drawable.ic_keyboard_arrow_up_black_18dp : R.drawable.ic_keyboard_arrow_down_black_18dp);
                    sectionAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;

        public ItemViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView tvTitle;
        private final ImageView imgArrow;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imgArrow = (ImageView) itemView.findViewById(R.id.imgArrow);
        }
    }

}
