package com.kindleren.kandouwo.search;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseActivity;
import com.kindleren.kandouwo.common.views.EditTextWithClearButton;


/**
 * Created by xuezhangbin on 14/11/27.
 */
public class SearchResultActivity extends BaseActivity implements View.OnClickListener, ResultSearchFragment.SearchTextListener {

    private static final String FRAGMENT_TAG_PREFIX = "SearchResultActivityFragment_";

    public static final String SEARCH_KEY = "search_key";
    public static final String FRAGMENT_CHOOSE = "fragment_choose";
    public static final String SEARCH = "search";
    public static final String RESULT = "result";
    private View customeView;
    private ImageView camerView;
    private ImageView search_image;

    private String mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        customeView = getLayoutInflater().inflate(R.layout.actionbar_search, null);
        getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg_listitem));

        camerView = (ImageView) customeView.findViewById(R.id.camera);
        customeView.findViewById(R.id.search_edit).setOnClickListener(this);
        ((EditTextWithClearButton) customeView.findViewById(R.id.search_edit)).setClearButton(R.drawable.ic_search_clear_in_dealmap);
        ((EditTextWithClearButton) customeView.findViewById(R.id.search_edit)).removeDrawableEmpty();
        search_image = (ImageView) customeView.findViewById(R.id.search_image);

        getSupportActionBar().setCustomView(customeView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        ;

        Bundle args = getIntent().getExtras();
        String fragmentType = args.getString(FRAGMENT_CHOOSE);

        if (fragmentType.equals(SEARCH)) {
            ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.search_result_content, resultSearchFragment).commitAllowingStateLoss();
        } else if (fragmentType.equals(RESULT)) {
            mSearchText = args.getString(SEARCH_KEY);
            SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(mSearchText);
            getSupportFragmentManager().beginTransaction().add(R.id.search_result_content, searchResultFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setSearchText(String text) {
        mSearchText = text;
        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(text);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.search_result_content, searchResultFragment);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }
}
