package com.kandouwo.model.datarequest.search;

import android.net.Uri;

import com.google.gson.JsonElement;
import com.kandouwo.model.datarequest.PageRequest;
import com.kandouwo.model.datarequest.RequestBase;
import com.kandouwo.model.datarequest.douban.DoubanBookInfo;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by xuezhangbin on 14/12/6.
 */
public abstract class AbstractBookListRequest extends RequestBase<List<DoubanBookInfo>> implements PageRequest<List<DoubanBookInfo>> {

    protected int offset;
    protected int limit;
    protected int total;

    @Override
    public void setStart(int start) {
        this.offset = start;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    protected List<DoubanBookInfo> local() throws IOException {
        return null;
    }

    protected abstract String getBaseUrl();

    @Override
    protected String getUrl() {
        Uri.Builder builder = Uri.parse(getBaseUrl()).buildUpon();
        if (limit != 0) {
            builder.appendQueryParameter("start", String.valueOf(offset));
            builder.appendQueryParameter("count", String.valueOf(limit));
        }
        return builder.toString();
    }

    @Override
    public List<DoubanBookInfo> convert(JsonElement rootElement) throws IOException {
        return super.convert(rootElement);
    }

    @Override
    protected void store(List<DoubanBookInfo> data) {

    }

    @Override
    public HttpUriRequest getHttpUriRequest() {
        return new HttpGet(getUrl());
    }

    @Override
    public Uri getDataUri() {
        return null;
    }

    @Override
    public boolean isLocalValid() {
        return false;
    }
}
