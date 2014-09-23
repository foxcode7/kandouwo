package com.kandouwo.model.datarequest.guess;

import android.net.Uri;

import com.google.gson.JsonElement;
import com.kandouwo.model.ApiConfig;
import com.kandouwo.model.datarequest.RequestBase;
import com.kandouwo.model.datarequest.douban.DoubanSearchBook;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

/**
 * Created by xuezhangbin on 14-9-22.
 */
public class GuessBookNameRuquest extends RequestBase<DoubanSearchBook> {

    private static final String URL_PATH = "v2/book/search";

    private final String keywords;

    public GuessBookNameRuquest(String keywords){
        this.keywords = keywords;
    }

    @Override
    protected DoubanSearchBook local() throws IOException {
        return null;//暂时不做本地化
    }

    @Override
    protected String getUrl() {
        return Uri.parse(ApiConfig.baseDoubanApiUrl).buildUpon()
                .appendEncodedPath(URL_PATH)
                .appendQueryParameter("q", keywords)
                .toString();
    }

    @Override
    public DoubanSearchBook convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement);
    }

    @Override
    protected void store(DoubanSearchBook data) {

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
