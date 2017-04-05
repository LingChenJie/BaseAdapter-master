package com.jiechen.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiechen.adapter.section.Section;
import com.jiechen.adapter.section.SectionAdapter;
import com.jiechen.adapter.section.StatelessSection;
import com.jiechen.example.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Example7Fragment
 * Created by JieChen on 2017/3/31.
 */

public class Example7Fragment extends Fragment implements SearchView.OnQueryTextListener {

    private SectionAdapter sectionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example, container, false);

        sectionAdapter = new SectionAdapter();

        for(char alphabet = 'A'; alphabet <= 'Z';alphabet++) {
            List<String> contacts = getContactsWithLetter(alphabet);

            if (contacts.size() > 0) {
                ContactsSection contactsSection = new ContactsSection(String.valueOf(alphabet), contacts);
                sectionAdapter.addSection(contactsSection);
            }
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.nav_example7);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment7, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        for(Section section : sectionAdapter.getSectionsMap().values()) {
            if(section instanceof FilterSection) {
                ((FilterSection) section).filter(query);
            }
        }
        sectionAdapter.notifyDataSetChanged();

        return true;
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

    class ContactsSection extends StatelessSection implements FilterSection {

        private String title;
        private List<String> list;
        private List<String> filterList;

        public ContactsSection(String title, List<String> list) {
            super(R.layout.section_ex7_header, R.layout.section_ex7_item);

            this.title = title;
            this.list = list;
            filterList = new ArrayList<>();
            filterList.addAll(list);
        }

        @Override
        public int getContentItemTotal() {
            return filterList.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String name = filterList.get(position);
            itemHolder.tvItem.setText(name);
            itemHolder.imgItem.setImageResource(name.hashCode() % 2 == 0 ? R.drawable.ic_tag_faces_black_48dp : R.drawable.ic_face_black_48dp);

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format(Locale.CHINESE,
                            "click on position #%s of section %s", sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()),
                            title), Toast.LENGTH_SHORT).show();
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
        }

        @Override
        public void filter(String query) {
            if (TextUtils.isEmpty(query)) {
                filterList = new ArrayList<>();
                filterList.addAll(list);
                this.setVisible(true);
            } else {
                filterList.clear();
                for (String value : list) {
                    if (value.toLowerCase().contains(query.toLowerCase())) {
                        filterList.add(value);
                    }
                }

                this.setVisible(!filterList.isEmpty());
            }
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            tvItem = (TextView) view.findViewById(R.id.tvItem);
        }
    }

    interface FilterSection {
        void filter(String query);
    }
}
