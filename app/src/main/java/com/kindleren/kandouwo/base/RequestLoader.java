package com.kindleren.kandouwo.base;

import android.content.Context;
import android.support.v4.content.ConcurrentTask;

import com.kandouwo.model.datarequest.Request;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by foxcoder on 14-9-18.
 */
public class RequestLoader<D> extends AbstractModelLoader<D> {
    protected final Request.Origin origin;
    private final Request<D> request;

    public RequestLoader(Context context, Request<D> request, Request.Origin origin) {
        super(context);
        this.request = request;
        this.origin = origin;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    protected D doLoadData() throws IOException {
        return getRequest().execute(origin);
    }

    @Override
    protected Executor dispatchExecutor() {
        switch (origin) {
            case LOCAL:
                return ConcurrentTask.SERIAL_EXECUTOR;
            case NET:
                return ConcurrentTask.THREAD_POOL_EXECUTOR;
            case UNSPECIFIED:
            default:
                return getRequest().isLocalValid() ? ConcurrentTask.SERIAL_EXECUTOR : ConcurrentTask.THREAD_POOL_EXECUTOR;
        }
    }

    public Request<D> getRequest() {
        return request;
    }
}

