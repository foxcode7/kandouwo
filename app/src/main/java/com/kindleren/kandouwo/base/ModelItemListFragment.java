package com.kindleren.kandouwo.base;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import roboguice.util.Ln;

/**
 * Created by xuezhangbin on 14/12/1.
 */
public abstract class ModelItemListFragment<D, I> extends BaseListFragment implements LoaderManager.LoaderCallbacks<D> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onLoadFinished(Loader<D> loader, D data) {
        Exception exception = null;
        if (loader instanceof AbstractModelLoader) {
            exception = ((AbstractModelLoader) loader).getException();
        }
        onLoadFinished(loader, data, exception);
        UIReactOnException(exception, data);
    }

    protected abstract void onLoadFinished(Loader<D> loader, D data, Exception exception);

    protected abstract List<I> getList(D d);

    protected abstract void UIReactOnException(Exception e, D data);

    @Override
    public void onLoaderReset(Loader<D> listLoader) {
    }

    @Override
    protected void onLogin() {
        super.onLogin();
        Ln.d("on user login");
        if (getListAdapter() == null || getListAdapter().getCount() <= 0) {
            setListShown(false);
            refresh();
        }
    }
}
