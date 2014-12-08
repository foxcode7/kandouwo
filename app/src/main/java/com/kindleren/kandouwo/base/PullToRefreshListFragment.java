package com.kindleren.kandouwo.base;

import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by xuezhangbin on 14/12/2.
 */
public abstract class PullToRefreshListFragment<D, I> extends ModelItemListFragment<D, I> implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private boolean isPullToRefresh;
    private PullToRefreshListView pullToRefreshListView;

    @Override
    protected final View createListView() {
        pullToRefreshListView = (PullToRefreshListView) createPullToRefreshListView();
        return pullToRefreshListView;
    }

    protected View createPullToRefreshListView() {
        PullToRefreshListView pullToRefreshListView = new PullToRefreshListView(getActivity());
        pullToRefreshListView.getRefreshableView().setDrawSelectorOnTop(true);
        return pullToRefreshListView;
    }

    public PullToRefreshListView getPullToRefreshView() {
        return pullToRefreshListView;
    }

    @Override
    protected void ensureList() {
        super.ensureList();
        if (pullToRefreshListView != null) {
            pullToRefreshListView.setOnRefreshListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pullToRefreshListView = null;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        onPullToRefresh();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        onPullToRefresh();
    }

    protected void onPullToRefresh() {
        isPullToRefresh = true;
        refresh();
    }

    @Override
    protected void onLoadFinished(Loader<D> loader, D data, Exception exception) {
        if(isPullToRefresh)
        {
            getPullToRefreshView().onRefreshComplete();
            isPullToRefresh = false;
        }
        setListShown(true);
    }

    @Override
    protected void UIReactOnException(Exception e, D data) {
        setEmptyState(e != null);
    }

    protected void setRefreshing() {
        if (pullToRefreshListView != null) {
            pullToRefreshListView.setRefreshing();
        }
    }
}
