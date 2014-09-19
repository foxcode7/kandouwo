package com.kindleren.kandouwo.search;

import android.os.Bundle;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseActivity;

/**
 * Created by foxcoder on 14-9-18.
 */
public class SearchBookKeywordsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        SearchBookKeywordsFragment fragment = new SearchBookKeywordsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commitAllowingStateLoss();
    }
}
