package com.jiechen.example.fragment;

import android.os.Bundle;
import android.os.Handler;
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

import com.jiechen.adapter.section.Section;
import com.jiechen.adapter.section.SectionAdapter;
import com.jiechen.example.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Example3Fragment
 * Created by JieChen on 2017/3/31.
 */

public class Example3Fragment extends Fragment {

    private Handler mHandler = new Handler();

    private SectionAdapter sectionAdapter;

    NewsSection worldNews;
    NewsSection bizNews;
    NewsSection techNews;
    NewsSection sportsNews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example, container, false);

        sectionAdapter = new SectionAdapter();

        worldNews = new NewsSection(NewsSection.WORLD);
        bizNews = new NewsSection(NewsSection.BUSINESS);
        techNews = new NewsSection(NewsSection.TECHNOLOGY);
        sportsNews = new NewsSection(NewsSection.SPORT);

        sectionAdapter.addSection(worldNews);
        sectionAdapter.addSection(bizNews);
        sectionAdapter.addSection(techNews);
        sectionAdapter.addSection(sportsNews);

        loadNews(worldNews);
        loadNews(bizNews);
        loadNews(techNews);
        loadNews(sportsNews);

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
                activity.getSupportActionBar().setTitle(R.string.nav_example3);
            }
        }
    }

    class NewsSection extends Section {

        final static int WORLD = 0;
        final static int BUSINESS = 1;
        final static int TECHNOLOGY = 2;
        final static int SPORT = 3;

        final int topic;

        String title;
        List<String> list;
        int imgPlaceholderResId;

        public NewsSection(int topic) {
            super(R.layout.section_ex3_header, R.layout.section_ex3_footer, R.layout.section_ex3_loading, R.layout.section_ex3_failed, R.layout.section_ex3_item);

            this.topic = topic;
            this.list = Collections.EMPTY_LIST;

            switch (topic) {
                case WORLD:
                    this.title = getString(R.string.world_topic);
                    this.imgPlaceholderResId = R.drawable.ic_public_black_48dp;
                    break;

                case BUSINESS:
                    this.title = getString(R.string.biz_topic);
                    this.imgPlaceholderResId = R.drawable.ic_business_black_48dp;
                    break;

                case TECHNOLOGY:
                    this.title = getString(R.string.tech_topic);
                    this.imgPlaceholderResId = R.drawable.ic_devices_other_black_48dp;
                    break;

                case SPORT:
                    this.title = getString(R.string.sports_topic);
                    this.imgPlaceholderResId = R.drawable.ic_directions_run_black_48dp;
                    break;
            }
        }

        @Override
        public int getContentItemTotal() {
            return list.size();
        }

        public int getTopic() {
            return topic;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String[] item = list.get(position).split("\\|");

            itemHolder.tvHeader.setText(item[0]);
            itemHolder.tvDate.setText(item[1]);
            itemHolder.imgItem.setImageResource(imgPlaceholderResId);

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format("Clicked on position #%s of Section %s", sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()), title), Toast.LENGTH_SHORT).show();
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
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            final FooterViewHolder footerHolder = (FooterViewHolder) holder;

            footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format("Clicked on footer of Section %s", title), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getFailedViewHolder(View view) {
            return new FailedViewHolder(view);
        }

        @Override
        public void onBindFailedViewHolder(RecyclerView.ViewHolder holder) {
            final FailedViewHolder failedHolder = (FailedViewHolder) holder;

            failedHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadNews(NewsSection.this);
                }
            });
        }
    }

    private void loadNews(final NewsSection section) {
        int timeInMills = new Random().nextInt((4000 - 3000) + 1) + 3000;

        section.setState(Section.State.LOADING);
        section.setHasFooter(false);
        sectionAdapter.notifyDataSetChanged();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int failed = new Random().nextInt((3 - 1) + 1) + 1;

                if (failed == 1) {
                    section.setState(Section.State.FAILED);
                } else {
                    int arrayResource;

                    switch (section.getTopic()) {
                        case NewsSection.WORLD:
                            arrayResource = R.array.news_world;
                            break;

                        case NewsSection.BUSINESS:
                            arrayResource = R.array.news_biz;
                            break;

                        case NewsSection.SPORT:
                            arrayResource = R.array.news_sports;
                            break;

                        case NewsSection.TECHNOLOGY:
                            arrayResource = R.array.news_tech;
                            break;

                        default:
                            throw new IllegalStateException("Invalid topic");
                    }

                    section.setList(getNews(arrayResource));
                    section.setState(Section.State.LOADED);
                    section.setHasFooter(true);
                }

                sectionAdapter.notifyDataSetChanged();
            }
        }, timeInMills);
    }

    private List<String> getNews(int arrayResource) {
        return new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayResource)));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvHeader;
        private final TextView tvDate;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            tvHeader = (TextView) view.findViewById(R.id.tvHeader);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;

        public FooterViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
        }
    }

    class FailedViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;

        public FailedViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
        }
    }
}
