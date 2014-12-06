package com.kindleren.kandouwo.base;

import android.content.Context;
import android.support.v4.content.ConcurrentTaskLoader;

import java.io.IOException;

/**
 * Created by foxcoder on 14-9-18.
 */
public abstract class AbstractModelLoader<D> extends ConcurrentTaskLoader<D> {
    protected D data;
    private Exception exception;

    public AbstractModelLoader(Context context) {
        super(context);
    }

    @Override
    public D loadInBackground() {
        try {
            D d = doLoadData();
            return d;
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        }
        if (takeContentChanged() || data == null) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(D data) {
        if (!isReset()) {
            this.data = data;
            super.deliverResult(data);
        }
    }

    protected abstract D doLoadData() throws IOException;

    public Exception getException() {
        return exception;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        data = null;
    }
}
