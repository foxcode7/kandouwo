package com.kindleren.kandouwo.search;

import android.os.Bundle;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseActivity;


/**
 * Created by xuezhangbin on 14/11/27.
 */
public class SearchResultActivity extends BaseActivity {

    public static final String SEARCH_KEY = "search_key";

    private String mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        Bundle args = getIntent().getExtras();
        mSearchText = args.getString(SEARCH_KEY);
        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(mSearchText);
        getSupportFragmentManager().beginTransaction().add(R.id.search_result_content,searchResultFragment).commitAllowingStateLoss();
    }
}
