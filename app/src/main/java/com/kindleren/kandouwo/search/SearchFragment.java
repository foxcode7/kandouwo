package com.kindleren.kandouwo.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.inject.Inject;
import com.kandouwo.model.datarequest.Request;
import com.kandouwo.model.datarequest.search.HotWord;
import com.kandouwo.model.datarequest.search.SearchHotwordRequest;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.base.RequestLoader;
import com.kindleren.kandouwo.common.config.BaseConfig;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by foxcoder on 14-9-22.
 */
public class SearchFragment extends BaseFragment {
    @Inject
    private LayoutInflater inflater;

    private static final int LOADER_ID_HOTWORD = 1;

    @InjectView(R.id.pager)
    private ViewPager viewPager;

    @InjectView(R.id.indicator)
    private CirclePageIndicator pageIndicator;

    @InjectView(R.id.camera)
    private ImageView imageViewCamera;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID_HOTWORD, null, hotWordLoader);
        if(getView()!=null)
        {
            imageViewCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MipcaActivityCapture.class);
                    startActivity(intent);
                    return;
                }
            });
        }
    }


    private LoaderManager.LoaderCallbacks hotWordLoader = new LoaderManager.LoaderCallbacks<List<HotWord>>() {

        @Override
        public Loader<List<HotWord>> onCreateLoader(int id, Bundle args) {

            return new RequestLoader<List<HotWord>>(getActivity(),new SearchHotwordRequest(), Request.Origin.NET);
        }

        @Override
        public void onLoadFinished(Loader<List<HotWord>> loader, List<HotWord> strings) {

            if (strings==null||strings.isEmpty()) {
                strings = loadHotWord();
            }
            viewPager.setAdapter(new HotWordAdapter(getChildFragmentManager(), strings));
            if (viewPager.getAdapter().getCount() > 1) {
                pageIndicator.setViewPager(viewPager);
                pageIndicator.setFillColor(getResources().getColor(R.color.green));
                pageIndicator.setPageColor(getResources().getColor(R.color.gray_light));
                pageIndicator.setRadius(BaseConfig.dp2px(4));
                pageIndicator.setVisibility(View.VISIBLE);
                getView().findViewById(R.id.indicator_divider).setVisibility(View.VISIBLE);
            }else {
                pageIndicator.setVisibility(View.GONE);
                getView().findViewById(R.id.indicator_divider).setVisibility(View.GONE);
            }
        }


        private List<HotWord> loadHotWord() {
            List<HotWord> hotWordList = new ArrayList<HotWord>();
            String[] hotWords = getResources().getStringArray(R.array.search_hot_word);
            for (int i = 0; i < hotWords.length; i++) {
                HotWord hotWord = new HotWord();
                hotWord.isHot = i == 0 ? true:false;
                hotWord.name = hotWords[i];
                hotWordList.add(hotWord);
            }
            return hotWordList;
        }


        @Override
        public void onLoaderReset(Loader<List<HotWord>> loader) {

        }
    };

    private class HotWordAdapter extends FragmentPagerAdapter {
        private List<HotWord> hotWords;

        public HotWordAdapter(FragmentManager fm, List<HotWord> hotWords) {
            super(fm);
            this.hotWords = hotWords;
        }

        @Override
        public Fragment getItem(int position) {

            return new HotWordsFragment().newInstance(HotWordsController.getFragmentCounts(hotWords).get(position));
        }

        @Override
        public int getCount() {
            return HotWordsController.getFragmentCounts(hotWords).size();
        }
    }

}
