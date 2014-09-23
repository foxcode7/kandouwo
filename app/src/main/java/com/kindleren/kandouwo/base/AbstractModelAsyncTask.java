package com.kindleren.kandouwo.base;

import android.support.v4.content.ConcurrentTask;

import org.apache.http.client.HttpResponseException;

/**
 * Created by foxcoder on 14-9-23.
 */
public abstract class AbstractModelAsyncTask<T> extends ConcurrentTask<Void, Integer, T> {
    private Exception exception;
    private T data;

    @Override
    protected T doInBackground(Void... voids) {
        try {
            data = doLoadData();
            exception = null;
        } catch (Exception e) {
            data = null;
            exception = e;
        }
        return getData();
    }

    protected abstract T doLoadData() throws Exception;

    public Exception getException() {
        return exception;
    }

    public T getData() {
        return data;
    }

    @Override
    protected void onPostExecute(T t) {
        if (exception == null) {
            onSuccess(t);
        } else {
            onException(exception);
        }
        onFinally();
    }

    public void onSuccess(T t) {

    }

    public void onFinally() {

    }

    public void onException(Exception exception) {

    }

    public String getExceptionMessage(){
        if (exception instanceof HttpResponseException) {
            return exception.getMessage();
        } else {
            return "数据获取失败，请稍候重试";
        }
    }
}