package com.kindleren.kandouwo.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.inject.Inject;
import com.kandouwo.model.datarequest.Request;
import com.kandouwo.model.datarequest.search.HotWord;
import com.kandouwo.model.datarequest.search.SearchHotwordRequest;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.base.RequestLoader;
import com.kindleren.kandouwo.base.widget.HorizontalListView;
import com.kindleren.kandouwo.common.config.BaseConfig;
import com.kindleren.kandouwo.common.views.EditTextWithClearButton;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import roboguice.inject.InjectView;
import roboguice.util.Strings;

/**
 * Created by foxcoder on 14-9-22.
 */
public class SearchFragment extends BaseFragment implements AbsListView.OnScrollListener {
    @Inject
    private LayoutInflater inflater;

    private static final int LOADER_ID_HOTWORD = 1;
    public static final int SOURCE_INPUT = 0;
    private static final String KEY_SEARCH_HISTORY = "search_history";

    private ViewPager viewPager;

    @InjectView(R.id.indicator)
    private CirclePageIndicator pageIndicator;

    private SharedPreferences mSettingPreferences;

    @InjectView(R.id.history)
    private ListView historyListView;

    @InjectView(R.id.camera)
    private ImageView imageViewCamera;

    private HorizontalListView hotBookHorizontalListView;

    private HotBookData[] hotBookDatas;

    private List<HotWord> listHotWord;

    private EditTextWithClearButton mSearchView;

    private List<String> mHistoryWords = new ArrayList<String>();


    //------------------------------ 假数据 -------------------------------
    private void initHotBookData() {
        hotBookDatas = new HotBookData[]{
                new HotBookData(R.drawable.book_one_small, ""),
                new HotBookData(R.drawable.book_two_small, ""),
                new HotBookData(R.drawable.book_three_small, ""),
                new HotBookData(R.drawable.book_one_small, ""),
                new HotBookData(R.drawable.book_two_small, ""),
                new HotBookData(R.drawable.book_three_small, ""),
        };
    }
    //--------------------------------------------------------------------

    private void setupHotBookList() {
        initHotBookData();
        HotBookAdapter adapter = new HotBookAdapter(getActivity(), hotBookDatas);
        hotBookHorizontalListView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadHistoryWords();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (EditTextWithClearButton) getView().findViewById(R.id.search_edit);
        mSearchView.setClearButton(R.drawable.ic_search_clear_in_dealmap);
        mSearchView.removeDrawableEmpty();

        //点击button跳转到搜索结果页的Activity的搜索fragment，该Activity还承载搜索结果页fragment，根据frament_choose标记跳的页面
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra(SearchResultActivity.FRAGMENT_CHOOSE, "search");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSettingPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        hotBookHorizontalListView = (HorizontalListView) getView().findViewById(R.id.free_book_list_view);
        setupHotBookList();

        viewPager = (ViewPager) getView().findViewById(R.id.pager);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                initHotBookSearch();
            }
        });

        //网络请求预留方法------------------------------
//        getLoaderManager().initLoader(LOADER_ID_HOTWORD, null, hotWordLoader);
        //--------------------------------------------

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


    private void search() {
        if (!TextUtils.isEmpty(mSearchView.getText())) {
            search(mSearchView.getText().toString(), SOURCE_INPUT);
        }
    }

    private void search(String query, int source) {
        search(query, true, source);
    }

    private void search(String query, boolean add2history, int source) {
        if (query == null || TextUtils.isEmpty(query.trim()))
            return;
        if (add2history) {
            add2SearchHistory(query);
        }

        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra(SearchResultActivity.SEARCH_KEY, query);
        startActivity(intent);
    }

    private View historyFooter;

    private void add2SearchHistory(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return;
        }
        searchKey = searchKey.replaceAll("\\s", "");

        if (searchKey != null && !mHistoryWords.isEmpty() && mHistoryWords.contains(searchKey)) {
            mHistoryWords.remove(searchKey);
        }

        mHistoryWords.add(0, searchKey);
        saveHistoryWords();
    }

    private void saveHistoryWords() {
        String history = Strings.join(",", mHistoryWords);
        setSearchHistory(history);
    }

    private void setSearchHistory(String history) {
        mSettingPreferences.edit().putString(KEY_SEARCH_HISTORY, history).commit();
    }

    private String getSearchHistory() {
        return mSettingPreferences.getString(KEY_SEARCH_HISTORY, "");
    }

    private void loadHistoryWords() {
        mHistoryWords.clear();
        String searchHistory = getSearchHistory();
        if (!TextUtils.isEmpty(searchHistory)) {
            String[] histories = searchHistory.split(",");
            // 历史记录只保存5条记录
            if (histories != null && histories.length > 5) {
                String[] tmp = new String[5];
                System.arraycopy(histories, 0, tmp, 0, 5);
                histories = tmp;
            }

            List<String> newList = Arrays.asList(histories);
            mHistoryWords.addAll(newList);

            final SearchHistoryAdapter historyAdapter = new SearchHistoryAdapter(getActivity(), mHistoryWords);
            if (historyFooter == null) {
                historyFooter = LayoutInflater.from(getActivity()).inflate(R.layout.listitem_clear_history, null);
            }
            historyListView.removeFooterView(historyFooter);
            historyListView.addFooterView(historyFooter);
            historyFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearHistory();
                }
            });
            historyListView.setAdapter(historyAdapter);
            setListViewHeightBasedOnChildren(historyListView);
            historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String text = parent.getItemAtPosition(position).toString();

                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra(SearchResultActivity.SEARCH_KEY, text);
                    intent.putExtra(SearchResultActivity.FRAGMENT_CHOOSE,"result");
                    startActivity(intent);
                }
            });
        }
    }


    private void clearHistory() {
        mHistoryWords.clear();
        mSettingPreferences.edit().remove(KEY_SEARCH_HISTORY).commit();

        //TODO:to find a better solution
        historyListView.setAdapter(null);
        historyListView.removeFooterView(historyFooter);
        setListViewHeightBasedOnChildren(historyListView);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private LoaderManager.LoaderCallbacks hotWordLoader = new LoaderManager.LoaderCallbacks<List<HotWord>>() {

        @Override
        public Loader<List<HotWord>> onCreateLoader(int id, Bundle args) {

            return new RequestLoader<List<HotWord>>(getActivity(), new SearchHotwordRequest(), Request.Origin.NET);
        }

        @Override
        public void onLoadFinished(Loader<List<HotWord>> loader, List<HotWord> strings) {

            if (strings == null || strings.isEmpty()) {
                strings = loadHotWord();
            }
            viewPager.setAdapter(new HotWordAdapter(getChildFragmentManager(), strings));
            if (viewPager.getAdapter().getCount() > 1) {
                pageIndicator.setViewPager(viewPager);
                pageIndicator.setFillColor(getResources().getColor(R.color.blue));
                pageIndicator.setPageColor(getResources().getColor(R.color.gray_light));
                pageIndicator.setRadius(BaseConfig.dp2px(4));
                pageIndicator.setVisibility(View.VISIBLE);
                getView().findViewById(R.id.indicator_divider).setVisibility(View.VISIBLE);
            } else {
                pageIndicator.setVisibility(View.GONE);
                getView().findViewById(R.id.indicator_divider).setVisibility(View.GONE);
            }
        }

        private List<HotWord> loadHotWord() {
            List<HotWord> hotWordList = new ArrayList<HotWord>();
            String[] hotWords = getResources().getStringArray(R.array.search_hot_word);
            for (int i = 0; i < hotWords.length; i++) {
                HotWord hotWord = new HotWord();
                hotWord.isHot = i == 0 ? true : false;
                hotWord.name = hotWords[i];
                hotWordList.add(hotWord);
            }
            return hotWordList;
        }

        @Override
        public void onLoaderReset(Loader<List<HotWord>> loader) {

        }
    };

    //滑动时隐藏输入法
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        mSearchView.clearFocus();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

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

    private List<HotWord> loadHotWord() {
        List<HotWord> hotWordList = new ArrayList<HotWord>();
        String[] hotWords = getResources().getStringArray(R.array.search_hot_word);
        for (int i = 0; i < hotWords.length; i++) {
            HotWord hotWord = new HotWord();
            hotWord.isHot = i == 0 ? true : false;
            hotWord.name = hotWords[i];
            hotWordList.add(hotWord);
        }
        return hotWordList;
    }

    private void initHotBookSearch() {
        listHotWord = loadHotWord();
        viewPager.setAdapter(new HotWordAdapter(getChildFragmentManager(), listHotWord));
        if (viewPager.getAdapter().getCount() > 1) {
            pageIndicator.setViewPager(viewPager);
            pageIndicator.setFillColor(getResources().getColor(R.color.blue));
            pageIndicator.setPageColor(getResources().getColor(R.color.gray_light));
            pageIndicator.setRadius(BaseConfig.dp2px(4));
            pageIndicator.setVisibility(View.VISIBLE);
            getView().findViewById(R.id.indicator_divider).setVisibility(View.VISIBLE);
        } else {
            pageIndicator.setVisibility(View.GONE);
            getView().findViewById(R.id.indicator_divider).setVisibility(View.GONE);
        }
    }
}
