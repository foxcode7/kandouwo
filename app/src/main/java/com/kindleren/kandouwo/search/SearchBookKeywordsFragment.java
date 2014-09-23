package com.kindleren.kandouwo.search;

import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;
import com.kandouwo.model.datarequest.Request;
import com.kandouwo.model.datarequest.douban.DoubanSearchBook;
import com.kandouwo.model.datarequest.douban.DoubanSearchBookRequest;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.base.RequestLoader;

/**
 * Created by foxcoder on 14-9-18.
 */
public class SearchBookKeywordsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<DoubanSearchBook> {

    @Inject
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_book_keywords, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<DoubanSearchBook> onCreateLoader(int id, Bundle args) {
        return new RequestLoader(getActivity(), new DoubanSearchBookRequest("莫言"), Request.Origin.NET);
    }

    @Override
    public void onLoadFinished(Loader<DoubanSearchBook> loader, DoubanSearchBook data) {
        if(data != null){
            DoubanSearchBook ds = data;

            TextView textView = (TextView)getView().findViewById(R.id.isSuccess);
            textView.setText("莫言找到了");
        }
    }

    @Override
    public void onLoaderReset(Loader<DoubanSearchBook> loader) {

    }
}
