package com.jiechen.example.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Example5Fragment
 * Created by JieChen on 2017/3/31.
 */

public class Example5Fragment extends Fragment {

    private SectionAdapter sectionAdapter;
    private StaggeredSectionAdapter staggeredSectionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example, container, false);

        sectionAdapter = new SectionAdapter();
        staggeredSectionAdapter = new StaggeredSectionAdapter();

        sectionAdapter.addSection(new MovieSection(getString(R.string.top_rated_movies_topic), getTopRatedMoviesList()));
        sectionAdapter.addSection(new MovieSection(getString(R.string.most_popular_movies_topic), getPopularRatedMoviesList()));
        staggeredSectionAdapter.addSection(new MovieSection(getString(R.string.top_rated_movies_topic), getTopRatedMoviesList()));
        staggeredSectionAdapter.addSection(new MovieSection(getString(R.string.most_popular_movies_topic), getPopularRatedMoviesList()));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
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
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(sectionAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.nav_example5);
            }
        }
    }

    private List<Movie> getTopRatedMoviesList() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.top_rated_movies)));

        List<Movie> movieList = new ArrayList<>();

        for (String str : arrayList) {
            String array[] = str.split("\\|");
            movieList.add(new Movie(array[0], array[1]));
        }
        return movieList;
    }

    class StaggeredSectionAdapter extends SectionAdapter {

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);

            switch (staggeredSectionAdapter.getSectionItemViewType(holder.getAdapterPosition())) {
                case SectionAdapter.VIEW_TYPE_HEADER:
                    StaggeredGridLayoutManager.LayoutParams staggeredParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    staggeredParams.setFullSpan(true);
                    break;
            }
        }
    }

    private List<Movie> getPopularRatedMoviesList() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.most_popular_movies)));

        List<Movie> movieList = new ArrayList<>();

        for (String str : arrayList) {
            String array[] = str.split("\\|");
            movieList.add(new Movie(array[0], array[1]));
        }
        return movieList;
    }

    class MovieSection extends StatelessSection {

        private String title;
        private List<Movie> list;

        public MovieSection(String title, List<Movie> list) {
            super(R.layout.section_ex5_header, R.layout.section_ex5_item);

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
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String name = list.get(position).getName();
            String category = list.get(position).getCategory();

            itemHolder.tvItem.setText(name);
            itemHolder.tvSubItem.setText(category);
            itemHolder.imgItem.setImageResource(R.drawable.ic_movie_black_48dp);

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
            headerHolder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format(Locale.CHINESE,
                            "Click on more button from the header of Section %s", title),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;
        private final TextView tvSubItem;

        public ItemViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            tvSubItem = (TextView) itemView.findViewById(R.id.tvSubItem);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final Button btnMore;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            btnMore = (Button) itemView.findViewById(R.id.btnMore);
        }
    }


    class Movie {
        String name;
        String category;

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
