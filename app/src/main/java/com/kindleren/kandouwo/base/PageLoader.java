package com.kindleren.kandouwo.base;

import android.content.Context;
import android.support.v4.content.ConcurrentTask;

import com.kandouwo.model.datarequest.PageIterator;
import com.kandouwo.model.datarequest.Request;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by xuezhangbin on 14/12/4.
 */
public class PageLoader<D> extends AbstractModelLoader<D> {
    private static final long CACHE_VALIDITY = 5 * 60 * 1000;
    private static final long MARK_VALIDITY = 30 * 60 * 1000;
    private final PageIterator<D> pageIterator;
    private boolean loaded;


    public PageLoader(Context context, PageIterator<D> pageIterator) {
        super(context);
        this.pageIterator = pageIterator;
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    public PageIterator<D> getPageIterator() {
        return pageIterator;
    }

    @Override
    protected D doLoadData() throws IOException {
        loaded = true;
        D data = pageIterator.next();
        return pageIterator.getResource();
    }

    @Override
    protected Executor dispatchExecutor() {
        if (pageIterator.loadFrom() == Request.Origin.LOCAL) {
            return ConcurrentTask.SERIAL_EXECUTOR;
        } else {
            return ConcurrentTask.THREAD_POOL_EXECUTOR;
        }
    }
}
