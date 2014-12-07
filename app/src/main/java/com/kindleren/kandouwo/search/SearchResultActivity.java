package com.kindleren.kandouwo.search;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseActivity;
import com.kindleren.kandouwo.common.views.EditTextWithClearButton;


/**
 * Created by xuezhangbin on 14/11/27.
 */
public class SearchResultActivity extends BaseActivity implements View.OnClickListener,ResultSearchFragment.SearchTextListener{

    private static final String FRAGMENT_TAG_PREFIX = "SearchResultActivityFragment_";

    public static final String SEARCH_KEY = "search_key";
    public static final String FRAGMENT_CHOOSE = "fragment_choose";
    public static final String SEARCH = "search";
    public static final String RESULT = "result";

    private String mSearchText;
    private View customeView;

    private EditTextWithClearButton mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);

        customeView = getLayoutInflater().inflate(R.layout.search_box_layout, null);
        customeView.findViewById(R.id.search_edit).setOnClickListener(this);
        mSearchView = (EditTextWithClearButton)customeView.findViewById(R.id.search_edit);
        ((EditTextWithClearButton) customeView.findViewById(R.id.search_edit)).setClearButton(R.drawable.ic_search_clear_in_dealmap);
        ((EditTextWithClearButton) customeView.findViewById(R.id.search_edit)).removeDrawableEmpty();
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.search_result_content,resultSearchFragment);
                ft.commitAllowingStateLoss();
                getSupportFragmentManager().executePendingTransactions();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//返回图标
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        getSupportActionBar().setCustomView(customeView, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL | Gravity.LEFT));
        getSupportActionBar().setDisplayShowHomeEnabled(false); //应用图标不显示

        Bundle args = getIntent().getExtras();

        String fragmentType = args.getString(FRAGMENT_CHOOSE);
        if (fragmentType.equals("search")) {
            ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.search_result_content, resultSearchFragment).commitAllowingStateLoss();
        } else if (fragmentType.equals("result")) {
            mSearchText = args.getString(SEARCH_KEY);
            SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(mSearchText);
            getSupportFragmentManager().beginTransaction().add(R.id.search_result_content, searchResultFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);
        return true;
    }

    @Override
    public void setSearchText(String text) {
        mSearchText = text;
        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(text);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.search_result_content,searchResultFragment);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }
}
