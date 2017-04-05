package com.jiechen.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Example6Fragment
 * Created by JieChen on 2017/3/31.
 */

public class Example6Fragment extends Fragment {

    private SectionAdapter sectionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example, container, false);

        sectionAdapter = new SectionAdapter();

        sectionAdapter.addSection(new ExpandableMovieSection(getString(R.string.top_rated_movies_topic), getTopRatedMovieList()));
        sectionAdapter.addSection(new ExpandableMovieSection(getString(R.string.most_popular_movies_topic), getMostPopularMovieList()));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridManager);
        recyclerView.setAdapter(sectionAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.nav_example6);
            }
        }
    }

    private List<Movie> getTopRatedMovieList() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.top_rated_movies)));

        List<Movie> movieList = new ArrayList<>();
        for (String str : arrayList) {
            String[] arr = str.split("\\|");
            movieList.add(new Movie(arr[0], arr[1]));
        }
        return movieList;
    }

    private List<Movie> getMostPopularMovieList() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.top_rated_movies)));

        List<Movie> movieList = new ArrayList<>();
        for (String str : arrayList) {
            String[] arr = str.split("\\|");
            movieList.add(new Movie(arr[0], arr[1]));
        }
        return movieList;
    }

    class ExpandableMovieSection extends StatelessSection {

        private String title;
        private List<Movie> list;
        private boolean expanded = true;

        public ExpandableMovieSection(String title, List<Movie> list) {
            super(R.layout.section_ex6_header, R.layout.section_ex6_item);

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

            String name = list.get(position).getName();
            String category = list.get(position).getCategory();

            itemHolder.tvItem.setText(name);
            itemHolder.tvSubItem.setText(category);
            itemHolder.imgItem.setImageResource(R.drawable.ic_movie_black_48dp);

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format(Locale.CHINESE, "click on position #%s of section #%s",
                            sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()), title),
                            Toast.LENGTH_SHORT).show();
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

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView tvTitle;
        private final ImageView imgArrow;

        public HeaderViewHolder(View view) {
            super(view);

            rootView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            imgArrow = (ImageView) view.findViewById(R.id.imgArrow);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;
        private final TextView tvSubItem;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            tvItem = (TextView) view.findViewById(R.id.tvItem);
            tvSubItem = (TextView) view.findViewById(R.id.tvSubItem);
        }
    }

    class Movie {
        private String name;
        private String category;

        public Movie(String name, String category) {
            this.name = name;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
