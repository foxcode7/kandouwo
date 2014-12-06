package com.kindleren.kandouwo.base;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kandouwo.model.datarequest.PageIterator;
import com.kindleren.kandouwo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuezhangbin on 14/12/4.
 */
public abstract class PagedItemListFragment<D,I> extends PullToRefreshListFragment<D,I> implements AbsListView.OnScrollListener {
    //分页模式
    protected static final int MODE_ADD_BELOW = 0; //向下滚动分页向下增长
    protected static final int MODE_ADD_ABOVE = 1; //向上滑动分页在屏幕顶端增长（适用于对话情景）
    protected static final int PAGE_LOADER_ID = 100;
    protected PageIterator<D> pageIterator;
    protected int preLastVisibleItem;
    protected int preFirstVisibleItem;
    protected int preTotalItemCount;
    private AbsListView.OnScrollListener onScrollListener;
    private boolean isPageLoading;
    private boolean loadAdded;
    private PointsLoopView loadView;
    private int mode = MODE_ADD_BELOW;

    //设置分页模式
    public void setPagedMode(int mode) {
        this.mode = mode;
    }

    @Override
    protected View createPullToRefreshListView() {
        PullToRefreshListView view = (PullToRefreshListView) super.createPullToRefreshListView();
        if (mode == MODE_ADD_ABOVE) view.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = ((ListView) view.findViewById(android.R.id.list));
        loadView = (PointsLoopView) inflater.inflate(R.layout.list_footer_more, listView, false);
        loadView.setEnabled(false);
        loadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNextPage();
            }
        });
        loadAdded = false;
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnScrollListener(this);
    }

    @Override
    protected void refresh() {
        preTotalItemCount = 0;
        //防止刷新时fragment被detach造成crash
        if (isAdded()) {
            Bundle args = new Bundle();
            args.putBoolean("refresh", true);
            getLoaderManager().restartLoader(PAGE_LOADER_ID, args, this);
        }
    }

    protected abstract BaseListAdapter<I> createAdapter();

    protected abstract PageIterator<D> createPageIterator(boolean refresh);

    protected PageLoader<D> createPageLoader(PageIterator<D> pageIterator) {
        return new PageLoader<D>(getActivity(), pageIterator);
    }

    @Override
    public Loader<D> onCreateLoader(int i, Bundle bundle) {
        isPageLoading = true;
        showIndeterminateProgressBar();
        boolean refresh = bundle != null && bundle.getBoolean("refresh");
        if (pageIterator == null || refresh) {
            pageIterator = createPageIterator(refresh);
        }
        return createPageLoader(pageIterator);
    }

    @Override
    public void onLoadFinished(Loader<D> loader, D data, Exception exception) {
        super.onLoadFinished(loader, data, exception);
        if (exception != null) {
            exception.printStackTrace();
        }
        if (pageIterator == null) {
            pageIterator = ((PageLoader<D>) loader).getPageIterator();
        }
        hideIndeterminateProgressBar();
        isPageLoading = false;

        resetListAdapter();

        List<I> mData = new ArrayList<I>();
        if (getList(data) != null) mData.addAll(getList(data));
        if (mode == MODE_ADD_ABOVE) {
            if (!(null == mData || mData.isEmpty())) {
                Collections.reverse(mData);
            }
        }

        bindLoadFinishedData(mData);

        refreshLoadState();
        if (mode == MODE_ADD_ABOVE) {
            getListView().setSelection(getListView().getCount() - preTotalItemCount - 1);
        }
    }

    protected void resetListAdapter() {
        if (getListAdapter() == null) {
            setListAdapter(createAdapter());
        }
    }

    protected void bindLoadFinishedData(List<I> mData) {
        if (!(mData == null || mData.isEmpty())) {
            ((BaseListAdapter) getListAdapter()).setData(mData);
        }
    }

    protected void showIndeterminateProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    protected void hideIndeterminateProgressBar() {
        setProgressBarIndeterminateVisibility(false);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (onScrollListener != null) {
            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        //如果位置没有变动则不用进行之后的计算了
        if ((mode == MODE_ADD_BELOW && preLastVisibleItem == firstVisibleItem + visibleItemCount) || (mode == MODE_ADD_ABOVE && preFirstVisibleItem == firstVisibleItem)) {
            return;
        }

        preLastVisibleItem = firstVisibleItem + visibleItemCount;
        preFirstVisibleItem = firstVisibleItem;
        if (pageIterator == null || !pageIterator.hasNext()) {
            return;
        }
        boolean isScrollDownRefresh = (mode == MODE_ADD_BELOW) && (visibleItemCount > 0) && (preLastVisibleItem >= totalItemCount) && !isPageLoading;
        boolean isScrollUpRefresh = (mode == MODE_ADD_ABOVE) && (visibleItemCount > 0) && (preFirstVisibleItem <= (loadAdded ? 1 : 0)) && !isPageLoading;
        if (isScrollDownRefresh || isScrollUpRefresh) {
            loadNextPage();
        }
    }

    @Override
    public void onDestroyView() {
        getListView().setOnScrollListener(null);
        super.onDestroyView();
        loadView.stopLoop();
        loadView = null;
    }

    protected void showLoadError(String footerText) {
        if (TextUtils.isEmpty(footerText)) {
            loadView.setText(R.string.page_footer_failed);
        } else {
            loadView.setText(footerText);
        }
        loadView.stopLoop();
        loadView.setEnabled(true);
    }

    protected void showLoadError() {
        showLoadError(getString(R.string.page_footer_failed));
    }

    protected void loadNextPage() {
        preTotalItemCount = getListAdapter().getCount();
        loadView.setText(R.string.loading);
        loadView.restartLoop();
        loadView.setEnabled(false);
        getLoaderManager().restartLoader(PAGE_LOADER_ID, null, this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    protected void UIReactOnException(Exception e, D data) {
        if (pageIterator == null || pageIterator.getStart() == 0) {
            super.UIReactOnException(e, data);
        } else {
            if (e != null) {
                showLoadError();
            }
        }
    }

    private void refreshLoadState() {
        if (pageIterator.hasNext()) { // 有下一页
            if (!loadAdded) { // 没有添加过分页器
                enableLoad();
            }
        } else {
            loadAdded = false;
            loadView.stopLoop();
            if (mode == MODE_ADD_BELOW) getListView().removeFooterView(loadView);
            if (mode == MODE_ADD_ABOVE) getListView().removeHeaderView(loadView);
        }
    }

    public void enableLoad() {
        loadView.setText(R.string.loading);
        loadAdded = true;
        loadView.startLoop();
        if (mode == MODE_ADD_BELOW) getListView().addFooterView(loadView, null, false);
        if (mode == MODE_ADD_ABOVE) getListView().addHeaderView(loadView, null, false);
    }
}
