package com.kindleren.kandouwo.search;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kandouwo.model.datarequest.PageIterator;
import com.kandouwo.model.datarequest.Request;
import com.kandouwo.model.datarequest.douban.DoubanBookInfo;
import com.kandouwo.model.datarequest.search.SearchBookResultRequest;
import com.kindleren.kandouwo.base.BaseListAdapter;
import com.kindleren.kandouwo.base.PageLoader;
import com.kindleren.kandouwo.base.PagedItemListFragment;

import java.util.List;

/**
 * Created by xuezhangbin on 14/11/27.
 */
public class SearchResultFragment extends PagedItemListFragment<List<DoubanBookInfo>, DoubanBookInfo> {

    private static final String TEXT = "text";
    private String mSearchText;

    public static SearchResultFragment newInstance(String text) {
        SearchResultFragment fragment;
        fragment = new SearchResultFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TEXT, text);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setSelector(android.R.color.transparent);
        getListView().setDivider(null);
        if (getArguments() != null) {
            mSearchText = getArguments().getString(TEXT);
        }

        getLoaderManager().initLoader(PAGE_LOADER_ID, null, this);
    }

    @Override
    public void onLoadFinished(Loader<List<DoubanBookInfo>> loader, List<DoubanBookInfo> data) {
        super.onLoadFinished(loader, data, null);
    }

    @Override
    protected List<DoubanBookInfo> getList(List<DoubanBookInfo> doubanBookInfos) {
        return doubanBookInfos;
    }


    @Override
    protected void refresh() {
        Bundle args = new Bundle();
        args.putBoolean("refresh", true);
        getLoaderManager().restartLoader(0, args, this);
    }

    @Override
    protected BaseListAdapter<DoubanBookInfo> createAdapter() {
        SearchResultAdapter bookListAdapter = new SearchResultAdapter(getActivity());
        return bookListAdapter;
    }

    @Override
    protected PageIterator<List<DoubanBookInfo>> createPageIterator(boolean refresh) {
        SearchBookResultRequest realRequest = new SearchBookResultRequest(mSearchText);
        return new PageIterator<List<DoubanBookInfo>>(realRequest, Request.Origin.NET, 20);
    }

    @Override
    protected PageLoader<List<DoubanBookInfo>> createPageLoader(PageIterator<List<DoubanBookInfo>> pageIterator) {
        return new PageLoader<List<DoubanBookInfo>>(getActivity(), pageIterator);
    }
}
